package dev.emythiel.justsimpledrawers.storage;

import dev.emythiel.justsimpledrawers.config.ServerConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class DrawerSlot {
    private ItemStack storedItem = ItemStack.EMPTY;
    private int count = 0;
    private ItemStack upgrade = ItemStack.EMPTY;
    private boolean locked = false;
    private boolean voidMode = false;
    private boolean hideItem = false;
    private boolean hideCount = false; // Slot count reference

    // Capacity calculation
    public int getCapacity(int baseMultiplier, int totalSlots) {
        if (storedItem.isEmpty()) {
            // return a default capacity if no item stored
            return baseMultiplier * 64 / totalSlots;
        }
        int maxStackSize = storedItem.getMaxStackSize();
        int baseCapacity = (baseMultiplier * maxStackSize) / totalSlots;
        return baseCapacity;
    }

    // Insert items
    public int insertItem(ItemStack stack) {
        // TODO: Implement item insert logic
        return 0;
    }

    // Withdraw items
    public ItemStack withdrawItem(int amount, boolean isSneaking) {
        // TODO: Implement item withdraw logic
        return null;
    }

    // Getters and Setters
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    public boolean isVoidMode() { return voidMode; }
    public void setVoidMode(boolean voidMode) { this.voidMode = voidMode; }

    public boolean isHideItem() { return hideItem; }
    public void setHideItem(boolean hideItem) { this.hideItem = hideItem; }

    public boolean isHideCount() { return hideCount; }
    public void setHideCount(boolean hideCount) { this.hideCount = hideCount; }
}
