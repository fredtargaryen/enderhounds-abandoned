package com.fredtargaryen.enderhounds.entity;

import com.fredtargaryen.enderhounds.DataReference;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class EntityEnderhound extends EntityMob implements Comparable<EntityEnderhound>
{
    public enum GrowthStage
    {
        //TODO Correct eye heights
        PUP(0, 0.45F),
        TEENAGE(1, 0.0F),
        MATURE(3, 0.0F),
        ELDERLY(2, 0.0F);

        /**
         * Shows which growth stage is strongest, for Enderhound strength comparison.
         * Pup < Teenage < Elderly < Mature
         */
        private final int strengthLevel;
        private final float eyeHeight;

        GrowthStage(int strengthLevel, float eyeHeight)
        {
            this.strengthLevel = strengthLevel;
            this.eyeHeight = eyeHeight;
        }

        //Getters below here
        public int getStrengthLevel(){return strengthLevel;}
        public float getEyeHeight(){ return eyeHeight;}
    }

    public enum Personality
    {
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

        Personality(int strengthLevel, float submit)
        {
            this.healthSubmitPercentage = submit;
            this.strengthLevel = strengthLevel;
        }

        //Getters below here
        public float getHealthSubmitPercentage(){return this.healthSubmitPercentage;}
        public int getStrengthLevel(){return this.strengthLevel;}
    }

    public enum Power
    {
        NONE,
        DOPPEL,
        BALL
    }

    public GrowthStage stage;
    public Personality personality;
    public Power power;
    protected boolean isShaking;
    private ArrayList<EntityEnderhound> nearbyHounds = new ArrayList<EntityEnderhound>();
    private EntityEnderhound leader = null;

    public EntityEnderhound(World world)
    {
        super(world);
        this.getNearbyHounds();
        this.resetAllegiance();
        this.stepHeight = 1.0F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchLeader(this));
        //TODO AI for following leaders
        this.tasks.addTask(8, new EntityAILookIdle(this));
        //TODO AI for getting hurt (reference: EntityAIHurtByTarget)
        //TODO AI for ranged powers and jumping at targets when close
        //TODO AI for finding a player (reference: EntityEnderman.AIFindPlayer)
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate()
        {
            public boolean func_179948_a(EntityEndermite p_179948_1_)
            {
                return p_179948_1_.isSpawnedByPlayer();
            }
            public boolean apply(Object p_apply_1_)
            {
                return this.func_179948_a((EntityEndermite)p_apply_1_);
            }
        }));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(70.0D);
    }

    /**
     * Handles transition between GrowthStages. All implementations of this (unless they can't grow anymore) should call
     * the super method, to prevent Enderhounds trying to interact with previous Enderhounds which don't exist anymore.
     */
    protected void grow()
    {
        for(EntityEnderhound e : this.nearbyHounds)
        {
            e.getNearbyHounds();
        }
    }

    /**
     * Decides the leader to follow based on the strength of all nearby Enderhounds (see compareTo).
     * TODO Later, should be able to follow humans and Enderman as well.
     */
    private void resetAllegiance()
    {
        Collections.sort(this.nearbyHounds);
        if(this.nearbyHounds.size() > 0) {
            this.leader = this.nearbyHounds.get(this.nearbyHounds.size() - 1);
        }
        else
        {
            this.leader = null;
        }
    }

    /**
     * Used for caller to check if an Enderhound is following its lead
     * @param caller The Enderhound that called this method
     * @return Whether or not this Enderhound is following the caller
     */
    public boolean isFollowingMe(EntityEnderhound caller)
    {
        return caller == this.leader;
    }

    /**
     * Assembles a list of all nearby Enderhounds within an area around this Enderhound.
     */
    protected void getNearbyHounds()
    {
        this.nearbyHounds.clear();
        for(Object o : this.world.getEntitiesWithinAABB(EntityEnderhound.class,
                new AxisAlignedBB(this.posX - 0.5, this.posY - 0.5, this.posZ - 0.5,
                        this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5)
                        .expand(DataReference.HERDRANGEXZ, DataReference.HERDRANGEY, DataReference.HERDRANGEXZ)))
        {
            if(o instanceof EntityEnderhound)
            {
                this.nearbyHounds.add((EntityEnderhound)o);
            }
        }
    }

    /**
     * Compares "natural" strength of two Enderhounds. The strongest Enderhound should be at the end of the list,
     * then that Enderhound can be assigned to the leader variable.
     * @param ee2 The Enderhound to compare this Enderhound with
     * @return Whether this is naturally stronger than ee2
     */
    public int compareTo(EntityEnderhound ee2)
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

    public class EntityAIWatchLeader extends EntityAIBase
    {
        private EntityEnderhound watcher;

        public EntityAIWatchLeader(EntityEnderhound watcher)
        {
            this.watcher = watcher;
            //Set to 2 so that the hound can't watch the nearest entity and the leader at the same time
            this.setMutexBits(2);
        }

        @Override
        public boolean shouldExecute() {
            return this.watcher.getLeader() != null;
        }

        @Override
        public boolean continueExecuting()
        {
            return this.shouldExecute() && this.watcher.getLeader().isEntityAlive();
        }

        @Override
        public void updateTask()
        {
            EntityLivingBase leader = this.watcher.getLeader();
            this.watcher.getLookHelper().setLookPosition(leader.posX, leader.posY + (double)leader.getEyeHeight(), leader.posZ, 10.0F, (float)watcher.getVerticalFaceSpeed());
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }

        this.isJumping = false;
        super.onLivingUpdate();
    }

    @Override
    protected void updateAITasks()
    {
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
        super.updateAITasks();
    }

    /**
     * Applies the given player interaction to this Entity.
     */
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        this.setAttackTarget(this.getAttackTarget() == null ? player : null);

        return super.processInteract(player, hand);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        switch(nbt.getInteger("personality"))
        {
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
        switch(nbt.getInteger("power"))
        {
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
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        switch(this.personality)
        {
            case CUNNING:
                nbt.setInteger("personality", 0);
                break;
            case AGGRESSIVE:
                nbt.setInteger("personality", 1);
                break;
            //The "weak" personality
            default:
                nbt.setInteger("personality", 2);
                break;
        }
        switch(this.power)
        {
            case BALL:
                nbt.setInteger("power", 0);
                break;
            case DOPPEL:
                nbt.setInteger("power", 1);
                break;
            //No power
            default:
                nbt.setInteger("power", 2);
                break;
        }
        return nbt;
    }

    //Simple getters
    public boolean isShaking() { return this.isShaking; }

    public EntityEnderhound getLeader() { return this.leader; }

    public float getEyeHeight()
    {
        return this.stage.getEyeHeight();
    }

    //TODO protected Item getDropItem(){return EnderhoundsBase.pelt;}
}
