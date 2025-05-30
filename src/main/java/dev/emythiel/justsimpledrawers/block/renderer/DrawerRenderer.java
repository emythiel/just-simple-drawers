package dev.emythiel.justsimpledrawers.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DrawerRenderer implements BlockEntityRenderer<StorageBlockEntity> {
    public DrawerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(StorageBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.inventory.getStackInSlot(0);

        if (stack.isEmpty()) return; // Exit if there's nothing stored

        // Figure out the blocks front
        BlockState state = blockEntity.getBlockState();
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

        // Get the light level
        Level level = blockEntity.getLevel();
        BlockPos facePos = blockEntity.getBlockPos().relative(facing);
        int light = level != null ? LevelRenderer.getLightColor(level, facePos) : LightTexture.pack(15, 15);

        // Initial position for the renderer is in the center
        poseStack.pushPose();
        poseStack.translate(0.50, 0.50, 0.5);

        // Rotate the item to the proper facing
        float yRot = switch (facing) {
            case NORTH -> 180;
            case SOUTH -> 0;
            case WEST -> -90;
            case EAST -> 90;
            default -> 180;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        // Push the item out from the center
        poseStack.translate(0, 0, 0.45);

        // Apply scale
        poseStack.scale(0.5f, 0.5f, 0.01f);

        // Final render
        itemRenderer.renderStatic(stack, ItemDisplayContext.GUI, light, packedOverlay, poseStack, bufferSource, null, 0);
        poseStack.popPose();
    }
}
