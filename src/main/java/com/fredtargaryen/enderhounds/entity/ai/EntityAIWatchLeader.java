package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIWatchLeader extends EntityAIBase
{
    private EntityEnderhound watcher;
    private EntityLivingBase leader;

    public EntityAIWatchLeader(EntityEnderhound watcher)
    {
        this.watcher = watcher;
        //Set to 2 so that the hound can't watch the nearest entity and the leader at the same time
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute() {
        this.leader = this.watcher.getLeader();
        return this.leader != null;
    }

    @Override
    public boolean continueExecuting()
    {
        return this.shouldExecute() && this.leader.isEntityAlive();
    }

    @Override
    public void updateTask()
    {
        this.watcher.getLookHelper().setLookPosition(leader.posX, leader.posY + (double)leader.getEyeHeight(), leader.posZ, 10.0F, (float)watcher.getVerticalFaceSpeed());
    }
}