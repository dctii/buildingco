package com.solvd.buildingco.utilities;

public class BooleanUtils {
    // check if array is empty or null, or if an array full of null items
    public static boolean isEmptyOrNull(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        // if one non-null item, then return false
        for (Object item : array) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }
}
