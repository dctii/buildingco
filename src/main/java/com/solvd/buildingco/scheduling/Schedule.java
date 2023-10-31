package com.solvd.buildingco.scheduling;

import com.solvd.buildingco.stakeholders.employees.Employee;

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

    private void initializeDefaultSchedule() {
        for (DayOfWeek day : DayOfWeek.values()) {
            weeklyActivities.put(day, new ArrayList<>());
        }
    }

    public Schedule addActivity(ScheduledActivity activity) {
        DayOfWeek day = activity.getDay();
        weeklyActivities.get(day).add(activity);
        return this;
    }

    public Map<DayOfWeek, List<ScheduledActivity>> getWeeklyActivities() {
        return weeklyActivities;
    }

    public static class ScheduledActivity {
        private List<Employee> assignedPersonnel;
        private String description;
        private String location;
        private DayOfWeek day;
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;

        public ScheduledActivity(String description, ZonedDateTime startTime, ZonedDateTime endTime) {
            this.description = description;
            this.startTime = startTime;
            this.endTime = endTime;
            this.assignedPersonnel = new ArrayList<>();
            this.day = startTime.getDayOfWeek();
        }

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

        public List<Employee> getAssignedPersonnel() {
            return assignedPersonnel;
        }

        public void setAssignedPersonnel(List<Employee> assignedPersonnel) {
            this.assignedPersonnel = assignedPersonnel;
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
