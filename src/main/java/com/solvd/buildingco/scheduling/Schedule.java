package com.solvd.buildingco.scheduling;

import com.solvd.buildingco.utilities.StringFormatters;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        Stream.of(DayOfWeek.values())
                .forEach(day -> weeklyActivities.put(day, new LinkedList<>()));
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
        Class<?> currClass = Schedule.class;
        String[] fieldNames = {"weeklyActivities"};

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
