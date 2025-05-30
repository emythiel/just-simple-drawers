package dev.emythiel.justsimpledrawers.block.base;

import dev.emythiel.justsimpledrawers.block.entity.DrawerBlockEntity;
import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class StorageBlock<T extends StorageBlockEntity> extends BaseBlock implements EntityBlock {
    public StorageBlock(Properties properties) {
        super(properties);
    }

    // Insert items
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (isFrontFace(state, hitResult.getDirection())) {
            if (!level.isClientSide) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof DrawerBlockEntity drawer) {
                    ItemStackHandler handler = drawer.inventory;
                    ItemStack stackInHand = player.getItemInHand(hand);

                    if (!stackInHand.isEmpty()) {
                        ItemStack remainder = handler.insertItem(0, stackInHand, false);
                        player.setItemInHand(hand, remainder);
                        return ItemInteractionResult.CONSUME;
                    }
                }
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }
}
