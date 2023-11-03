package com.solvd.buildingco.scheduling;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private Map<DayOfWeek, List<ScheduledActivity>> weeklyActivities;

    public Schedule() {
        this.weeklyActivities = new HashMap<>();
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
            weeklyActivities.put(day, new ArrayList<>());
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
    public static class ScheduledActivity {
        private String description;
        private String location;
        private DayOfWeek day;
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;

        public ScheduledActivity(String description, ZonedDateTime startTime, ZonedDateTime endTime) {
            this.description = description; // description/name/type of activity
            this.startTime = startTime;
            this.endTime = endTime;
            this.day = startTime.getDayOfWeek(); // get DOTW that belongs to startTime ZonedDate
        }

        // getters and setters for ScheduledActivity

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public ZonedDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(ZonedDateTime startTime) {
            this.startTime = startTime;
        }

        public ZonedDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(ZonedDateTime endTime) {
            this.endTime = endTime;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public void setDay(DayOfWeek day) {
            this.day = day;
        }
    }


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
