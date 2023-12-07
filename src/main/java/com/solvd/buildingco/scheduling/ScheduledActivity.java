package com.solvd.buildingco.scheduling;

import com.solvd.buildingco.exception.InvalidValueException;
import com.solvd.buildingco.utilities.ScheduleUtils;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public class ScheduledActivity {
    private static final Logger LOGGER = LogManager.getLogger(ScheduledActivity.class);
    private String description;
    private String location;
    private DayOfWeek day;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    static final String BLANK_DESCRIPTION_MESSAGE = "The 'description' cannot be blank.";
    static final String BLANK_LOCATION_MESSAGE = "The 'location' cannot be blank.";

    public ScheduledActivity() {

    }

    public ScheduledActivity(String description) {
        if (StringUtils.isBlank(description)) {
            LOGGER.warn(BLANK_DESCRIPTION_MESSAGE);
            throw new InvalidValueException(BLANK_DESCRIPTION_MESSAGE);
        }
        this.description = description;
    }

    public ScheduledActivity(String description, String location) {
        if (StringUtils.isBlank(description)) {
            LOGGER.warn(BLANK_DESCRIPTION_MESSAGE);
            throw new InvalidValueException(BLANK_DESCRIPTION_MESSAGE);
        }

        if (StringUtils.isBlank(location)) {
            LOGGER.warn(BLANK_LOCATION_MESSAGE);
            throw new InvalidValueException(BLANK_LOCATION_MESSAGE);
        }

        this.description = description;
        this.location = location;
    }

    public ScheduledActivity(String description, ZonedDateTime startTime, ZonedDateTime endTime) {
        if (StringUtils.isBlank(description)) {
            LOGGER.warn(BLANK_DESCRIPTION_MESSAGE);
            throw new InvalidValueException(BLANK_DESCRIPTION_MESSAGE);
        }

        ScheduleUtils.validateScheduledTime(startTime, endTime);

        this.description = description; // description/name/type of activity
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = startTime.getDayOfWeek(); // get DOTW that belongs to startTime ZonedDate
    }

    public ScheduledActivity(String description, String location, ZonedDateTime startTime,
                             ZonedDateTime endTime
    ) {
        if (StringUtils.isBlank(description)) {
            LOGGER.warn(BLANK_DESCRIPTION_MESSAGE);
            throw new InvalidValueException(BLANK_DESCRIPTION_MESSAGE);
        }

        if (StringUtils.isBlank(location)) {
            LOGGER.warn(BLANK_LOCATION_MESSAGE);
            throw new InvalidValueException(BLANK_LOCATION_MESSAGE);
        }

        ScheduleUtils.validateScheduledTime(startTime, endTime);

        this.description = description; // description/name/type of activity
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = startTime.getDayOfWeek(); // get DOTW that belongs to startTime ZonedDate
        this.location = location;
    }

    // getters and setters for ScheduledActivity

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (StringUtils.isBlank(description)) {
            LOGGER.warn(BLANK_DESCRIPTION_MESSAGE);
            throw new InvalidValueException(BLANK_DESCRIPTION_MESSAGE);
        }

        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (StringUtils.isBlank(location)) {
            LOGGER.warn(BLANK_LOCATION_MESSAGE);
            throw new InvalidValueException(BLANK_LOCATION_MESSAGE);
        }

        this.location = location;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        ScheduleUtils.validateScheduledTime(startTime, this.endTime);

        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        ScheduleUtils.validateScheduledTime(this.startTime, endTime);

        this.endTime = endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    @Override
    public String toString() {
        Class<?> currClass = ScheduledActivity.class;
        String[] fieldNames = {
                "description",
                "location",
                "day",
                "startTime",
                "endTime"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);

    }
}
