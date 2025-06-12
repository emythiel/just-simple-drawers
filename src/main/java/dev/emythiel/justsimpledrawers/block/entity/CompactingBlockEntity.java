package dev.emythiel.justsimpledrawers.block.entity;

import dev.emythiel.justsimpledrawers.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CompactingBlockEntity extends StorageBlockEntity {
    public CompactingBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COMPACTING_BLOCK_ENTITY.get(), pos, blockState, 3);
    }
}
