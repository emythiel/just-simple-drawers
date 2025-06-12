package dev.emythiel.justsimpledrawers.events;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.block.base.StorageBlock;
import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import dev.emythiel.justsimpledrawers.storage.DrawerSlot;
import dev.emythiel.justsimpledrawers.util.RaycastUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

@EventBusSubscriber(modid = JustSimpleDrawers.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        // Check if block is StorageBlock
        if (block instanceof StorageBlock<?> storageBlock) {
            // Calculate hit result
            BlockHitResult hitResult = RaycastUtil.getTarget(player, pos);

            // Check if front face
            if (!storageBlock.isFrontFace(state, hitResult.getDirection())) {
                // For non-front faces in creative mode, allow normal breaking
                if (player.getAbilities().instabuild) {
                    return;
                }
                // For survival, prevent breaking when clicking on non-front
                return;
            }

            // For front face in creative mode, withdraw instead of break
            if (player.getAbilities().instabuild) {
                event.setCanceled(true);
            }

            // Only handle server-side logic
            if (level.isClientSide) return;

            // Calculate hit position
            Vec2 uv = RaycastUtil.calculateFrontFaceLocation(pos, hitResult.getLocation(),
                state.getValue(HorizontalDirectionalBlock.FACING), hitResult.getDirection());

            if (uv == null) return;

            int slotIndex = storageBlock.getSlotIndex(storageBlock.slots, uv);
            if (slotIndex == -1) return;

            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof StorageBlockEntity storageBE)) return;

            if (slotIndex >= storageBE.slots.length) return;

            DrawerSlot slot = storageBE.slots[slotIndex];
            if (slot.getStoredItem().isEmpty()) return;

            // Withdraw items with stack size awareness
            ItemStack withdrawn = slot.withdrawItem(player, player.isCrouching());

            if (!withdrawn.isEmpty()) {
                // Add to players inventory
                if (!player.getInventory().add(withdrawn)) {
                    // Fallback just in case
                    player.drop(withdrawn, false);
                }
                storageBE.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);

                // Play sound effect
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.2f, 0.2f);
            }
        }
    }
}
