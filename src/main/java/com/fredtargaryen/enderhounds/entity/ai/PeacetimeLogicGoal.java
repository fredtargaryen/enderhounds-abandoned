package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.entity.EnderhoundEntity;
import net.minecraft.entity.ai.goal.Goal;

public class PeacetimeLogicGoal extends Goal {
    private EnderhoundEntity hound;
    private short ticksUntilRefreshLeader = 100;

    public PeacetimeLogicGoal(EnderhoundEntity hound) {
        this.hound = hound;
    }

    @Override
    public boolean shouldExecute() {
        return this.hound.getAttackTarget() != null;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute();
    }

    @Override
    public void tick() {
        if(this.ticksUntilRefreshLeader > 0) {
            --this.ticksUntilRefreshLeader;
        }
        else {
            this.ticksUntilRefreshLeader = 1200;
            this.hound.refreshLeader();
        }
    }
}
