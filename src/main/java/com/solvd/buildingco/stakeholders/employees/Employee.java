package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.exception.InvalidDateFormatException;
import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.stakeholders.Stakeholder;
import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.ScheduleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public abstract class Employee extends Stakeholder {
    private static final Logger LOGGER = LogManager.getLogger(Employee.class);
    private PayRate<BigDecimal> payRate;
    private Schedule schedule;
    private String personnelType;

    public Employee() {
        super();
    }

    public Employee(String[] nameParts) {
        super(nameParts);
    }

    public Employee(String[] nameParts, PayRate payRate) {
        super(nameParts);
        this.payRate = payRate;
    }

    public Employee(String[] nameParts, PayRate payRate, Schedule schedule) {
        super(nameParts);
        this.payRate = payRate;
        this.schedule = schedule;
    }

    public Employee(String[] nameParts, String[] postNominals, String[] organizationNames,
                    String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                    PayRate payRate, Schedule schedule, String personnelType) {

        super(nameParts, postNominals, organizationNames, roles,
                addresses, phoneNumbers, emails);

        this.personnelType = personnelType;
        this.payRate = payRate;
        this.schedule = schedule;
    }


    // iterates through the employee's schedule to get their work hours
    public long getWorkHours(String startDateStr, String endDateStr) {

        final DateTimeFormatter dateFormat = ScheduleUtils.getDateFormat();
        final String INVALID_START_DATE_STRING_FORMAT_MESSAGE =
                "'startDateStr' is not in the correct format, so it cannot be parsed by LocalDate" +
                        ".parse()";
        final String INVALID_END_DATE_STRING_FORMAT_MESSAGE =
                "'endDateStr' is not in the correct format, so it cannot be parsed by LocalDate" +
                        ".parse()";

        // range for work hours
        LocalDate startDate, endDate;

        try {
            startDate = LocalDate.parse(startDateStr, dateFormat);
        } catch (DateTimeParseException e) {
            LOGGER.warn(INVALID_START_DATE_STRING_FORMAT_MESSAGE);
            throw new InvalidDateFormatException(INVALID_START_DATE_STRING_FORMAT_MESSAGE);
        }

        try {
            endDate = LocalDate.parse(endDateStr, dateFormat);
        } catch (DateTimeParseException e) {
            LOGGER.warn(INVALID_END_DATE_STRING_FORMAT_MESSAGE);
            throw new InvalidDateFormatException(INVALID_END_DATE_STRING_FORMAT_MESSAGE);
        }

        // if there is a schedule, iterate and see how many hours the employee has on their schedule
        long totalWorkHours;
        if (schedule != null) {
            totalWorkHours =
                    ScheduleUtils.calculateTotalWorkHours(schedule, startDate, endDate);
        } else {
            totalWorkHours = 0;
        }

        return totalWorkHours;
    }


    // getters and setters

    public BigDecimal getPayRate() {
        return payRate.getRate();
    }

    public void setPayRate(PayRate payRate) {
        this.payRate = payRate;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;

    }

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }


    @Override
    public String toString() {
        String className = "Personnel";
        String stakeholderStr = super.toString();
        String[] fieldNames = {"payRate", "schedule",
                "personnelType"};

        StringBuilder builder = new StringBuilder(className + "{");
        builder.append(stakeholderStr);

        for (String fieldName : fieldNames) {
            Object fieldValue = ReflectionUtils.getField(this, fieldName);
            if (fieldValue != null) {
                builder
                        .append(",")
                        .append(fieldName)
                        .append("=")
                        .append(fieldValue);
            }
        }

        builder.append("}");
        return builder.toString();
    }


}
