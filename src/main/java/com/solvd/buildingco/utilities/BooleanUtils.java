package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;

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

    public static <T> boolean isValidBuildingType(T building) {
        if (building instanceof House || building instanceof IndustrialBuilding || building instanceof Skyscraper) {
            return true;
        } else {
            return false;
        }
    }

    private BooleanUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
