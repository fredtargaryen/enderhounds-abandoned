/**
 * TODO
 * Registering eggs
 *
 * Port Pup Techne model to Tabula
 * (EntityAIFollowLeader) Teleporting on uneven ground (check)
 * Fix hitboxes (check 1)
 * Fix eye heights (check 1)
 *
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
 *
 * Powers:
 * * Ball
 * * Doppel
 * * Jaws
 */
package com.fredtargaryen.enderhounds;

import com.fredtargaryen.enderhounds.entity.*;
import com.fredtargaryen.enderhounds.entity.capability.DefaultLeadImplFactory;
import com.fredtargaryen.enderhounds.entity.capability.ILeadPackCapability;
import com.fredtargaryen.enderhounds.entity.capability.LeadCapStorage;
import com.fredtargaryen.enderhounds.proxy.ClientProxy;
import com.fredtargaryen.enderhounds.proxy.IProxy;
import com.fredtargaryen.enderhounds.proxy.ServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

@Mod(value=DataReference.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnderhoundsBase {
    //Declare EntityTypes here
    @ObjectHolder("pup")
    public static EntityType PUP_TYPE;
    @ObjectHolder("teenage")
    public static EntityType TEENAGE_TYPE;
    @ObjectHolder("mature")
    public static EntityType MATURE_TYPE;
    @ObjectHolder("elderly")
    public static EntityType ELDERLY_TYPE;

    //Declare all items here

    // Says where the client and server 'proxy' code is loaded.
    private static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public EnderhoundsBase() {
        //Register the config
        //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG_SPEC);

        //Event bus
        IEventBus loadingBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        loadingBus.addListener(this::postRegistration);

        // Register ourselves for server, registry and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //Load the config
        //Config.loadConfig(FMLPaths.CONFIGDIR.get().resolve(DataReference.MODID + ".toml"));
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                EntityType.Builder.create(EntityEnderhoundPup.class, EntityEnderhoundPup::new)
                        .tracker(64, 10, true)
                        .build(DataReference.MODID)
                        .setRegistryName("pup")
//                EntityType.Builder.create(EntityEnderhoundTeenage.class, EntityEnderhoundTeenage::new)
//                        .tracker(64, 10, true)
//                        .build(DataReference.MODID)
//                        .setRegistryName("teenage"),
//                EntityType.Builder.create(EntityEnderhoundMature.class, EntityEnderhoundMature::new)
//                        .tracker(64, 10, true)
//                        .build(DataReference.MODID)
//                        .setRegistryName("mature"),
//                EntityType.Builder.create(EntityEnderhoundElderly.class, EntityEnderhoundElderly::new)
//                        .tracker(64, 10, true)
//                        .build(DataReference.MODID)
//                        .setRegistryName("elderly")
        );
    }

    /**
     * Called after all registry events. Runs in parallel with other SetupEvent handlers.
     * @param event
     */
    public void postRegistration(FMLCommonSetupEvent event) {
        //Register Capabilities
        CapabilityManager.INSTANCE.register(ILeadPackCapability.class, new LeadCapStorage(), new DefaultLeadImplFactory());

        //TODO Also change egg colour one day
        //EntityRegistry.registerEgg(pupRL, 0, 1447446);

        //Add spawns
        EntitySpawnPlacementRegistry.register(PUP_TYPE, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.WORLD_SURFACE, null);

        //EntityRegistry.addSpawn(EntityEnderhoundPup.class, 4, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundTeenage.class, 3, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundMature.class, 2, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(EntityEnderhoundElderly.class, 1, 1, 3, EnumCreatureType.MONSTER);
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
    public void addCapsToNewEntity(AttachCapabilitiesEvent<Entity> evt) {
        Entity e = evt.getObject();
        if (e instanceof EntityPlayer || e instanceof EntityEnderhound || e instanceof EntityEnderman) {
            evt.addCapability(DataReference.LEAD_CAP_LOCATION,
                    //Full name ICapabilitySerializableProvider
                    new ICapabilitySerializable<NBTTagCompound>() {
                        ILeadPackCapability inst = LEADCAP.getDefaultInstance();

                        @Override
                        public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
                            return capability == LEADCAP ? LazyOptional.of(() -> (T) inst) : LazyOptional.empty();
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
