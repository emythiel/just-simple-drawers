package dev.emythiel.justsimpledrawers.block;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(JustSimpleDrawers.MOD_ID);

    public static final DeferredBlock<Block> DRAWER_SINGLE = registerBlock("drawer_single",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> DRAWER_DOUBLE = registerBlock("drawer_double",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> DRAWER_QUAD = registerBlock("drawer_quad",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> DRAWER_COMPACTING = registerBlock("drawer_compacting",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> DRAWER_CONTROLLER = registerBlock("drawer_controller",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
