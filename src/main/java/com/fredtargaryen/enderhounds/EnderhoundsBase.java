/**
 * TODO
 * Regen (function of light level and y coord)
 * Teenage Model
 * Adult Model
 * Elderly Model
 * Fix hitboxes (check 1)
 * Fix eye heights (check 1)
 * AI for getting hit
 * AI for ranged powers
 * Pelt armour
 * Humans and Endermen as leaders
 * Sound
 * Banner
 *
 * COMBAT LOGIC
 * Aggressive:
 * -At >20% health, will take 3 hits before tp'ing
 * -Stares and shakes very briefly before attacking
 * Cunning:
 * -Tp's away after one or two hits
 * -Can teleport behind target
 * -Stares and shakes a random amount of time before
 * attacking
 * -Occasionally pretends to give in at <50% health
 * Weak:
 * -Teleports far away on hit
 * -Stares for a long time before attacking
 *
 * PACK LOGIC
 * Aggressive:
 * -Will challenge at any health level but back down quickly
 * -Will always fight and never surrender when challenged
 * Cunning:
 * -Will challenge when the leader's health is low
 * -Will surrender at <20% health if challenged
 * Weak:
 * -Will never challenge
 * -Will always surrender if challenged
 *
 * POWERS
 * Ball: Long range straight line projectile
 * Doppel: Create an identical copy of the Enderhound that moves in a different direction and dies in one hit
 * Jaws: Pick up hostile mobs and place them near the target (these ones can bring food and items to leaders in peacetime)
 */
package com.fredtargaryen.enderhounds;

import com.fredtargaryen.enderhounds.entity.EnderhoundEntity;
import com.fredtargaryen.enderhounds.entity.PupEnderhoundEntity;
import com.fredtargaryen.enderhounds.entity.capability.DefaultLeadImplFactory;
import com.fredtargaryen.enderhounds.entity.capability.ILeadPackCapability;
import com.fredtargaryen.enderhounds.entity.capability.LeadCapStorage;
import com.fredtargaryen.enderhounds.proxy.ClientProxy;
import com.fredtargaryen.enderhounds.proxy.IProxy;
import com.fredtargaryen.enderhounds.proxy.ServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

@Mod(value = DataReference.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnderhoundsBase {
    //Declare Items here
    @ObjectHolder("pelt")
    public static Item PELT;
    @ObjectHolder("pup_spawn_egg")
    public static Item PUP_EGG;

    //Declare EntityTypes here
    @ObjectHolder("pup")
    public static EntityType PUP_TYPE;
    private static EntityType PUP_EARLYREG;
    @ObjectHolder("teenage")
    public static EntityType TEENAGE_TYPE;
    @ObjectHolder("mature")
    public static EntityType MATURE_TYPE;
    @ObjectHolder("elderly")
    public static EntityType ELDERLY_TYPE;

    //Declare all items here

    // Says where the client and server 'proxy' code is loaded.
    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public EnderhoundsBase() {
        //Register the config
        //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG_SPEC);

        //Event bus
        IEventBus loadingBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        loadingBus.addListener(this::postRegistration);
        loadingBus.addListener(this::clientSetup);

        // Register ourselves for server, registry and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //Load the config
        //Config.loadConfig(FMLPaths.CONFIGDIR.get().resolve(DataReference.MODID + ".toml"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        PUP_EARLYREG = EntityType.Builder.create((type, world) -> new PupEnderhoundEntity(world), EntityClassification.MONSTER)
                .size(EnderhoundEntity.GrowthStage.PUP.getBoxWidth(), EnderhoundEntity.GrowthStage.PUP.getBoxHeight())
                .setTrackingRange(64)
                .setUpdateInterval(10)
                .setShouldReceiveVelocityUpdates(true)
                .build(DataReference.MODID)
                .setRegistryName("pup");
        event.getRegistry().registerAll(
                new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName("pelt"),
                new SpawnEggItem(PUP_EARLYREG, 0, 1447446, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("pup_spawn_egg")
        );
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                PUP_EARLYREG
//                EntityType.Builder.create(TeenageEnderhoundEntity.class, TeenageEnderhoundEntity::new)
//                        .tracker(64, 10, true)
//                        .build(DataReference.MODID)
//                        .setRegistryName("teenage"),
//                EntityType.Builder.create(MatureEnderhoundEntity.class, MatureEnderhoundEntity::new)
//                        .tracker(64, 10, true)
//                        .build(DataReference.MODID)
//                        .setRegistryName("mature"),
//                EntityType.Builder.create(ElderlyEnderhoundEntity.class, ElderlyEnderhoundEntity::new)
//                        .tracker(64, 10, true)
//                        .build(DataReference.MODID)
//                        .setRegistryName("elderly")
        );
    }

    public void clientSetup(FMLClientSetupEvent event) {
        proxy.registerRenderers();
    }

    /**
     * Called after all registry events. Runs in parallel with other SetupEvent handlers.
     * @param event
     */
    public void postRegistration(FMLCommonSetupEvent event) {
        //Register Capabilities
        CapabilityManager.INSTANCE.register(ILeadPackCapability.class, new LeadCapStorage(), new DefaultLeadImplFactory());

        //Add spawns
        EntitySpawnPlacementRegistry.register(PUP_TYPE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.WORLD_SURFACE, null);

        //EntityRegistry.addSpawn(PupEnderhoundEntity.class, 4, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(TeenageEnderhoundEntity.class, 3, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(MatureEnderhoundEntity.class, 2, 1, 3, EnumCreatureType.MONSTER);
        //EntityRegistry.addSpawn(ElderlyEnderhoundEntity.class, 1, 1, 3, EnumCreatureType.MONSTER);
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
        if (e instanceof PlayerEntity || e instanceof EnderhoundEntity || e instanceof EndermanEntity) {
            evt.addCapability(DataReference.LEAD_CAP_LOCATION,
                    //Full name ICapabilitySerializableProvider
                    new ICapabilitySerializable<CompoundNBT>() {
                        ILeadPackCapability inst = LEADCAP.getDefaultInstance();

                        @Override
                        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
                            return capability == LEADCAP ? LazyOptional.of(() -> (T) inst) : LazyOptional.empty();
                        }

                        @Override
                        public CompoundNBT serializeNBT() {
                            return (CompoundNBT) LEADCAP.getStorage().writeNBT(LEADCAP, inst, null);
                        }

                        @Override
                        public void deserializeNBT(CompoundNBT nbt) {
                            LEADCAP.getStorage().readNBT(LEADCAP, inst, null, nbt);
                        }
                    }
            );
        }
    }
}
