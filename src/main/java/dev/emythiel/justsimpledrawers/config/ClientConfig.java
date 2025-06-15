package dev.emythiel.justsimpledrawers.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.IntValue ITEM_VIEW_DISTANCE;
    private static final ModConfigSpec.IntValue TEXT_VIEW_DISTANCE;
    private static final ModConfigSpec.BooleanValue DECIMAL_SEPARATOR;

    public static int itemViewDistance;
    public static int textViewDistance;
    public static boolean decimalSeparator;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        // DRAWER RENDER SETTINGS
        builder.comment().push("render-settings");
        ITEM_VIEW_DISTANCE = builder
            .comment("Sets the distance at which items on the drawers are rendered")
            .defineInRange("itemViewDistance", 24, 1, 128);
        TEXT_VIEW_DISTANCE = builder
            .comment("Sets the distance at which text on the drawers are rendered")
            .defineInRange("textViewDistance", 16, 1, 128);
        DECIMAL_SEPARATOR = builder
            .comment("Sets whether to show a decimal separator or not for larger numbers")
            .define("decimalSeparator", true);
        builder.pop();

        SPEC = builder.build();
    }

    public static void load() {
        itemViewDistance = ITEM_VIEW_DISTANCE.get();
        textViewDistance = TEXT_VIEW_DISTANCE.get();
        decimalSeparator = DECIMAL_SEPARATOR.get();
    }
}
