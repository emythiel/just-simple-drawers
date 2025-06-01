package dev.emythiel.justsimpledrawers.block;

import dev.emythiel.justsimpledrawers.block.base.StorageBlock;
import dev.emythiel.justsimpledrawers.block.entity.DrawerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class DrawerBlock extends StorageBlock<DrawerBlockEntity> {
    public final int slots;

    public DrawerBlock(Properties properties, int slots) {
        super(properties, slots);
        this.slots = slots;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrawerBlockEntity(pos, state);
    }
}
