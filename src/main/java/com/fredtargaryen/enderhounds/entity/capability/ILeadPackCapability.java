package com.fredtargaryen.enderhounds.entity.capability;

import net.minecraft.entity.EntityLivingBase;

/**
 * Feel free to use this capability if you want your custom mob to be able
 * to lead a pack of Enderhounds!
 */
public interface ILeadPackCapability {
    boolean isStrongerThan(EntityLivingBase elb);
}
