package dev.emythiel.justsimpledrawers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import dev.emythiel.justsimpledrawers.config.ClientConfig;
import dev.emythiel.justsimpledrawers.storage.DrawerSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

// 64, 32
public class DrawerRenderer implements BlockEntityRenderer<StorageBlockEntity> {
    private static final int ITEM_DISTANCE = ClientConfig.itemViewDistance;
    private static final int TEXT_DISTANCE = ClientConfig.countViewDistance;

    public DrawerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(StorageBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Guard clause in case slots don't match up
        if (blockEntity.slots == null || blockEntity.slots.length == 0) {
            return;
        }

        // Get player and check distance
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        BlockPos blockPos = blockEntity.getBlockPos();
        Vec3 playerPos = player.position();
        double distanceSq = playerPos.distanceToSqr(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

        // Check if we should render item
        if (distanceSq > ITEM_DISTANCE*ITEM_DISTANCE) {
            return;
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Font font = Minecraft.getInstance().font;
        BlockState state = blockEntity.getBlockState();
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        Level level = blockEntity.getLevel();
        BlockPos facePos = blockEntity.getBlockPos().relative(facing);
        int light = level != null ? LevelRenderer.getLightColor(level, facePos) : LightTexture.pack(15, 15);

        for (int i = 0; i < blockEntity.slots.length; i++) {
            DrawerSlot slot = blockEntity.slots[i];
            ItemStack stack = slot.getStoredItem();
            if (stack.isEmpty()) continue;

            poseStack.pushPose();

            // Move item to center
            poseStack.translate(0.50, 0.50, 0.50);

            // Rotate to face front
            float yRot = getYRotation(facing);
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

            // Position based on slot index
            applySlotOffset(poseStack, i, blockEntity.slots.length);

            // Push forward
            poseStack.translate(0, 0, 0.45);

            // Scale depending on slots
            float itemScale = getItemScale(blockEntity.slots.length);
            poseStack.scale(itemScale, itemScale, 0.01f);

            // Render the item
            itemRenderer.renderStatic(stack, ItemDisplayContext.GUI, light, packedOverlay, poseStack, bufferSource, null, 0);

            // Render the count if wihtin range, not zero and not hidden
            if (distanceSq <= TEXT_DISTANCE*TEXT_DISTANCE && !slot.isHideCount() && slot.getCount() > 0) {
                renderText(poseStack, bufferSource, font, slot.getCount(), light);
            }

            poseStack.popPose();
        }
    }

    private float getYRotation(Direction facing) {
        return switch (facing) {
            case NORTH -> 180;
            case SOUTH -> 0;
            case WEST -> -90;
            case EAST -> 90;
            default -> 0;
        };
    }

    private float getItemScale(int slotCount) {
        return slotCount == 1 ? 0.5f : 0.25f;
    }

    private void applySlotOffset(PoseStack poseStack, int slotIndex, int totalSlots) {
        switch (totalSlots) {
            case 1 -> {} // Single Drawer - Centered already
            case 2 -> { // Double Drawer
                if (slotIndex == 0) poseStack.translate(0, 0.27, 0); // Top
                else poseStack.translate(0, -0.23, 0); // Bottom
            }
            case 4 -> { // Quad Drawer
                float x = (slotIndex % 2 == 0) ? -0.25f : 0.25f;
                float y = (slotIndex < 2) ? 0.27f : -0.23f;
                poseStack.translate(x, y, 0);
            }
            case 3 -> { // Compacting drawer
                if (slotIndex == 0) poseStack.translate(0f, 0.27f, 0f); // Top center
                else if (slotIndex == 1) poseStack.translate(-0.25f, -0.23f, 0f); // Bottom left
                else poseStack.translate(0.25f, -0.23f, 0f); // Bottom right
            }
        }
    }

    private void renderText(PoseStack poseStack, MultiBufferSource bufferSource, Font font, int count, int light) {
        poseStack.pushPose();
        //System.out.println("Rendering count: " + count);

        try {
            // Position the text
            poseStack.translate(0, -0.55f, 0);

            // Format count text
            String countText = formatCount(count);
            Component textComponent = Component.literal(countText);
            FormattedCharSequence formattedText = textComponent.getVisualOrderText();
            int textWidth = font.width(formattedText);

            // Scale the text
            float scale = 0.03f;
            poseStack.scale(scale, -scale, scale);

            // Center the text
            float xOffset = -textWidth / 2f;

            // Render the text
            font.drawInBatch(
                formattedText,
                xOffset, 0,
                0xFFFFFF,
                false,
                poseStack.last().pose(),
                bufferSource,
                Font.DisplayMode.NORMAL,
                0,
                light
            );
        } finally {
            poseStack.popPose();
        }
    }

    private String formatCount(int count) {
        if (count < 100000) {
            // Show full number with commas (e.g., 12,345)
            return String.format("%,d", count);
        } else if (count < 1000000) {
            // Format as whole k (e.g., 100k)
            return String.format("%.0fk", count / 1000f);
        } else {
            // Format as M with 1 decimal if < 10M
            float m = count / 1000000f;
            if (m < 10) {
                return String.format("%.1fM", m);
            }
            return String.format("%.0fM", m);
        }
    }
}
