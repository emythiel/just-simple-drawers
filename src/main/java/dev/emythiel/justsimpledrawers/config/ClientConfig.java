package dev.emythiel.justsimpledrawers.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Item and Text view distance
    public static final ModConfigSpec.IntValue ITEM_VIEW_DISTANCE = BUILDER
        .comment("Sets the distance at which items on the drawers are rendered")
        .defineInRange("itemViewDistance", 24, 1, 128);
    public static final ModConfigSpec.IntValue TEXT_VIEW_DISTANCE = BUILDER
        .comment("Sets the distance at which text on the drawers are rendered")
        .defineInRange("textViewDistance", 16, 1, 128);

    // Decimal seperator for high amounts
    public static final ModConfigSpec.BooleanValue DECIMAL_SEPARATOR = BUILDER
        .comment("Set whether to show a decimal separator or not for larger numbers")
        .define("decimalSeparator", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
