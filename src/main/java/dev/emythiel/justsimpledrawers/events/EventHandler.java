/*
package dev.emythiel.justsimpledrawers.events;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.block.base.StorageBlock;
import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
            // Return if not front
            if (!storageBlock.isFrontFace(state, event.getFace())) return;

            // Return if not logic server
            if (level.isClientSide) return;

            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof StorageBlockEntity storageBE)) return;

            ItemStackHandler handler = storageBE.inventory;
            boolean isCrouching = player.isCrouching();
            // Extract stack if crouching, else just 1
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
*/
