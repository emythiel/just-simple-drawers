package dev.emythiel.justsimpledrawers.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

// Utility to map 3d hit location to a 2d coordinates
public class RaycastUtil {
    public static Vec2 calculateFrontFaceLocation(BlockPos blockPos, Vec3 hitPos, Direction blockFacing, Direction clickedFace) {
        if (clickedFace != blockFacing) return null; // Only process front face

        // Local hit position
        Vec3 internal = hitPos.subtract(Vec3.atLowerCornerOf(blockPos));

        // Map coordinates based on blockFacing
        float u, v;

        switch (blockFacing) {
            case NORTH -> {
                u = 1.0f - (float) internal.x;
                v = 1.0f - (float) internal.y;
            }
            case EAST -> {
                u = 1.0f - (float) internal.z;
                v = 1.0f - (float) internal.y;
            }
            case SOUTH -> {
                u = (float) internal.x;
                v = 1.0f - (float) internal.y;
            }
            case WEST -> {
                u = (float) internal.z;
                v = 1.0f - (float) internal.y;
            }
            default -> {
                return null;
            }
        }
        return new Vec2(u, v); // (0,0) top-left, (1,1) bottom-right, relative to front face
    }

    public static BlockHitResult getTarget(LivingEntity player, BlockPos blockPos) {
        Vec3 from = player.getEyePosition(1.0f);
        double length = Vec3.atCenterOf(blockPos).subtract(from).length() + 1.0;
        Vec3 look = player.getLookAngle();
        Vec3 to = from.add(look.scale(length));

        return player.level().clip(new ClipContext(
            from, to,
            ClipContext.Block.OUTLINE,
            ClipContext.Fluid.NONE,
            player
        ));
    }
}
