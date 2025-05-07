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
        return Position.TOP_LEFT;
    }

    public static Style getStyle() {
        return Style.SLOT;
    }

    public static boolean getUnder() {
        return false;
    }
}
