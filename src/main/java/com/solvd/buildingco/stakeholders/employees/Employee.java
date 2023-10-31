package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.Schedule.ScheduledActivity;
import com.solvd.buildingco.stakeholders.Stakeholder;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Employee extends Stakeholder {
    private PayRate payRate;
    private Schedule schedule;
    private String personnelType;

    public Employee(String[] nameParts, String[] postNominals, String[] organizationNames,
                    String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                    PayRate payRate, Schedule schedule, String personnelType) {
        super(nameParts, postNominals, organizationNames, roles,
                addresses, phoneNumbers, emails);

        this.personnelType = personnelType;

        this.payRate = payRate;

        this.schedule = schedule;
    }

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

    public long getWorkHours(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        long totalWorkHours = 0;

        if (schedule != null) {
            for (Map.Entry<DayOfWeek, List<ScheduledActivity>> entry :
                    schedule.getWeeklyActivities().entrySet()) {
                for (ScheduledActivity activity : entry.getValue()) {
                    LocalDate activityDate = activity.getStartTime().toLocalDate();
                    boolean isWithinDateSpan =
                            (activityDate.isEqual(startDate) || activityDate.isAfter(startDate)) &&
                            (activityDate.isEqual(endDate) || activityDate.isBefore(endDate));

                    if (isWithinDateSpan) {
                        long hours =
                                activity.getEndTime().toEpochSecond() - activity.getStartTime().toEpochSecond();
                        long secondsInHour = 3600;
                        totalWorkHours += hours / secondsInHour;
                    }
                }
            }
        }

        return totalWorkHours;
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
            Object fieldValue = FieldUtils.getField(this, fieldName);
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
