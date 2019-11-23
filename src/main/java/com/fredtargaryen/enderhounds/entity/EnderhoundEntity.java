package com.fredtargaryen.enderhounds.entity;

import com.fredtargaryen.enderhounds.DataReference;
import com.fredtargaryen.enderhounds.entity.ai.FollowLeaderGoal;
import com.fredtargaryen.enderhounds.entity.ai.PackLogicGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;

import static com.fredtargaryen.enderhounds.EnderhoundsBase.LEADCAP;

public abstract class EnderhoundEntity extends CreatureEntity implements Comparable<EnderhoundEntity> {
    public enum GrowthStage {
        //TODO Correct eye heights and hitboxes
        PUP(0, 1.1F, 3, 0.5F, 1.0F, 8),
        TEENAGE(1, 0.0F, 5, 0.5F, 1.0F, 16),
        MATURE(3, 0.0F, 9, 0.5F, 1.0F, 32),
        ELDERLY(2, 0.0F, 7, 0.5F, 1.0F, 24);

        /**
         * Shows which growth stage is strongest, for Enderhound strength comparison.
         * Pup < Teenage < Elderly < Mature
         */
        private final int strengthLevel;
        private final float eyeHeight;
        private final int averageTPLength;
        private final float bBoxWidth;
        private final float bBoxHeight;
        private final int tpRange;

        GrowthStage(int strengthLevel, float eyeHeight, int averTPLen, float width, float height, int tpRange) {
            this.strengthLevel = strengthLevel;
            this.eyeHeight = eyeHeight;
            this.averageTPLength = averTPLen;
            this.bBoxWidth = width;
            this.bBoxHeight = height;
            this.tpRange = tpRange;
        }

        //Getters below here
        public int getStrengthLevel(){return strengthLevel;}
        public float getEyeHeight(){ return eyeHeight;}
        public int getAverageTPLength() { return averageTPLength; }
        public float getBoxWidth() { return bBoxWidth; }
        public float getBoxHeight() { return bBoxHeight; }
        public int getTpRange() { return tpRange; }
    }

    public enum Personality {
        AGGRESSIVE(2, 0.1F),
        CUNNING(1, 0.4F),
        WEAK(0, 0.2F);

        /**
         * The Enderhound will concede the fight if its health reaches this value * its maximum health or lower
         */
        private final float healthSubmitPercentage;
        /**
         * Shows which personality is strongest, for Enderhound strength comparison.
         * Weak < Cunning < Aggressive
         */
        private final int strengthLevel;

        Personality(int strengthLevel, float submit) {
            this.healthSubmitPercentage = submit;
            this.strengthLevel = strengthLevel;
        }

        //Getters below here
        public float getHealthSubmitPercentage(){return this.healthSubmitPercentage;}
        public int getStrengthLevel(){return this.strengthLevel;}
    }

    public enum Power {
        NONE,
        BALL,
        DOPPEL,
        JAWS
    }

    public GrowthStage stage;
    public Personality personality;
    public Power power;
    protected boolean isShaking;
    private ArrayList<EnderhoundEntity> nearbyHounds = new ArrayList<EnderhoundEntity>();
    protected LivingEntity leader = null;

    public EnderhoundEntity(EntityType type, World world) {
        super(type, world);
        //TODO this.setSize(this.stage.getBoxWidth(), this.stage.getBoxHeight());
        this.stepHeight = 1.0F;
    }
    
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new FollowLeaderGoal(this));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new PackLogicGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, EndermiteEntity.class, true));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        AbstractAttributeMap aam = this.getAttributes();
        aam.getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        aam.getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(70.0D);
    }

    /**
     * Handles transition between GrowthStages. All implementations of this (unless they can't grow anymore) should call
     * the super method, to prevent Enderhounds trying to interact with previous Enderhounds which don't exist anymore.
     */
    protected void grow() {
        for(EnderhoundEntity e : this.nearbyHounds) {
            e.refreshNearbyHounds();
        }
    }

    /**
     * Decides the leader to follow based on the strength of all Enderhounds in area (see compareTo).
     */
    public void refreshLeader() {
        this.refreshNearbyHounds();
        Collections.sort(this.nearbyHounds);
        //this.nearbyHounds will at least contain this
        //Strongest hound around is at bottom of list (could be this)
        EnderhoundEntity strongestHound = this.nearbyHounds.get(this.nearbyHounds.size() - 1);
        //Leader implicitly has ILeadPackCapability
        if(this.leader == null) {
            if(strongestHound != this) {
                this.leader = strongestHound;
            }
        }
        else {
            this.leader.getCapability(LEADCAP, Direction.UP).ifPresent(cap -> {
                if (!cap.isStrongerThan(strongestHound)) {
                    if (strongestHound == this) {
                        this.leader = null;
                    } else {
                        this.leader = strongestHound;
                    }
                }
            });
        }
    }

    /**
     * Used for caller to check if an Enderhound is following its lead
     * @param caller The Enderhound that called this method
     * @return Whether or not this Enderhound is following the caller
     */
    public boolean isFollowingMe(EnderhoundEntity caller)
    {
        return caller == this.leader;
    }

    /**
     * Assembles a list of all Enderhounds within an area around this Enderhound (including this Enderhound).
     */
    private void refreshNearbyHounds()
    {
        this.nearbyHounds.clear();
        for(Object o : this.world.getEntitiesWithinAABB(EnderhoundEntity.class,
                new AxisAlignedBB(this.posX - 0.5, this.posY - 0.5, this.posZ - 0.5,
                        this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5)
                        .expand(DataReference.HERDRANGEXZ, DataReference.HERDRANGEY, DataReference.HERDRANGEXZ)))
        {
            if(o instanceof EnderhoundEntity)
            {
                this.nearbyHounds.add((EnderhoundEntity)o);
            }
        }
    }

    /**
     * Compares "natural" strength of two Enderhounds. The strongest Enderhound should be at the end of the list,
     * then that Enderhound can be assigned to the leader variable.
     * @param ee2 The Enderhound to compare this Enderhound with
     * @return Whether this is naturally stronger than ee2
     */
    public int compareTo(EnderhoundEntity ee2)
    {
        //Compares personality strengths
        int ee1points = this.personality.getStrengthLevel();
        int ee2points = ee2.personality.getStrengthLevel();
        if(ee1points > ee2points)
        {
            return 1;
        }
        else if(ee1points < ee2points)
        {
            return -1;
        }
        else
        {
            //Compares health
            ee1points = (int)this.getHealth();
            ee2points = (int)ee2.getHealth();
            if(ee1points > ee2points)
            {
                return 1;
            }
            else if(ee1points < ee2points)
            {
                return -1;
            }
            else
            {
                //Compares growth stages
                ee1points = this.stage.getStrengthLevel();
                ee2points = ee2.stage.getStrengthLevel();
                if(ee1points > ee2points)
                {
                    return 1;
                }
                else if(ee1points < ee2points)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void livingTick() {
        float health = this.getHealth();
        float maxHealth = this.getMaxHealth();
        if (this.world.isRemote) {
            float healthRatio = health / maxHealth;
            //If at less than 1/3 health, doesn't spawn any particles
            //Else if at less than 2/3 health, spawns one particle
            //Else spawns 2 particles
            for (int i = 0; i < healthRatio * 3 - 1; ++i) {
                this.world.addParticle(
                        ParticleTypes.PORTAL,
                        this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.stage.getBoxWidth(),
                        this.posY + this.rand.nextDouble() * (double)this.stage.getBoxHeight() - 0.25D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.stage.getBoxWidth(),
                        (this.rand.nextDouble() - 0.5D) * 2.0D,
                        -this.rand.nextDouble(),
                        (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        else
        {
            BlockPos pos = this.getPosition();
            float lightLevel = this.world.getLight(pos);
            float yCoord = pos.getY();
            //Should it heal?
            if(lightLevel <= 10 && yCoord < 129 && health < maxHealth)
            {
                int ticksUntilRegen = (int) ((lightLevel + 1) * 10 + yCoord);
                if(this.ticksExisted % ticksUntilRegen == 0)
                {
                    this.heal(1F);
                }
            }
        }

        this.isJumping = false;
        super.livingTick();
    }

    @Override
    protected void updateAITasks() {
        if (this.isInWaterRainOrBubbleColumn()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

//        if (this.world.isDaytime() && this.ticksExisted >= this.targetChangeTime + 600) {
//            float f = this.getBrightness();
//            if (f > 0.5F && this.world.isSkyLightMax(new BlockPos(this)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
//                this.setAttackTarget((LivingEntity)null);
//                this.teleportRandomly();
//            }
//        }
        super.updateAITasks();
    }

    /**
     * Applies the given player interaction to this Entity.
     */
    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand) {
        this.setAttackTarget(this.getAttackTarget() == null ? player : null);
        this.isShaking = !this.isShaking;
        return true;
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        switch(nbt.getInt("personality")) {
            case 0:
                this.personality = Personality.CUNNING;
                break;
            case 1:
                this.personality = Personality.AGGRESSIVE;
                break;
            default:
                this.personality = Personality.WEAK;
                break;
        }
        switch(nbt.getInt("power")) {
            case 0:
                this.power = Power.BALL;
                break;
            case 1:
                this.power = Power.DOPPEL;
                break;
            default:
                this.power = Power.NONE;
                break;
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        switch(this.personality) {
            case CUNNING:
                nbt.putInt("personality", 0);
                break;
            case AGGRESSIVE:
                nbt.putInt("personality", 1);
                break;
            //The "weak" personality
            default:
                nbt.putInt("personality", 2);
                break;
        }
        switch(this.power) {
            case BALL:
                nbt.putInt("power", 0);
                break;
            case DOPPEL:
                nbt.putInt("power", 1);
                break;
            //No power
            default:
                nbt.putInt("power", 2);
                break;
        }
    }

    ///////////////////////////////////////////
    //TELEPORT METHODS - STOLEN FROM ENDERMAN//
    ///////////////////////////////////////////
    /**
     * Teleport the enderhound to a random nearby position within its range
     */
    protected boolean teleportRandomly() {
        int range = this.stage.getTpRange();
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * range;
        double d1 = this.posY + (double)(this.rand.nextInt(range) - (range / 2));
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * range;
        return this.teleportTo(d0, d1, d2);
    }

    /**
     * Teleport the enderhound to another entity. Probably will never use
     */
    protected boolean teleportToEntity(Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getBoundingBox().minY + (double)(this.getHeight() / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.teleportTo(d1, d2, d3);
    }

    /**
     * Teleport the enderhound
     */
    protected boolean teleportTo(double x, double y, double z) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

        while(blockpos$mutableblockpos.getY() > 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMovement()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        if (!this.world.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMovement()) {
            return false;
        } else {
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
            boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag) {
                this.world.playSound((PlayerEntity)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag;
        }
    }

    /**
     * x, y and z are of the ultimate target. This will do a single TP roughly towards it, covering roughly the jump
     * length of the Enderhound.
     */
    public void teleportOnceTowards(double x, double y, double z) {
        double angleTowardsPoint = Math.atan2(z - this.posZ, x - this.posX);
        angleTowardsPoint += this.rand.nextBoolean() ? Math.PI / 4 : -Math.PI / 4;
        angleTowardsPoint += this.rand.nextBoolean() ? Math.PI / 8 : -Math.PI / 8;
        int range = this.stage.getTpRange();
        this.teleportTo(x + range * Math.cos(angleTowardsPoint), y, z + range * Math.sin(angleTowardsPoint));
    }
    ///////////////////////////
    //END OF TELEPORT METHODS//
    ///////////////////////////

    public boolean attackEntityFrom(DamageSource source, float amount) {
        super.attackEntityFrom(source, amount);
        Entity e = source.getTrueSource();
        if(e instanceof LivingEntity) {
            this.setAttackTarget((LivingEntity) e);
            this.pingNewAttackTarget((LivingEntity) e);
        }
        return true;
    }

    public void pingNewAttackTarget(LivingEntity targ) {
        for(EnderhoundEntity e : this.nearbyHounds) {
            e.considerNewTarget(this, targ);
        }
    }

    public void considerNewTarget(EnderhoundEntity caller, LivingEntity targ) {
        if(this.leader == null) {
            if(caller.isFollowingMe(this)) {
                if (this.getAttackTarget() == null) {
                    this.setAttackTarget(targ);
                }
            }
        }
        else {
            if(caller == this.leader) {
                this.setAttackTarget(targ);
            }
            else {
                if(caller.leader == this.leader && this.getAttackTarget() == null) {
                    this.setAttackTarget(targ);
                }
            }
        }
    }

    //Simple getters
    public boolean isShaking() { return this.isShaking; }

    public LivingEntity getLeader() { return this.leader; }

    //TODO Remove
//    public float getEyeHeight() {
//        return this.stage.getEyeHeight();
//    }
}
