package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;
import org.apache.commons.lang3.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class BooleanUtils {
    // check if array is empty or null, or if an array full of null items
    public static boolean isEmptyOrNullArray(Object[] array) {
        return array == null
                || array.length == 0
                || Arrays.stream(array).allMatch(item -> item == null);
    }

    public static boolean isNotEmptyOrNullArray(Object[] array) {
        return !isEmptyOrNullArray(array);
    }

    public static boolean isBlankOrEmptyString(String string) {
        return StringUtils.isBlank(string) || StringUtils.isEmpty(string);
    }

    public static boolean isNotBlankOrEmptyString(String string) {
        return !isBlankOrEmptyString(string);
    }

    public static <T> boolean isValidBuildingType(T building) {
        return building instanceof House || building instanceof IndustrialBuilding || building instanceof Skyscraper;
    }

    public static boolean isWithinDateSpan(LocalDate activityDate, LocalDate startDate,
                                           LocalDate endDate) {
        return (
                isSameDate(activityDate, startDate)
                        || activityDate.isAfter(startDate)
        )
                && (
                isSameDate(activityDate, endDate)
                        || activityDate.isBefore(endDate)
        );
    }

    public static boolean isSameDate(LocalDate comparans, LocalDate comparandum) {
        return comparans.isEqual(comparandum);
    }

    public static boolean isSameDate(ZonedDateTime comparans, ZonedDateTime comparandum) {
        return comparans.isEqual(comparandum);
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        final DayOfWeek[] weekends = {
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
        };

        return Arrays
                .asList(weekends)
                .contains(dayOfWeek);
    }


    private BooleanUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
