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
            .icon(() -> new ItemStack(ModBlocks.DRAWER_SINGLE.get()))
            .title(Component.translatable("creativetab.justsimpledrawers"))
            .displayItems((params, output) -> {
                output.accept(ModBlocks.DRAWER_SINGLE.get());
                output.accept(ModBlocks.DRAWER_DOUBLE.get());
                output.accept(ModBlocks.DRAWER_QUAD.get());
                output.accept(ModBlocks.DRAWER_COMPACTING.get());

                output.accept(ModItems.DRAWER_UPGRADE_BASE.get());
                output.accept(ModItems.DRAWER_UPGRADE_T1.get());
                output.accept(ModItems.DRAWER_UPGRADE_T2.get());
                output.accept(ModItems.DRAWER_UPGRADE_T3.get());
                output.accept(ModItems.DRAWER_UPGRADE_T4.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
