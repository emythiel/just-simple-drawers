package dev.emythiel.justsimpledrawers.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.IntValue BASE_CAPACITY;
    private static final ModConfigSpec.IntValue COMPACTING_BASE_CAPACITY;
    private static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T1_MULTIPLIER;
    private static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T2_MULTIPLIER;
    private static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T3_MULTIPLIER;
    private static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T4_MULTIPLIER;
    private static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T5_MULTIPLIER;

    private static final ModConfigSpec.IntValue BASE_RANGE;
    private static final ModConfigSpec.IntValue RANGE_UPGRADE_T1_MULTIPLIER;
    private static final ModConfigSpec.IntValue RANGE_UPGRADE_T2_MULTIPLIER;
    private static final ModConfigSpec.IntValue RANGE_UPGRADE_T3_MULTIPLIER;
    private static final ModConfigSpec.IntValue RANGE_UPGRADE_T4_MULTIPLIER;
    private static final ModConfigSpec.IntValue RANGE_UPGRADE_T5_MULTIPLIER;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_ITEMS;

    public static int baseCapacity;
    public static int compactingBaseCapacity;
    public static int capacityUpgradeT1Mult;
    public static int capacityUpgradeT2Mult;
    public static int capacityUpgradeT3Mult;
    public static int capacityUpgradeT4Mult;
    public static int capacityUpgradeT5Mult;

    public static int baseRange;
    public static int rangeUpgradeT1Mult;
    public static int rangeUpgradeT2Mult;
    public static int rangeUpgradeT3Mult;
    public static int rangeUpgradeT4Mult;
    public static int rangeUpgradeT5Mult;

    public static Set<Item> blacklistedItems;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        // CAPACITY SETTINGS
        builder.comment().push("capacity");
        BASE_CAPACITY = builder
            .comment(
                "Sets the base drawer capacity (the amount of stacks that can be stored)",
                "This value is divided by the amount of slots a drawer block has"
            )
            .defineInRange("baseCapacity", 32, 1, 1024);
        COMPACTING_BASE_CAPACITY = builder
            .comment(
                "Sets the base compacting drawer capacity (the amount of stacks that can be stored)",
                "Capacity is based on the most compressed item stored"
            )
            .defineInRange("compactingBaseCapacity", 8, 1, 1024);
        CAPACITY_UPGRADE_T1_MULTIPLIER = builder
            .comment("Sets the tier 1 capacity upgrade multiplier")
            .defineInRange("capacityUpgradeT1Mult", 2, 1, 1024);
        CAPACITY_UPGRADE_T2_MULTIPLIER = builder
            .comment("Sets the tier 2 capacity upgrade multiplier")
            .defineInRange("capacityUpgradeT2Mult", 4, 1, 1024);
        CAPACITY_UPGRADE_T3_MULTIPLIER = builder
            .comment("Sets the tier 3 capacity upgrade multiplier")
            .defineInRange("capacityUpgradeT3Mult", 8, 1, 1024);
        CAPACITY_UPGRADE_T4_MULTIPLIER = builder
            .comment("Sets the tier 4 capacity upgrade multiplier")
            .defineInRange("capacityUpgradeT4Mult", 16, 1, 1024);
        CAPACITY_UPGRADE_T5_MULTIPLIER = builder
            .comment("Sets the tier 5 capacity upgrade multiplier")
            .defineInRange("capacityUpgradeT5Mult", 32, 1, 1024);
        builder.pop();

        // RANGE SETTINGS
        builder.comment().push("range");
        BASE_RANGE = builder
            .comment("Sets the base range (in blocks) for the controller network")
            .defineInRange("baseRange", 4, 1, 128);
        RANGE_UPGRADE_T1_MULTIPLIER = builder
            .comment("Sets the tier 1 range upgrade multiplier")
            .defineInRange("rangeUpgradeT1Mult", 2, 1, 32);
        RANGE_UPGRADE_T2_MULTIPLIER = builder
            .comment("Sets the tier 2 range upgrade multiplier")
            .defineInRange("rangeUpgradeT2Mult", 4, 1, 32);
        RANGE_UPGRADE_T3_MULTIPLIER = builder
            .comment("Sets the tier 3 range upgrade multiplier")
            .defineInRange("rangeUpgradeT3Mult", 6, 1, 32);
        RANGE_UPGRADE_T4_MULTIPLIER = builder
            .comment("Sets the tier 4 range upgrade multiplier")
            .defineInRange("rangeUpgradeT4Mult", 8, 1, 32);
        RANGE_UPGRADE_T5_MULTIPLIER = builder
            .comment("Sets the tier 5 range upgrade multiplier")
            .defineInRange("rangeUpgradeT5Mult", 10, 1, 32);
        builder.pop();

        // BLACKLISTED ITEMS
        BLACKLISTED_ITEMS = builder
            .comment("List of items that cannot be inserted into the drawers")
            .defineListAllowEmpty("blacklistedItems",
                () -> List.of("minecraft:shulker_box"),
                () -> "minecraft:shulker_box",
                ServerConfig::validateItem);

        SPEC = builder.build();
    }

    public static void load() {
        baseCapacity = BASE_CAPACITY.get();
        compactingBaseCapacity = COMPACTING_BASE_CAPACITY.get();
        capacityUpgradeT1Mult = CAPACITY_UPGRADE_T1_MULTIPLIER.get();
        capacityUpgradeT2Mult = CAPACITY_UPGRADE_T2_MULTIPLIER.get();
        capacityUpgradeT3Mult = CAPACITY_UPGRADE_T3_MULTIPLIER.get();
        capacityUpgradeT4Mult = CAPACITY_UPGRADE_T4_MULTIPLIER.get();
        capacityUpgradeT5Mult = CAPACITY_UPGRADE_T5_MULTIPLIER.get();

        baseRange = BASE_RANGE.get();
        rangeUpgradeT1Mult = RANGE_UPGRADE_T1_MULTIPLIER.get();
        rangeUpgradeT2Mult = RANGE_UPGRADE_T2_MULTIPLIER.get();
        rangeUpgradeT3Mult = RANGE_UPGRADE_T3_MULTIPLIER.get();
        rangeUpgradeT4Mult = RANGE_UPGRADE_T4_MULTIPLIER.get();
        rangeUpgradeT5Mult = RANGE_UPGRADE_T5_MULTIPLIER.get();

        blacklistedItems = BLACKLISTED_ITEMS.get().stream()
            .map(ResourceLocation::parse)
            .map(BuiltInRegistries.ITEM::get)
            .collect(Collectors.toSet());
    }

    private static boolean validateItem(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
