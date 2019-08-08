package com.fredtargaryen.enderhounds.entity.ai;

import com.fredtargaryen.enderhounds.DataReference;
import com.fredtargaryen.enderhounds.entity.EnderhoundEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class FollowLeaderGoal extends Goal {
    private EnderhoundEntity follower;
    private LivingEntity leader;

    private double distanceFromLeader;

    public FollowLeaderGoal(EnderhoundEntity follower)
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
        this.distanceFromLeader = this.follower.getDistance(this.leader);
        return this.leader.isAlive() && this.distanceFromLeader > DataReference.HERDRANGEFOLLOWDIST;
    }

    @Override
    public void tick() {
        if(this.distanceFromLeader > DataReference.HERDRANGEMAXDIST) {
            this.follower.getLookController().setLookPosition(leader.posX, leader.posY + (double)leader.getEyeHeight(), leader.posZ, 10.0F, (float)follower.getVerticalFaceSpeed());
            this.follower.singleTP(this.leader.posX, this.leader.posY, this.leader.posZ);
        }
        else {
            this.follower.getMoveHelper().setMoveTo(this.leader.posX, this.leader.posY, this.leader.posZ, this.follower.getAIMoveSpeed());
        }
    }
}