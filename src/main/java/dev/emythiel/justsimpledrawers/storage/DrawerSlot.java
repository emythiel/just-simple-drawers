package dev.emythiel.justsimpledrawers.storage;

import dev.emythiel.justsimpledrawers.config.ServerConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class DrawerSlot {
    private ItemStack storedItem = ItemStack.EMPTY;
    private int count = 0;
    private boolean locked = false;
    private boolean voidMode = false;
    private boolean hideItem = false;
    private boolean hideCount = false;

    private int upgradeMultiplier = 1;
    private final int totalSlots;

    public DrawerSlot(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    // Capacity calculation
    public int getCapacity() {
        int baseMultiplier = ServerConfig.baseCapacity;
        int stackSize = storedItem.isEmpty() ? 64 : storedItem.getMaxStackSize();;

        return (baseMultiplier * stackSize * upgradeMultiplier) / totalSlots;
    }

    public int getRemainingCapacity() {
        return Math.max(0, getCapacity() - count);
    }

    public boolean canAccept(ItemStack stack) {
        return storedItem.isEmpty() ||
            (ItemStack.isSameItemSameComponents(storedItem, stack) && !locked);
    }

    // Insert items into this slot
    public int insertItem(ItemStack stack) {
        if (canAccept(stack)) {
            int maxInsert = Math.min(stack.getCount(), getRemainingCapacity());
            count += maxInsert;
            return maxInsert;
        }
        return 0;
    }

    // Withdraw items
    public ItemStack withdrawItem(boolean isSneaking) {
        if (storedItem.isEmpty() || count <= 0) {
            return ItemStack.EMPTY;
        }

        // Determine withdraw amount
        int amount = isSneaking ? storedItem.getMaxStackSize() : 1;
        int amountToWithdraw = Math.min(amount, count);
        ItemStack result = storedItem.copyWithCount(amountToWithdraw);

        // Update drawer state
        count -= amountToWithdraw;
        if (count <= 0) {
            storedItem = ItemStack.EMPTY;
            count = 0;
        }

        return result;
    }

    // NBT Serialization
    public CompoundTag save(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();

        if (storedItem.isEmpty()) {
            tag.put("item", new CompoundTag()); // Empty tag placeholder
        } else {
            tag.put("item", storedItem.save(registries));
        }

        tag.putInt("count", count);
        tag.putBoolean("locked", locked);
        tag.putBoolean("voidMode", voidMode);
        tag.putBoolean("hideDisplay", hideItem);
        tag.putBoolean("hideAmount", hideCount);
        return tag;
    }

    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag itemTag = tag.getCompound("item");

        if (itemTag.isEmpty()) {
            storedItem = ItemStack.EMPTY;
        } else {
            storedItem = ItemStack.parse(registries, itemTag).orElse(ItemStack.EMPTY);
        }

        count = tag.getInt("count");
        locked = tag.getBoolean("locked");
        voidMode = tag.getBoolean("voidMode");
        hideItem = tag.getBoolean("hideDisplay");
        hideCount = tag.getBoolean("hideAmount");
    }

    // Getters and setters
    public ItemStack getStoredItem() { return storedItem; }
    public void setStoredItem(ItemStack item) { this.storedItem = item; count = 0;}
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
    public boolean isVoidMode() { return voidMode; }
    public void setVoidMode(boolean voidMode) { this.voidMode = voidMode; }
    public boolean isHideItem() { return hideItem; }
    public void setHideItem(boolean hideItem) { this.hideItem = hideItem; }
    public boolean isHideCount() { return hideCount; }
    public void setHideCount(boolean hideCount) { this.hideCount = hideCount; }

    public void setUpgradeMultiplier(int multiplier) { this.upgradeMultiplier = multiplier; }
}
