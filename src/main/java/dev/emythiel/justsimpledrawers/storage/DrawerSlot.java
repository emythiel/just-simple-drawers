package dev.emythiel.justsimpledrawers.storage;

import dev.emythiel.justsimpledrawers.config.ServerConfig;
import dev.emythiel.justsimpledrawers.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DrawerSlot {
    private ItemStack storedItem = ItemStack.EMPTY;
    private int count;
    private ItemStack upgrade = ItemStack.EMPTY;
    private boolean locked;
    private boolean voidMode;
    private boolean hideItem;
    private boolean hideCount;
    private final int totalSlotsInDrawer;

    public DrawerSlot(int totalSlotsInDrawer) {
        this.totalSlotsInDrawer = totalSlotsInDrawer;
    }

    public int getCapacity() {
        int baseMultiplier = ServerConfig.baseCapacity;
        int stackSize = storedItem.isEmpty() ? 64 : storedItem.getMaxStackSize();
        int upgradeMultiplier = getUpgradeMultiplier();

        return (baseMultiplier * stackSize * upgradeMultiplier) / totalSlotsInDrawer;
    }

    private int getUpgradeMultiplier() {
        if (upgrade.isEmpty()) return 1;

        Item upgradeItem = upgrade.getItem();
        return switch (upgradeItem.getDescriptionId()) {
            case "item.justsimpledrawers.capacity_upgrade_t1" -> ServerConfig.capacityUpgradeT1Mult;
            case "item.justsimpledrawers.capacity_upgrade_t2" -> ServerConfig.capacityUpgradeT2Mult;
            case "item.justsimpledrawers.capacity_upgrade_t3" -> ServerConfig.capacityUpgradeT3Mult;
            case "item.justsimpledrawers.capacity_upgrade_t4" -> ServerConfig.capacityUpgradeT4Mult;
            case "item.justsimpledrawers.capacity_upgrade_t5" -> ServerConfig.capacityUpgradeT5Mult;
            default -> 1;
        };
    }

    // Insert items
    public int insertItem(ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || ServerConfig.blacklistedItems.contains(stack.getItem())) {
            return 0;
        }

        if (isLocked()) {
            if (storedItem.isEmpty()) return 0;
            if (!ItemStack.isSameItemSameComponents(storedItem, stack)) return 0;
        }

        if (storedItem.isEmpty()) {
            if (!simulate) {
                storedItem = stack.copyWithCount(1); // Init with 1 item
            }
        } else if (!ItemStack.isSameItemSameComponents(storedItem, stack)) {
            return 0;
        }

        int capacity = getCapacity();
        int remainingSpace = capacity - count;
        int toInsert = Math.min(stack.getCount(), remainingSpace);

        if (isVoidMode()) {
            toInsert = stack.getCount();
        } else if (toInsert <= 0) {
            return 0;
        }

        if (!simulate) {
            count = Math.min(count + toInsert, capacity);
        }

        return toInsert;
    }

    // Withdraw items
    public ItemStack withdrawItem(int amount, boolean isSneaking) {
        // TODO: Implement item withdraw logic
        return null;
    }

    // Upgrade management
    public boolean installUpgrade(ItemStack upgradeStack) {
        if (!isUpgradeItem(upgradeStack)) return false;

        int currentMultiplier = getUpgradeMultiplier();
        int newMultiplier = getUpgradeMultiplierForItem(upgradeStack);

        if (newMultiplier < currentMultiplier && count > getCapacityWithMultiplier(newMultiplier)) {
            return false;
        }

        upgrade = upgradeStack.copyWithCount(1);
        return true;
    }

    public ItemStack removeUpgrade() {
        if (upgrade.isEmpty()) return ItemStack.EMPTY;

        ItemStack removed = upgrade.copy();
        upgrade = ItemStack.EMPTY;
        return removed;
    }

    private boolean isUpgradeItem(ItemStack stack) {
        return stack.is(ModItems.CAPACITY_UPGRADE_T1.get()) ||
               stack.is(ModItems.CAPACITY_UPGRADE_T2.get()) ||
               stack.is(ModItems.CAPACITY_UPGRADE_T3.get()) ||
               stack.is(ModItems.CAPACITY_UPGRADE_T4.get()) ||
               stack.is(ModItems.CAPACITY_UPGRADE_T5.get());
    }

    private int getUpgradeMultiplierForItem(ItemStack stack) {
        return switch (stack.getDescriptionId()) {
            case "item.justsimpledrawers.capacity_upgrade_t1" -> ServerConfig.capacityUpgradeT1Mult;
            case "item.justsimpledrawers.capacity_upgrade_t2" -> ServerConfig.capacityUpgradeT2Mult;
            case "item.justsimpledrawers.capacity_upgrade_t3" -> ServerConfig.capacityUpgradeT3Mult;
            case "item.justsimpledrawers.capacity_upgrade_t4" -> ServerConfig.capacityUpgradeT4Mult;
            case "item.justsimpledrawers.capacity_upgrade_t5" -> ServerConfig.capacityUpgradeT5Mult;
            default -> 1;
        };
    }

    private int getCapacityWithMultiplier(int multiplier) {
        int stackSize = storedItem.isEmpty() ? 64 : storedItem.getMaxStackSize();
        return (ServerConfig.baseCapacity * stackSize * multiplier) / totalSlotsInDrawer;
    }

    // NBT SERIALIZATION
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        if (!storedItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            storedItem.save(provider, itemTag);
            tag.put("Item", itemTag);
        }
        tag.putInt("Count", count);

        if (!upgrade.isEmpty()) {
            CompoundTag upgradeTag = new CompoundTag();
            upgrade.save(provider, upgradeTag);
            tag.put("Upgrade", upgradeTag);
        }

        tag.putBoolean("Locked", locked);
        tag.putBoolean("VoidMode", voidMode);
        tag.putBoolean("HideItem", hideItem);
        tag.putBoolean("HideCount", hideCount);

        return tag;
    }

    public void load(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains("Item")) {
            storedItem = ItemStack.parse(provider, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
        }
        count = tag.getInt("Count");

        if (tag.contains("Upgrade")) {
            upgrade = ItemStack.parse(provider, tag.getCompound("Upgrade")).orElse(ItemStack.EMPTY);
        }

        locked = tag.getBoolean("Locked");
        voidMode = tag.getBoolean("VoidMode");
        hideItem = tag.getBoolean("HideItem");
        hideCount = tag.getBoolean("HideCount");
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

    public ItemStack getStoredItem() { return storedItem; }
    public int getCount() { return count; }
    public ItemStack getUpgrade() { return upgrade; }
}
