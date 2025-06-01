package dev.emythiel.justsimpledrawers.registry;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.block.CompactingBlock;
import dev.emythiel.justsimpledrawers.block.ControllerBlock;
import dev.emythiel.justsimpledrawers.block.DrawerBlock;
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

    // Basic Drawer Blocks
    public static final DeferredBlock<Block> SINGLE_DRAWER = registerBlock("single_drawer",
        () -> new DrawerBlock(getDrawerProperties(), 1));
    public static final DeferredBlock<Block> DOUBLE_DRAWER = registerBlock("double_drawer",
        () -> new DrawerBlock(getDrawerProperties(), 2));
    public static final DeferredBlock<Block> QUAD_DRAWER = registerBlock("quad_drawer",
        () -> new DrawerBlock(getDrawerProperties(), 4));

    // Compacting Drawer Block
    public static final DeferredBlock<Block> COMPACTING_DRAWER = registerBlock("compacting_drawer", () -> new CompactingBlock(getDrawerProperties(), 3));

    // Drawer Controller Block
    public static final DeferredBlock<Block> CONTROLLER = registerBlock("controller", () -> new ControllerBlock(getDrawerProperties()));

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

    // Block propertiers helper
    private static BlockBehaviour.Properties getDrawerProperties() {
        return BlockBehaviour.Properties.of()
            .strength(3f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK);
    }
}
