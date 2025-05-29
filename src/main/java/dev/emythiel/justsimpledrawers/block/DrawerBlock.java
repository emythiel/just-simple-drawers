package dev.emythiel.justsimpledrawers.block;

import dev.emythiel.justsimpledrawers.block.base.StorageBlock;
import dev.emythiel.justsimpledrawers.block.entity.DrawerBlockEntity;
import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;


public class DrawerBlock extends StorageBlock<DrawerBlockEntity> {
    public final int slots;

    public DrawerBlock(Properties properties, int slots) {
        super(properties);
        this.slots = slots;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrawerBlockEntity(pos, state);
    }

    // Insert items
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
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

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DrawerBlockEntity drawer) {
                ItemStackHandler handler = drawer.inventory;
                boolean isCrouching = player.isCrouching();

                int amountToExtract = isCrouching ? handler.getStackInSlot(0).getCount() : 1;

                if (amountToExtract > 0) {
                    ItemStack extracted = handler.extractItem(0, amountToExtract, false);
                    if (!extracted.isEmpty()) {
                        if (!player.addItem(extracted)) {
                            player.drop(extracted, false);
                        }
                    }
                }
            }
        }
    }
}
