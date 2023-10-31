package com.solvd.buildingco.scheduling;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Availability {
    private Map<DayOfWeek, TimeSlot> weeklyAvailability;

    public Availability() {
        this.weeklyAvailability = new HashMap<>();
        initializeDefaultAvailability();
    }

    private void initializeDefaultAvailability() {
        for (DayOfWeek day : DayOfWeek.values()) {
            boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
            if (isWeekend) {
                weeklyAvailability.put(day, null);
            } else if (day == DayOfWeek.FRIDAY) {
                LocalTime startTime = LocalTime.parse("09:00:00");
                LocalTime endTime = LocalTime.parse("14:30:00");
                TimeSlot timeSlot = new TimeSlot(startTime, endTime);
                weeklyAvailability.put(day, timeSlot);
            } else {
                LocalTime startTime = LocalTime.parse("09:00:00");
                LocalTime endTime = LocalTime.parse("17:00:00");
                TimeSlot timeSlot = new TimeSlot(startTime, endTime);
                weeklyAvailability.put(day, timeSlot);
            }
        }
    }

    public Map<DayOfWeek, TimeSlot> getWeeklyAvailability() {
        return weeklyAvailability;
    }

    public void setAvailabilityForDay(DayOfWeek day, TimeSlot timeSlot) {
        weeklyAvailability.put(day, timeSlot);
    }

    public static class TimeSlot {
        private LocalTime startTime;
        private LocalTime endTime;

        public TimeSlot(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }
    }

    @Override
    public String toString() {
        String className = "Availability";
        StringBuilder builder = new StringBuilder(className + "{");

        for (Map.Entry<DayOfWeek, TimeSlot> entry : weeklyAvailability.entrySet()) {
            builder.append(entry.getKey()).append(": ");
            if (entry.getValue() == null) {
                builder.append("OFF");
            } else {
                builder.append(entry.getValue().getStartTime())
                        .append(" - ")
                        .append(entry.getValue().getEndTime());
            }
            builder.append(", ");
        }
        builder.append("}");
        return builder.toString();
    }

}
