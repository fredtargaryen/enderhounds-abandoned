package com.fredtargaryen.enderhounds.entity.capability;

import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.concurrent.Callable;

/**
 * The default implementation of ILeadPackCapability.
 * Feel free to use this capability if you want your custom mob to be able
 * to lead a pack of Enderhounds!
 */
public class DefaultLeadImplFactory implements Callable<ILeadPackCapability>
{
    public ILeadPackCapability call()
    {
        return new DefaultLeadImpl();
    }

    private class DefaultLeadImpl implements ILeadPackCapability
    {
        public DefaultLeadImpl()
        {

        }

        public boolean isStrongerThan(EntityLivingBase entity)
        {
            return entity.getHealth() < 10.0F;
        }
    }
}