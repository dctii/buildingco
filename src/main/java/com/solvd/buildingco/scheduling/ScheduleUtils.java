package com.solvd.buildingco.scheduling;

import java.time.format.DateTimeFormatter;

public class ScheduleUtils {
    // short-form to create the Date formatter used throughout the project
    public static DateTimeFormatter getDateFormat() {
        return DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }
}
