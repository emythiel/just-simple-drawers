package dev.emythiel.justsimpledrawers.block.entity;

import dev.emythiel.justsimpledrawers.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class StorageBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 99;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private int upgradeMultiplier;
    private boolean lockMode;
    private boolean voidMode;
    private boolean hideDisplay;
    private boolean hideAmount;

    public StorageBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DRAWER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("upgradeMultiplier", this.upgradeMultiplier);
        tag.putBoolean("lockMode", this.lockMode);
        tag.putBoolean("voidMode", this.voidMode);
        tag.putBoolean("hideDisplay", this.hideDisplay);
        tag.putBoolean("hideAmount", this.hideAmount);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.upgradeMultiplier = tag.getInt("upgradeMultiplier");
        this.lockMode = tag.getBoolean("lockMode");
        this.voidMode = tag.getBoolean("voidMode");
        this.hideDisplay = tag.getBoolean("hideDisplay");
        this.hideAmount = tag.getBoolean("hideAmount");
    }



    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }
}
