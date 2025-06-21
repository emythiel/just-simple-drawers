package dev.emythiel.justsimpledrawers.block.base;

import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;

public abstract class StorageBlock<T extends StorageBlockEntity> extends BaseBlock implements EntityBlock {
    public final int slots;

    public StorageBlock(Properties properties, int slots) {
        super(properties);
        this.slots = slots;
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
