package dev.emythiel.justsimpledrawers.registry;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JustSimpleDrawers.MOD_ID);

    // UPGRADE BASE
    public static final DeferredItem<Item> UPGRADE_BASE = ITEMS.register("upgrade_base",
        () -> new Item(new Item.Properties()));

    // CAPACITY UPGRADES
    public static final DeferredItem<Item> CAPACITY_UPGRADE_T1 = ITEMS.register("capacity_upgrade_t1",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAPACITY_UPGRADE_T2 = ITEMS.register("capacity_upgrade_t2",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAPACITY_UPGRADE_T3 = ITEMS.register("capacity_upgrade_t3",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAPACITY_UPGRADE_T4 = ITEMS.register("capacity_upgrade_t4",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAPACITY_UPGRADE_T5 = ITEMS.register("capacity_upgrade_t5",
        () -> new Item(new Item.Properties()));

    // RANGE UPGRADES
    public static final DeferredItem<Item> RANGE_UPGRADE_T1 = ITEMS.register("range_upgrade_t1",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RANGE_UPGRADE_T2 = ITEMS.register("range_upgrade_t2",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RANGE_UPGRADE_T3 = ITEMS.register("range_upgrade_t3",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RANGE_UPGRADE_T4 = ITEMS.register("range_upgrade_t4",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RANGE_UPGRADE_T5 = ITEMS.register("range_upgrade_t5",
        () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
