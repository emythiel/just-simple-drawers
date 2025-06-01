package dev.emythiel.justsimpledrawers.registry;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.block.entity.CompactingBlockEntity;
import dev.emythiel.justsimpledrawers.block.entity.DrawerBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, JustSimpleDrawers.MOD_ID);

    public static final Supplier<BlockEntityType<DrawerBlockEntity>> DRAWER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
        "drawer_block_entity",
        () -> new BlockEntityType<>(
            DrawerBlockEntity::new,
            Set.of(
                ModBlocks.SINGLE_DRAWER.get(),
                ModBlocks.DOUBLE_DRAWER.get(),
                ModBlocks.QUAD_DRAWER.get()
            ),
            null
        )
    );

    public static final Supplier<BlockEntityType<CompactingBlockEntity>> COMPACTING_BLOCK_ENTITY = BLOCK_ENTITIES.register(
        "compacting_block_entity",
        () -> new BlockEntityType<>(
            CompactingBlockEntity::new,
            Set.of(
                ModBlocks.COMPACTING_DRAWER.get()
            ),
            null
        )
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
