package com.solvd.buildingco.scheduling;

import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class Schedule {
    private Map<DayOfWeek, List<ScheduledActivity>> weeklyActivities = new HashMap<>();

    public Schedule() {
        initializeDefaultSchedule();
    }

    public Schedule(Map<DayOfWeek, List<ScheduledActivity>> weeklyActivities) {
        this.weeklyActivities = weeklyActivities;
    }


    /*
    Needed to avoid a `NullPointerException`, bug that appeared is below:
        Exception in thread "main" java.lang.NullPointerException
        at com.solvd.buildingco.scheduling.Schedule.addActivity(Schedule.java:27)
        at com.solvd.buildingco.buildings.House.generateEmployeeSchedule(House.java:100)
        at com.solvd.buildingco.buildings.House.calculateLaborCost(House.java:113)
        at com.solvd.buildingco.Main.main(Main.java:78)

    Create initializer so when calling on addActivity, it has a usable reference
    */
    private void initializeDefaultSchedule() {
        for (DayOfWeek day : DayOfWeek.values()) {
            weeklyActivities.put(day, new LinkedList<>());
        }
    }

    // chainable activity adder for Schedule
    public Schedule addActivity(ScheduledActivity activity) {
        DayOfWeek day = activity.getDay();
        weeklyActivities.get(day).add(activity);

        return this;
    }

    public Map<DayOfWeek, List<ScheduledActivity>> getWeeklyActivities() {
        return weeklyActivities;
    }


    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();

        String[] fieldNames = {"weeklyActivities"};

        String result = Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(this, fieldName);
                    return fieldValue != null
                            ? StringFormatters.stateEquivalence(
                            fieldName,
                            StringFormatters.mapToString((Map<?, List<?>>) fieldValue)
                    )
                            : StringConstants.EMPTY_STRING;

                })
                .filter(fieldStr -> !fieldStr.isEmpty())
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

        return className + StringFormatters.nestInCurlyBraces(result);
    }
}
