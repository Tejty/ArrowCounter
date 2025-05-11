package net.tejty.arrow_counter.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ArrowCounterClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.EnumValue<ConfiguredValues.Position> POSITION;
    public static final ForgeConfigSpec.EnumValue<ConfiguredValues.Style> STYLE;
    public static final ForgeConfigSpec.BooleanValue UNDER;
    public static final ForgeConfigSpec.DoubleValue OPACITY;
    public static final ForgeConfigSpec.IntValue OFFSET;
    public static final ForgeConfigSpec.BooleanValue STRICT_CHECK;

    static {
        BUILDER.push("Config for ArrowCounter");

        POSITION = BUILDER.comment("The position of the counter on screen")
                .defineEnum("position", ConfiguredValues.Position.MIDDLE_NEXT, ConfiguredValues.Position.values());

        STYLE = BUILDER.comment("The style of the counter")
                .defineEnum("style", ConfiguredValues.Style.BLUE, ConfiguredValues.Style.values());

        UNDER = BUILDER.comment("Specifies whether the text should be rendered below or next to the counter.")
                .define("under", false);

        OPACITY = BUILDER.comment("The background opacity of the counter")
                .defineInRange("opacity", 0.7D, 0D, 1D);

        OFFSET = BUILDER.comment("The offset of the counter from edges")
                .defineInRange("offset", 10, 0, 30);

        STRICT_CHECK = BUILDER.comment("If true, only projectiles of the same item will count")
                .define("strict_check", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
