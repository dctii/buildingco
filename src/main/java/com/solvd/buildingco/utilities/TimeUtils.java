package com.solvd.buildingco.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static LocalTime getLocalTime(String time) {
        return LocalTime.parse(time);
    }
    public static ZonedDateTime getZonedTime(String date, String time, ZoneId zoneId) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        return ZonedDateTime.of(localDate, localTime, zoneId);
    }
}
