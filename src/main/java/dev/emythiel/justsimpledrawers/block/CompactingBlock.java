package dev.emythiel.justsimpledrawers.block;

import dev.emythiel.justsimpledrawers.block.base.StorageBlock;
import dev.emythiel.justsimpledrawers.block.entity.CompactingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CompactingBlock extends StorageBlock<CompactingBlockEntity> {
    public final int slots;

    public CompactingBlock(Properties properties, int slots) {
        super(properties, slots);
        this.slots = slots;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CompactingBlockEntity(pos, state);
    }
}
