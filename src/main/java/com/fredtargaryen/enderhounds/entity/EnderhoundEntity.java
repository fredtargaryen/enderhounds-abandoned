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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;

import static com.fredtargaryen.enderhounds.EnderhoundsBase.LEADCAP;

public abstract class EnderhoundEntity extends CreatureEntity implements Comparable<EnderhoundEntity> {
    public enum GrowthStage {
        //TODO Correct eye heights and hitboxes
        PUP(0, 1.1F, 3, 0.5F, 1.0F),
        TEENAGE(1, 0.0F, 5, 0.5F, 1.0F),
        MATURE(3, 0.0F, 9, 0.5F, 1.0F),
        ELDERLY(2, 0.0F, 7, 0.5F, 1.0F);

        /**
         * Shows which growth stage is strongest, for Enderhound strength comparison.
         * Pup < Teenage < Elderly < Mature
         */
        private final int strengthLevel;
        private final float eyeHeight;
        private final int averageTPLength;
        private final float bBoxWidth;
        private final float bBoxHeight;

        GrowthStage(int strengthLevel, float eyeHeight, int averTPLen, float width, float height) {
            this.strengthLevel = strengthLevel;
            this.eyeHeight = eyeHeight;
            this.averageTPLength = averTPLen;
            this.bBoxWidth = width;
            this.bBoxHeight = height;
        }

        //Getters below here
        public int getStrengthLevel(){return strengthLevel;}
        public float getEyeHeight(){ return eyeHeight;}
        public int getAverageTPLength() { return averageTPLength; }
        public float getBoxWidth() { return bBoxWidth; }
        public float getBoxHeight() { return bBoxHeight; }
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
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
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

        this.isJumping = false;
        super.livingTick();
    }

    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
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

    /**
     * x, y and z are of the ultimate target. This will do a single TP roughly towards it, covering roughly the jump
     * length of the Enderhound.
     */
    public void singleTP(double x, double y, double z) {
        double angleTowardsPoint = Math.atan2(z - this.posZ, x - this.posX);
        angleTowardsPoint += this.rand.nextBoolean() ? Math.PI / 4 : -Math.PI / 4;
        angleTowardsPoint += this.rand.nextBoolean() ? Math.PI / 8 : -Math.PI / 8;
        int jumpDist = this.stage.getAverageTPLength();
        this.attemptTeleport(x + jumpDist * Math.cos(angleTowardsPoint), y, z + jumpDist * Math.sin(angleTowardsPoint), true);
    }

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