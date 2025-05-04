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

    public static Position getPosition() {
        return Position.HOTBAR_RIGHT;
    }
}
