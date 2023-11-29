package com.solvd.buildingco.utilities;

public class DoubleUtils {
    public static int roundToInt(double number) {
        return (int) Math.round(number);
    }

    private DoubleUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
