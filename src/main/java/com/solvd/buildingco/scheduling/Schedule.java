package com.solvd.buildingco.scheduling;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Schedule {
    private final Map<DayOfWeek, List<ScheduledActivity>> weeklyActivities = new HashMap<>();

    public Schedule() {
        initializeDefaultSchedule();
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

    // specific activity of a Schedule instance.


    @Override
    public String toString() {
        String className = "Schedule";
        StringBuilder builder = new StringBuilder(className + "{");

        for (Map.Entry<DayOfWeek, List<ScheduledActivity>> entry : weeklyActivities.entrySet()) {
            builder.append(entry.getKey()).append(": [");
            for (ScheduledActivity activity : entry.getValue()) {
                builder.append("ScheduledActivity{")
                        .append("description: ").append(activity.getDescription())
                        .append(", startTime: ").append(activity.getStartTime())
                        .append(", endTime: ").append(activity.getEndTime())
                        .append("}, ");
            }
            builder.append("], ");
        }
        builder.append("}");
        return builder.toString();
    }

}
