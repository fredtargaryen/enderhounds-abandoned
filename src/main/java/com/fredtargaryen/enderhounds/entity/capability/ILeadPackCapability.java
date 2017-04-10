package com.fredtargaryen.enderhounds.entity.capability;

import net.minecraft.entity.EntityLivingBase;

public interface ILeadPackCapability
{
    boolean isStrongerThan(EntityLivingBase elb);
}
