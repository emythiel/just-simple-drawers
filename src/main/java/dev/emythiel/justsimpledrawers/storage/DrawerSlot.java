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
    private boolean hideDisplay = false;
    private boolean hideText = false;
    private final int totalSlots; // Slot count reference

    // Get base multiplier capacity from config
    private static final int BASE_MULTIPLIER = ServerConfig.BASE_CAPACITY.get();

    // Capacity calculation
    public int getCapacity() {
        if (storedItem.isEmpty()) {
            // return a default capacity if no item stored
            return BASE_MULTIPLIER * 64 / totalSlots;
        }
        return BASE_MULTIPLIER * storedItem.getMaxStackSize() / totalSlots;
    }

    public DrawerSlot(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    // Insert items into this slot
    public int insertItem(ItemStack stack) {
        if (canAccept(stack)) {
            int maxInsert = Math.min(stack.getCount(), getRemainingSpace());
            count += maxInsert;
            return maxInsert;
        }
        return 0;
    }

    public boolean canAccept(ItemStack stack) {
        return storedItem.isEmpty() ||
            (ItemStack.isSameItemSameComponents(storedItem, stack) && !locked);
    }

    public int getRemainingSpace() {
        return Math.max(0, getCapacity() - count);
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }
    public void setStoredItem(ItemStack stack) {
        this.storedItem = stack;
        this.count = 0; // Reset count when changing item
    }

    public ItemStack getUpgrade() {
        return upgrade;
    }
    public void setUpgrade(ItemStack stack) {
        this.upgrade = stack;
        this.count = 0;
    }

    public int getItemCount() {
        return count;
    }
    public void setItemCount(int count) {
        this.count = count;
    }

    public boolean getLockedState() {
        return locked;
    }
    public void toggleLockedState() {
        this.locked = !locked;
    }

    public boolean getVoidMode() {
        return voidMode;
    }
    public void toggleVoidMode() {
        this.voidMode = !voidMode;
    }

    public boolean getHideDisplay() {
        return hideDisplay;
    }
    public void toggleHideDisplay() {
        this.hideDisplay = !hideDisplay;
    }

    public boolean getHideText() {
        return hideText;
    }
    public void toggleHideAmount() {
        this.hideText = !hideText;
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

        //tag.put("item", storedItem.save(registries));
        // Handle storedItem: save empty compound if stack is empty
        if (storedItem.isEmpty()) {
            tag.put("item", new CompoundTag()); // Empty tag placeholder
        } else {
            tag.put("item", storedItem.save(registries));
        }

        tag.putInt("count", count);

        //tag.put("upgrade", upgrade.save(registries));
        if (upgrade.isEmpty()) {
            tag.put("upgrade", new CompoundTag()); // Empty tag placeholder
        } else {
            tag.put("upgrade", upgrade.save(registries));
        }

        tag.putBoolean("locked", locked);
        tag.putBoolean("voidMode", voidMode);
        tag.putBoolean("hideDisplay", hideDisplay);
        tag.putBoolean("hideAmount", hideText);
        return tag;
    }
    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag itemTag = tag.getCompound("item");

        // Handle empty item tag
        if (itemTag.isEmpty()) {
            storedItem = ItemStack.EMPTY;
        } else {
            storedItem = ItemStack.parse(registries, itemTag).orElse(ItemStack.EMPTY);
        }

        count = tag.getInt("count");

        CompoundTag upgradeTag = tag.getCompound("upgrade");
        // Handle empty item tag
        if (upgradeTag.isEmpty()) {
            upgrade = ItemStack.EMPTY;
        } else {
            upgrade = ItemStack.parse(registries, upgradeTag).orElse(ItemStack.EMPTY);
        }

        locked = tag.getBoolean("locked");
        voidMode = tag.getBoolean("voidMode");
        hideDisplay = tag.getBoolean("hideDisplay");
        hideText = tag.getBoolean("hideAmount");
    }
}
