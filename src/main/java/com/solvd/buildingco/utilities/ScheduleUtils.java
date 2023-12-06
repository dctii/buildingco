package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.InvalidTemporalTypeException;
import com.solvd.buildingco.exception.TimeConflictException;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.ScheduledActivity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

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
            ZonedDateTime customerEndDateTime,
            int totalConstructionDays,
            String workDescription) {

        Schedule schedule = new Schedule();


        // count back from the expected end time of construction
        ZonedDateTime requiredStartDateTime = customerEndDateTime.minusDays(totalConstructionDays);
        ZonedDateTime architectEndDateTime =
                requiredStartDateTime.plusDays(NumberUtils.roundToInt(totalConstructionDays * 0.25));

        boolean isArchitect =
                workDescription.equalsIgnoreCase(ARCHITECT.getDefaultActivityDescription());

        customerEndDateTime = !isArchitect
                ? customerEndDateTime
                : architectEndDateTime;


        datesBetween(requiredStartDateTime, customerEndDateTime, false)
                .forEach(currentDateTime -> {
                    DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();

                    if (isNotWeekend(dayOfWeek)) {
                        ZonedDateTime startDateTime = currentDateTime.withHour(9);
                        ZonedDateTime endDateTime = currentDateTime.withHour(17);

                        ScheduledActivity workerActivity =
                                new ScheduledActivity(
                                        workDescription,
                                        startDateTime,
                                        endDateTime
                                );
                        schedule.addActivity(workerActivity);
                    }
                });

        return schedule;
    }

    public static Stream<ZonedDateTime> datesBetween(ZonedDateTime start, ZonedDateTime end) {
        /*
            "Producing streams in java.time?"
            https://mail.openjdk.org/pipermail/core-libs-dev/2015-December/037844.html

            "Java 8 LocalDate - How do I get all dates between two dates?"
            https://stackoverflow.com/questions/38220543/java-8-localdate-how-do-i-get-all-dates-between-two-dates

        */
        return Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end));
    }

    public static Stream<ZonedDateTime> datesBetween(ZonedDateTime start, ZonedDateTime end,
                                                     boolean includeWeekends) {
        if (includeWeekends) {
            return datesBetween(start, end);
        } else {
            return Stream.iterate(start, ScheduleUtils::nextWeekday)
                    .limit(ChronoUnit.DAYS.between(start, end))
                    .filter(date -> isNotWeekend(date.getDayOfWeek()));
        }
    }

    private static ZonedDateTime nextWeekday(ZonedDateTime date) {
        ZonedDateTime nextDay = date.plusDays(1);

        while (isWeekend(nextDay.getDayOfWeek())) {
            nextDay = nextDay.plusDays(1);
        }

        return nextDay;
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return BooleanUtils.isWeekend(dayOfWeek);
    }

    public static boolean isNotWeekend(DayOfWeek dayOfWeek) {
        return !isWeekend(dayOfWeek);
    }

    public static String toReadableDateString(Temporal datetime) {
        if (datetime instanceof LocalDate) {
            return StringFormatters.toReadableDateString((LocalDate) datetime);
        } else if (datetime instanceof ZonedDateTime) {
            return StringFormatters.toReadableDateString((ZonedDateTime) datetime);
        } else {
            final String INVALID_TEMPORAL_TYPE_MESSAGE =
                    "Only LocalDate and ZonedDateTime accepted.";
            LOGGER.warn(INVALID_TEMPORAL_TYPE_MESSAGE);
            throw new InvalidTemporalTypeException(INVALID_TEMPORAL_TYPE_MESSAGE);
        }
    }

    public static void validateScheduledTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        if (startTime != null && endTime != null) {
            if (isSameDate(startTime, endTime)) {
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
        Set<Map.Entry<DayOfWeek, List<ScheduledActivity>>> weeklyActivitiesSet =
                schedule.getWeeklyActivities().entrySet();

        final long SECONDS_IN_HOUR = 3600;

        long totalWorkHours = weeklyActivitiesSet.stream()
                .flatMap(entry -> entry.getValue().stream())
                .filter(activity -> {
                    LocalDate activityDate = activity.getStartTime().toLocalDate();

                    return isWithinDateSpan(activityDate, startDate, endDate);
                })
                .mapToLong(activity -> {
                    long durationInSeconds =
                            activity.getEndTime().toEpochSecond() - activity.getStartTime().toEpochSecond();
                    long durationInHours = durationInSeconds / SECONDS_IN_HOUR;

                    return durationInHours;
                })
                .sum();

        return totalWorkHours;
    }

    public static boolean isWithinDateSpan(LocalDate activityDate, LocalDate startDate,
                                           LocalDate endDate) {
        return BooleanUtils.isWithinDateSpan(activityDate, startDate, endDate);
    }


    public static boolean isSameDate(Temporal comparans, Temporal comparandum) {
        if (comparans instanceof LocalDate && comparandum instanceof LocalDate) {
            return BooleanUtils.isSameDate(
                    (LocalDate) comparans,
                    (LocalDate) comparandum
            );
        } else if (comparans instanceof ZonedDateTime && comparandum instanceof ZonedDateTime) {
            return BooleanUtils.isSameDate(
                    (ZonedDateTime) comparans,
                    (ZonedDateTime) comparandum
            );
        } else {
            final String INVALID_TEMPORAL_TYPE_MESSAGE =
                    "Only LocalDate and ZonedDateTime accepted.";
            LOGGER.warn(INVALID_TEMPORAL_TYPE_MESSAGE);
            throw new InvalidTemporalTypeException(INVALID_TEMPORAL_TYPE_MESSAGE);
        }
    }

    public static int countWorkDays(Schedule schedule) {
        /*
             "IntStream mapToInt(ToIntFunction<? super T> mapper)"
             https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#mapToInt-java.util.function.ToIntFunction-
        */
        Collection<List<ScheduledActivity>> allActivities = schedule.getWeeklyActivities().values();
        ToIntFunction<ScheduledActivity> countActivityAsOne =
                activity -> 1;

        int workDaysCount = allActivities.stream()
                .flatMap(Collection::stream)
                .mapToInt(countActivityAsOne)
                .sum();

        return workDaysCount;
    }


    private ScheduleUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
