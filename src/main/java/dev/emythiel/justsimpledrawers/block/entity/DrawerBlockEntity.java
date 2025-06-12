package dev.emythiel.justsimpledrawers.block.entity;

import dev.emythiel.justsimpledrawers.block.DrawerBlock;
import dev.emythiel.justsimpledrawers.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DrawerBlockEntity extends StorageBlockEntity {
    public DrawerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DRAWER_BLOCK_ENTITY.get(), pos, blockState, getSlotCount(blockState));
    }

    private static int getSlotCount(BlockState state) {
        if (state.getBlock() instanceof DrawerBlock drawer) {
            return drawer.slots;
        }
        return 1; // Default
    }
}
