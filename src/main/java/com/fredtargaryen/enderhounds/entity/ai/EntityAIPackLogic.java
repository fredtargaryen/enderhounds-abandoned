package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPackLogic extends EntityAIBase
{
    private EntityEnderhound hound;
    private short ticksUntilRefreshLeader = 100;

    public EntityAIPackLogic(EntityEnderhound hound)
    {
        this.hound = hound;
    }

    @Override
    public boolean shouldExecute() {
        return this.hound.getAttackTarget() == null;
    }

    @Override
    public boolean continueExecuting()
    {
        return this.shouldExecute();
    }

    @Override
    public void updateTask()
    {
        if(this.ticksUntilRefreshLeader > 0)
        {
            --this.ticksUntilRefreshLeader;
        }
        else
        {
            this.ticksUntilRefreshLeader = 1200;
            this.hound.refreshLeader();
        }
    }
}
