package dev.emythiel.justsimpledrawers.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class DrawerInteractionStatusManager {
    // Double-click time (in ticks)
    public static final int DOUBLE_CLICK_TICKS = 5; // ~0.25 seconds

    private static final Map<UUID, Interaction> lastInteractions = new HashMap<>();

    public static boolean isDoubleClick(Player player, BlockPos pos, int slotIndex) {
        Level level = player.level();
        UUID uuid = player.getUUID();
        long currentTick = level.getGameTime();

        if (lastInteractions.containsKey(uuid)) {
            Interaction last = lastInteractions.get(uuid);

            // Check if same block, same slot, and within time window
            if (last.pos.equals(pos) &&
                last.slotIndex == slotIndex &&
                (currentTick - last.tick) <= DOUBLE_CLICK_TICKS) {

                // Clear after successful double-click
                lastInteractions.remove(uuid);
                return true;
            }
        }

        // Store new interaction
        lastInteractions.put(uuid, new Interaction(currentTick, pos, slotIndex));
        return false;
    }

    private record Interaction(long tick, BlockPos pos, int slotIndex) {}
}
