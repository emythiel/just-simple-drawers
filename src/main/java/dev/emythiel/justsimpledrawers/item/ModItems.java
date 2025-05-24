package dev.emythiel.justsimpledrawers.item;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JustSimpleDrawers.MOD_ID);

    // Drawer Upgrade Items
    public static final DeferredItem<Item> DRAWER_UPGRADE_BASE = ITEMS.register("drawer_upgrade_base",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DRAWER_UPGRADE_T1 = ITEMS.register("drawer_upgrade_t1",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DRAWER_UPGRADE_T2 = ITEMS.register("drawer_upgrade_t2",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DRAWER_UPGRADE_T3 = ITEMS.register("drawer_upgrade_t3",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DRAWER_UPGRADE_T4 = ITEMS.register("drawer_upgrade_t4",
        () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
