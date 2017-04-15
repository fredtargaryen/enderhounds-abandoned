/**
 * TODO
 * (EntityAIFollowLeader) Doesn't seem to attempt teleport (check)
 * --What about uneven ground
 * Fix hitboxes
 * Fix eye heights (see GrowthStage)
 * Less particles when less healthy
 * Regen (function of light level and y coord)
 * AI for getting hit
 * AI for ranged powers
 * Pelt
 * Armour
 * Humans and Endermen as leaders
 * Sound
 * Banner
 *
 * Other notes:
 * Pup - drop 0-1 pelt
 * Teenager - drop 0-2 pelt
 * Mature - drop 1-4 pelt
 * Elderly - drop 3-5 pelt
 *
 * Aggressive:
 * -At >20% health, will take 3 hits before tp'ing
 * -Stares and shakes very briefly before attacking
 * Cunning:
 * -On hit, teleports behind target
 * -Stares and shakes a random amount of time before
 * attacking
 * -Occasionally pretends to give in at <50% health
 * Weak:
 * -Teleports far away on hit
 * -Stares for a long time before attacking
 */
package com.fredtargaryen.enderhounds;

import com.fredtargaryen.enderhounds.entity.*;
import com.fredtargaryen.enderhounds.entity.capability.DefaultLeadImplFactory;
import com.fredtargaryen.enderhounds.entity.capability.ILeadPackCapability;
import com.fredtargaryen.enderhounds.entity.capability.LeadCapStorage;
import com.fredtargaryen.enderhounds.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid=DataReference.MODID, name=DataReference.MODNAME, version=DataReference.VERSION)
public class EnderhoundsBase
{
    @Mod.Instance(DataReference.MODID)
    public static EnderhoundsBase instance;

    /**
     * Declare all items here
     */
    /**
     * Says where the client and server 'proxy' code is loaded.
     */
    @SidedProxy(clientSide= DataReference.CLIENTPROXYPATH, serverSide= DataReference.SERVERPROXYPATH)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Register Capabilities
        CapabilityManager.INSTANCE.register(ILeadPackCapability.class, new LeadCapStorage(), new DefaultLeadImplFactory());
        MinecraftForge.EVENT_BUS.register(this);

        //Makes all packets to be used
        //PacketHandler.init();

        //Makes all items to be used

        //Registering items
        //GameRegistry.registerItem(floopowder1t, "floopowder_one");

        //Adding recipes

        //Register Entities with EntityRegistry
        //Last three params are for tracking: trackingRange, updateFrequency and sendsVelocityUpdates
        ResourceLocation pupRL = new ResourceLocation(DataReference.MODID+":pup");
        EntityRegistry.registerModEntity(pupRL, EntityEnderhoundPup.class, "enderhound0", 0, instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DataReference.MODID+":teenage"), EntityEnderhoundTeenage.class, "enderhound1", 1, instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DataReference.MODID+":mature"), EntityEnderhoundMature.class, "enderhound2", 2, instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DataReference.MODID+":elderly"), EntityEnderhoundElderly.class, "enderhound3", 3, instance, 64, 10, true);

        //Change egg colour one day
        EntityRegistry.registerEgg(pupRL, 0, 1447446);

        //Add spawns
        EntityRegistry.addSpawn(EntityEnderhoundPup.class, 100, 1, 1, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundPup.class, 4, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundTeenage.class, 3, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundMature.class, 2, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundElderly.class, 1, 1, 3, EnumCreatureType.MONSTER);

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        proxy.registerModels();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    ////////////////
    //CAPABILITIES//
    ////////////////

    /**
     * Allows the given entity to lead a pack of Enderhounds
     */
    @CapabilityInject(ILeadPackCapability.class)
    public static final Capability<ILeadPackCapability> LEADCAP = null;

    @SubscribeEvent
    public void addCapsToNewEntity(AttachCapabilitiesEvent.Entity evt) {
        Entity e = evt.getEntity();
        if (e instanceof EntityPlayer || e instanceof EntityEnderhound || e instanceof EntityEnderman) {
            evt.addCapability(DataReference.LEAD_CAP_LOCATION,
                    //Full name ICapabilitySerializableProvider
                    new ICapabilitySerializable<NBTTagCompound>() {
                        ILeadPackCapability inst = LEADCAP.getDefaultInstance();

                        @Override
                        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                            return capability == LEADCAP;
                        }

                        @Override
                        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                            return capability == LEADCAP ? LEADCAP.<T>cast(inst) : null;
                        }

                        @Override
                        public NBTTagCompound serializeNBT() {
                            return (NBTTagCompound) LEADCAP.getStorage().writeNBT(LEADCAP, inst, null);
                        }

                        @Override
                        public void deserializeNBT(NBTTagCompound nbt) {
                            LEADCAP.getStorage().readNBT(LEADCAP, inst, null, nbt);
                        }
                    }
            );
        }
    }
}
