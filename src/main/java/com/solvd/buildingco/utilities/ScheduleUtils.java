package com.solvd.buildingco.utilities;

import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.ScheduledActivity;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.solvd.buildingco.buildings.BuildingConstants.ARCHITECTURE_WORK_DESCRIPTION;

public class ScheduleUtils {
    // short-form to create the Date formatter used throughout the project
    private static final String DATE_PATTERN = "MM/dd/yyyy";

    public static DateTimeFormatter getDateFormat() {
        return DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    // create an employee schedule, provides qty of hours with rates to calculate labor costs
    public static Schedule generateEmployeeSchedule(
            ZonedDateTime customerEndDate,
            int totalConstructionDays,
            String workDescription) {

        Schedule schedule = new Schedule();

        ZonedDateTime requiredStartTime, architectEndTime;
        requiredStartTime = customerEndDate.minusDays(totalConstructionDays);
        architectEndTime = requiredStartTime.plusDays(totalConstructionDays / 5);

        ScheduledActivity workerActivity;

        if (!workDescription.equalsIgnoreCase(ARCHITECTURE_WORK_DESCRIPTION)) {
            workerActivity =
                    new ScheduledActivity(
                            workDescription,
                            requiredStartTime,
                            customerEndDate
                    );
        } else {
            workerActivity =
                    new ScheduledActivity(
                            workDescription,
                            requiredStartTime,
                            architectEndTime
                    );
        }

        schedule.addActivity(workerActivity);


        return schedule;
    }

    private ScheduleUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
