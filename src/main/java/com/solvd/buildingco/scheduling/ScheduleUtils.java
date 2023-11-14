package com.solvd.buildingco.scheduling;

import java.time.format.DateTimeFormatter;

public class ScheduleUtils {
    // short-form to create the Date formatter used throughout the project
    private static final String DATE_PATTERN = "MM/dd/yyyy";

    public static DateTimeFormatter getDateFormat() {
        return DateTimeFormatter.ofPattern(DATE_PATTERN);
    }
}
