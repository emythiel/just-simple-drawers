package dev.emythiel.justsimpledrawers.block.entity;

import dev.emythiel.justsimpledrawers.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ControllerBlockEntity extends BaseBlockEntity {
    public ControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONTROLLER_BLOCK_ENTITY.get(), pos, state);
    }
}
