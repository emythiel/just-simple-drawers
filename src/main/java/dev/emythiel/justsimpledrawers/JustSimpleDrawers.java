package dev.emythiel.justsimpledrawers;

import dev.emythiel.justsimpledrawers.config.ClientConfig;
import dev.emythiel.justsimpledrawers.config.ServerConfig;
import dev.emythiel.justsimpledrawers.registry.ModBlockEntities;
import dev.emythiel.justsimpledrawers.registry.ModBlocks;
import dev.emythiel.justsimpledrawers.registry.ModCreativeModeTabs;
import dev.emythiel.justsimpledrawers.registry.ModItems;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(JustSimpleDrawers.MOD_ID)
public class JustSimpleDrawers {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "justsimpledrawers";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public JustSimpleDrawers(IEventBus modEventBus, ModContainer modContainer) {
        // Register mod configuration
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        modEventBus.addListener(this::onConfigLoad);
        // Create a configuration screen
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register creative tab
        ModCreativeModeTabs.register(modEventBus);

        // Register items and blocks
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // Register block entities
        ModBlockEntities.register(modEventBus);
    }

    private void onConfigLoad(ModConfigEvent event) {
        if (event.getConfig().getSpec() == ClientConfig.SPEC) {
            ClientConfig.load();
        } else if (event.getConfig().getSpec() == ServerConfig.SPEC) {
            ServerConfig.load();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // TODO: Remove this method if not used in final build
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // TODO: Remove this method if not used in final build
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
