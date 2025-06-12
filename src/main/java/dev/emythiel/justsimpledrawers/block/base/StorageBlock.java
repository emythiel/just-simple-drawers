package dev.emythiel.justsimpledrawers.block.base;

import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import dev.emythiel.justsimpledrawers.storage.DrawerSlot;
import dev.emythiel.justsimpledrawers.util.DrawerInteractionStatusManager;
import dev.emythiel.justsimpledrawers.util.RaycastUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;

public abstract class StorageBlock<T extends StorageBlockEntity> extends BaseBlock implements EntityBlock {
    public final int slots;

    public StorageBlock(Properties properties, int slots) {
        super(properties);
        this.slots = slots;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        // Check if front face is hit
        Vec2 uv = RaycastUtil.calculateFrontFaceLocation(pos, hitResult.getLocation(),
            state.getValue(HorizontalDirectionalBlock.FACING), hitResult.getDirection());

        // Client-side handling
        if (level.isClientSide) {
            return uv != null ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // Rest is server-side
        if (uv == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        // Check if frame is hit
        int slotIndex = getSlotIndex(this.slots, uv);
        if (slotIndex == -1) return ItemInteractionResult.SUCCESS;

        // Not correct block entity
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof StorageBlockEntity storageBE)) {
            return ItemInteractionResult.SUCCESS;
        }

        // Check if valid slot
        if (slotIndex >= storageBE.slots.length) {
            return ItemInteractionResult.SUCCESS;
        }

        DrawerSlot slot = storageBE.slots[slotIndex];
        ItemStack heldStack = player.getItemInHand(hand);

        // Check if double-right-click
        boolean isDoubleClick = DrawerInteractionStatusManager.isDoubleClick(player, pos, slotIndex);

        if (isDoubleClick) {
            // Bulk insertion for double-click
            if (slot.getStoredItem().isEmpty() && heldStack.isEmpty()) {
                // Nothing to insert
                return ItemInteractionResult.SUCCESS;
            }

            int totalInserted = 0;
            ItemStack template = slot.getStoredItem().isEmpty() ? heldStack : slot.getStoredItem();

            // Set stored item if slot is empty
            if (slot.getStoredItem().isEmpty()) {
                slot.setStoredItem(template.copyWithCount(1));
            }

            // Insert from players inventory
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stackInSlot = player.getInventory().getItem(i);

                if (!stackInSlot.isEmpty() && slot.canAccept(stackInSlot) && ItemStack.isSameItemSameComponents(template, stackInSlot)) {
                    int inserted = slot.insertItem(stackInSlot);
                    stackInSlot.shrink(inserted);
                    totalInserted += inserted;

                    if (slot.getRemainingSpace() == 0) break;
                }
            }

            if (totalInserted > 0) {
                storageBE.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
            }
            return ItemInteractionResult.SUCCESS;
        }

        // Handle insertion
        if (!heldStack.isEmpty() && slot.canAccept(heldStack)) {
            // If slot is empty, set the stored item type
            if (slot.getStoredItem().isEmpty()) {
                slot.setStoredItem(heldStack.copyWithCount(1));
            }

            // Insert items
            int inserted = slot.insertItem(heldStack);
            if (inserted > 0) {
                heldStack.shrink(inserted);
                storageBE.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    // Get the slot index
    public int getSlotIndex(int slots, Vec2 uv) {
        float min = 1.0F / 16.0F;
        float max = 15.0F / 16.0F;
        if (uv.x < min || uv.x > max || uv.y < min || uv.y > max) {
            return -1; // Hit frame, not drawer
        }
        // Map to [1,14] grid
        float px = uv.x * 15.0f;
        float py = uv.y * 15.0f;

        return switch (slots) {
            case 1 -> 0;
            case 2 -> {
                // Double Drawer Slots
                if (px >= 1 && px <= 14 && py >= 1 && py <= 6) yield 0;     // Top
                if (px >= 1 && px <= 14 && py >= 9 && py <= 14) yield 1;    // Bottom
                yield -1;
            }
            case 3 -> {
                // Compacting Drawer Slots
                if (px >= 5 && px <= 10 && py >= 1 && py <= 6) yield 0;     // Top
                if (px >= 1 && px <= 6 && py >= 9 && py <= 14) yield 1;     // Bottom left
                if (px >= 9 && px <= 14 && py >= 9 && py <= 14) yield 2;    // Bottom right
                yield -1;
            }
            case 4 -> {
                // Quad Drawer Slots
                if (px >= 1 && px <= 6 && py >= 1 && py <= 6) yield 0;      // Top left
                if (px >= 9 && px <= 14 && py >= 1 && py <= 6) yield 1;     // Top right
                if (px >= 1 && px <= 6 && py >= 9 && py <= 14) yield 2;     // Bottom left
                if (px >= 9 && px <= 14 && py >= 9 && py <= 14) yield 3;    // Bottom right
                yield -1;
            }
            default -> -1;
        };
    }

    public boolean isFrontFace(BlockState state, Direction clickedFace) {
        return state.getValue(HorizontalDirectionalBlock.FACING) == clickedFace;
    }
}
