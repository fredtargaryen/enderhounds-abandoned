package com.fredtargaryen.enderhounds.entity.capability;

import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.concurrent.Callable;

/**
 * The implementation of ILeadPackCapability for all official Rocket Squids.
 * This implementation was designed for exclusive use with this mod;
 * correct operation is not guaranteed in any other context!
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