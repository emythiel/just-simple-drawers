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
    private static final int TEXT_DISTANCE = ClientConfig.textViewDistance;

    public DrawerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(StorageBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    }
}
