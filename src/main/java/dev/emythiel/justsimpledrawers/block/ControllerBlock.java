package dev.emythiel.justsimpledrawers.block;

import dev.emythiel.justsimpledrawers.block.base.BaseBlock;
import dev.emythiel.justsimpledrawers.block.entity.ControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ControllerBlock extends BaseBlock implements EntityBlock {
    public ControllerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ControllerBlockEntity(pos, state);
    }
}
