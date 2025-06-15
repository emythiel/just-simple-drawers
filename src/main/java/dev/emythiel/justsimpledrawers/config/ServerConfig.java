package dev.emythiel.justsimpledrawers.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Base drawer slot capacity
    public static final ModConfigSpec.IntValue BASE_CAPACITY = BUILDER
        .comment("Sets the base drawer capacity (the amount of stacks that can be stored)", "This value is divided by the amount of slots a drawer block has")
        .defineInRange("baseCapacity", 32, 1, 1024);

    // Capacity upgrade multipliers
    public static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T1_MULTIPLIER = BUILDER
        .comment("Sets the tier 1 capacity upgrade multiplier")
        .defineInRange("capacityUpgradeT1Mult", 2, 1, 1024);
    public static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T2_MULTIPLIER = BUILDER
        .comment("Sets the tier 2 capacity upgrade multiplier")
        .defineInRange("capacityUpgradeT2Mult", 4, 1, 1024);
    public static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T3_MULTIPLIER = BUILDER
        .comment("Sets the tier 3 capacity upgrade multiplier")
        .defineInRange("capacityUpgradeT3Mult", 8, 1, 1024);
    public static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T4_MULTIPLIER = BUILDER
        .comment("Sets the tier 4 capacity upgrade multiplier")
        .defineInRange("capacityUpgradeT4Mult", 16, 1, 1024);
    public static final ModConfigSpec.IntValue CAPACITY_UPGRADE_T5_MULTIPLIER = BUILDER
        .comment("Sets the tier 5 capacity upgrade multiplier")
        .defineInRange("capacityUpgradeT5Mult", 32, 1, 1024);

    // Storage blacklist
    public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_ITEMS = BUILDER
        .comment("A list of items that cannot be inserted into the drawers")
        .defineListAllowEmpty("blacklistedItems",
            () -> List.of("minecraft:shulker_box"),
            () -> "minecraft:shulker_box",
            ServerConfig::validateItem);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItem(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
