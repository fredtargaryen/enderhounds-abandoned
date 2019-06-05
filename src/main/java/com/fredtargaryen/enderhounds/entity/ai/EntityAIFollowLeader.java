package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.DataReference;
import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFollowLeader extends EntityAIBase {
    private EntityEnderhound follower;
    private EntityLivingBase leader;

    private double distanceFromLeader;

    public EntityAIFollowLeader(EntityEnderhound follower)
    {
        this.follower = follower;
    }

    @Override
    public boolean shouldExecute() {
        this.leader = this.follower.getLeader();
        return this.leader != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        this.distanceFromLeader = this.follower.getDistance(this.leader.posX, this.leader.posY, this.leader.posZ);
        return this.leader.isAlive() && this.distanceFromLeader > DataReference.HERDRANGEFOLLOWDIST;
    }

    @Override
    public void tick() {
        if(this.distanceFromLeader > DataReference.HERDRANGEMAXDIST) {
            this.follower.getLookHelper().setLookPosition(leader.posX, leader.posY + (double)leader.getEyeHeight(), leader.posZ, 10.0F, (float)follower.getVerticalFaceSpeed());
            this.follower.singleTP(this.leader.posX, this.leader.posY, this.leader.posZ);
        }
        else {
            this.follower.getMoveHelper().setMoveTo(this.leader.posX, this.leader.posY, this.leader.posZ, this.follower.getAIMoveSpeed());
        }
    }
}