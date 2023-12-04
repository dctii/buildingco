package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.TimeConflictException;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.ScheduledActivity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.solvd.buildingco.stakeholders.employees.Personnel.ARCHITECT;

public class ScheduleUtils {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleUtils.class);
    // short-form to create the Date formatter used throughout the project
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    final static String IDENTICAL_TIMES_MESSAGE =
            "The 'startTime' and 'endTime' cannot be identical.";
    final static String START_AFTER_END_MESSAGE =
            "The 'startTime' cannot be after the 'endTime'.";

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

        if (!workDescription.equalsIgnoreCase(ARCHITECT.getDefaultActivityDescription())) {
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

    public static void validateScheduledTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        if (startTime != null && endTime != null) {
            if (startTime.isEqual(endTime)) {
                LOGGER.warn(IDENTICAL_TIMES_MESSAGE);
                throw new TimeConflictException(IDENTICAL_TIMES_MESSAGE);
            } else if (startTime.isAfter(endTime)) {
                LOGGER.warn(START_AFTER_END_MESSAGE);
                throw new TimeConflictException(START_AFTER_END_MESSAGE);
            }
        }
    }

    public static long calculateTotalWorkHours(Schedule schedule, LocalDate startDate,
                                               LocalDate endDate) {
        final long SECONDS_IN_HOUR = 3600;
        long totalWorkHours = 0;

        for (Map.Entry<DayOfWeek, List<ScheduledActivity>> entry :
                schedule.getWeeklyActivities().entrySet()) {

            for (ScheduledActivity activity : entry.getValue()) {
                LocalDate activityDate = activity.getStartTime().toLocalDate();

                boolean isWithinDateSpan =
                        (activityDate.isEqual(startDate) || activityDate.isAfter(startDate)) &&
                                (activityDate.isEqual(endDate) || activityDate.isBefore(endDate));

                // if within date range, then update work hours
                if (isWithinDateSpan) {
                    // calculate duration of activity in seconds;
                    // EpochSecond = date and time computers use to measure system time
                    long hours =
                            activity.getEndTime().toEpochSecond() - activity.getStartTime().toEpochSecond();

                    // update amount of totalWorkHours
                    totalWorkHours += (hours / SECONDS_IN_HOUR);
                }
            }
        }
        return totalWorkHours;
    }

    private ScheduleUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
