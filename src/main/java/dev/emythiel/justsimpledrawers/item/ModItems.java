package dev.emythiel.justsimpledrawers.item;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JustSimpleDrawers.MOD_ID);

    // Drawer Upgrade Items
    public static final DeferredItem<Item> UPGRADE_BASE = ITEMS.register("upgrade_base",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UPGRADE_T1 = ITEMS.register("upgrade_t1",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UPGRADE_T2 = ITEMS.register("upgrade_t2",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UPGRADE_T3 = ITEMS.register("upgrade_t3",
        () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UPGRADE_T4 = ITEMS.register("upgrade_t4",
        () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
