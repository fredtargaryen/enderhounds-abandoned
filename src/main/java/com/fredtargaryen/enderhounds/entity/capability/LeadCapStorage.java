package com.fredtargaryen.enderhounds.entity.capability;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * The storage class for ILeadPackCapability.
 * Feel free to use this capability if you want your custom mob to be able
 * to lead a pack of Enderhounds!
 */
public class LeadCapStorage implements Capability.IStorage<ILeadPackCapability> {
    @Override
    public INBTBase writeNBT(Capability<ILeadPackCapability> capability, ILeadPackCapability instance, EnumFacing side) {
        //NBTTagCompound comp = new NBTTagCompound();
        //comp.setDouble("pitch", instance.getRotPitch());
        //return comp;
        return new NBTTagCompound();
    }

    @Override
    public void readNBT(Capability<ILeadPackCapability> capability, ILeadPackCapability instance, EnumFacing side, INBTBase nbt) {
        //NBTTagCompound comp = (NBTTagCompound) nbt;
        //instance.setRotPitch(comp.getDouble("pitch"));
    }
}
