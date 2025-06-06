package net.tejty.arrow_counter.config;

public class ConfiguredValues {
    public enum Position {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        MIDDLE_NEXT,
        MIDDLE_UNDER,
        HOTBAR_LEFT,
        HOTBAR_RIGHT;
    }

    public enum Style {
        BLUE,
        BLACK,
        BAR,
        SLOT;
    }

    public static Position getPosition() {
        return ArrowCounterClientConfigs.POSITION.get();
    }

    public static Style getStyle() {
        return ArrowCounterClientConfigs.STYLE.get();
    }

    public static boolean getUnder() {
        return ArrowCounterClientConfigs.UNDER.get();
    }

    public static float getBackgroundOpacity() {
        return ArrowCounterClientConfigs.OPACITY.get().floatValue();
    }

    public static int getOffset() {
        return ArrowCounterClientConfigs.OFFSET.get();
    }

    public static boolean getStrictCheck() {
        return ArrowCounterClientConfigs.STRICT_CHECK.get();
    }
}
