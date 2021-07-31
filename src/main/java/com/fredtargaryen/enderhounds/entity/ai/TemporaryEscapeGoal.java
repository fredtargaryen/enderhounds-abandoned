package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.entity.EnderhoundEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class TemporaryEscapeGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private EnderhoundEntity thisEnderhound;
    private LivingEntity target;
    private int ticksToSpendFleeing;
    private int ticksLeftForFleeing;
    private int hitCountBeforeFleeing;

    public TemporaryEscapeGoal(EnderhoundEntity entity, Class<LivingEntity> p_i50313_2_, boolean p_i50313_3_) {
        super(entity, p_i50313_2_, p_i50313_3_);
        this.thisEnderhound = entity;
        this.hitCountBeforeFleeing = 0;
    }

    /**
     * Returns whether the Goal should begin execution.
     * Return true on reaction to a hit, if the hound should start teleporting away
     */
    public boolean shouldExecute() {
        if(this.thisEnderhound.getAttackTarget() != null) {
            switch(this.thisEnderhound.personality) {
                case WEAK:
                    //Flee for four seconds
                    this.ticksToSpendFleeing = 80;
                    break;
                case CUNNING:
                    //Flee for 2 seconds
                    this.ticksToSpendFleeing = 40;
                    break;
                case AGGRESSIVE:
                    //Flee for 1 second
                    this.ticksToSpendFleeing = 20;
                    break;
            }
            this.target = this.thisEnderhound.getLastAttackedEntity();
            switch(this.thisEnderhound.personality) {
                case AGGRESSIVE:
                    //Tank three hits before fleeing, if fairly healthy
                    if (this.thisEnderhound.getHealthRatio() > 0.2) {
                        ++this.hitCountBeforeFleeing;
                        if (this.hitCountBeforeFleeing == 3) {
                            this.hitCountBeforeFleeing = 0;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        //Not healthy - flee after each hit
                        this.hitCountBeforeFleeing = 0;
                        return true;
                    }
                case CUNNING:
                    if (this.hitCountBeforeFleeing == 0) {
                        //Possibly sneak in another hit
                        if (this.thisEnderhound.world.rand.nextFloat() < 0.1f) {
                            ++this.hitCountBeforeFleeing;
                            return false;
                        } else {
                            this.hitCountBeforeFleeing = 0;
                            return true;
                        }
                    } else {
                        //Got hit once; enough to flee
                        this.hitCountBeforeFleeing = 0;
                        return true;
                    }
                case WEAK:
                    //Always flee
                    return true;
            }
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        //At low health spend nearly double the time away
        this.ticksLeftForFleeing = (int) (this.ticksToSpendFleeing + this.ticksToSpendFleeing * (1f - this.thisEnderhound.getHealthRatio()));
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.target != null && this.target.isAlive() && this.ticksLeftForFleeing > 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        --this.ticksLeftForFleeing;
        if((this.ticksToSpendFleeing - this.ticksLeftForFleeing) % this.thisEnderhound.stage.getTpInterval() == 0) {
            this.thisEnderhound.teleportOnceTowards(EnderhoundEntity.getBlockPosAwayFrom(this.target.getPosition(), this.thisEnderhound.getPosition()));
        }
        super.tick();
    }
}
