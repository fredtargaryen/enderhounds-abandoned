package com.fredtargaryen.enderhounds.entity.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

/**
 * The storage class for ILeadPackCapability.
 * Feel free to use this capability if you want your custom mob to be able
 * to lead a pack of Enderhounds!
 */
public class LeadCapStorage implements Capability.IStorage<ILeadPackCapability> {
    @Override
    public INBT writeNBT(Capability<ILeadPackCapability> capability, ILeadPackCapability instance, Direction side) {
        //CompoundNBT comp = new CompoundNBT();
        //comp.setDouble("pitch", instance.getRotPitch());
        //return comp;
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<ILeadPackCapability> capability, ILeadPackCapability instance, Direction side, INBT nbt) {
        //CompoundNBT comp = (CompoundNBT) nbt;
        //instance.setRotPitch(comp.getDouble("pitch"));
    }
}
