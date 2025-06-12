package dev.emythiel.justsimpledrawers.block.entity;

import dev.emythiel.justsimpledrawers.storage.DrawerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class StorageBlockEntity extends BlockEntity {
    public final DrawerSlot[] slots;
    private final int slotCount;

    public StorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int slotCount) {
        super(type, pos, state);
        this.slotCount = slotCount;
        this.slots = new DrawerSlot[slotCount];
        for (int i = 0; i < slotCount; i++) {
            slots[i] = new DrawerSlot(slotCount);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag slotsTag = new CompoundTag();
        for (int i = 0; i < slotCount; i++) {
            slotsTag.put("slot_" + i, slots[i].save(registries));
        }
        tag.put("slots", slotsTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag slotsTag = tag.getCompound("slots");
        for (int i = 0; i < slotCount; i++) {
            if (slotsTag.contains("slot_" + i)) {
                slots[i].load(slotsTag.getCompound("slot_" + i), registries);
            }
        }
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
