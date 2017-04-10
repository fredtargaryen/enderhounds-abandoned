package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPackLogic extends EntityAIBase
{
    private EntityEnderhound hound;

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
        this.hound.refreshLeader();
    }
}
