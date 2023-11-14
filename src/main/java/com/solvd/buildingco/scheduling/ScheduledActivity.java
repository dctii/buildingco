package com.solvd.buildingco.scheduling;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public class ScheduledActivity {
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
