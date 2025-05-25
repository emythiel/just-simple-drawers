package dev.emythiel.justsimpledrawers.item;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JustSimpleDrawers.MOD_ID);

    public static final Supplier<CreativeModeTab> JUSTSIMPLEDRAWERS_TAB = CREATIVE_MODE_TAB.register("justsimpledrawers_tab",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.SINGLE_DRAWER.get()))
            .title(Component.translatable("creativetab.justsimpledrawers"))
            .displayItems((params, output) -> {
                output.accept(ModBlocks.SINGLE_DRAWER.get());
                output.accept(ModBlocks.DOUBLE_DRAWER.get());
                output.accept(ModBlocks.QUAD_DRAWER.get());
                output.accept(ModBlocks.COMPACTING_DRAWER.get());
                output.accept(ModBlocks.CONTROLLER.get());

                output.accept(ModItems.UPGRADE_BASE.get());
                output.accept(ModItems.UPGRADE_T1.get());
                output.accept(ModItems.UPGRADE_T2.get());
                output.accept(ModItems.UPGRADE_T3.get());
                output.accept(ModItems.UPGRADE_T4.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
