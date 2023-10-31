package com.solvd.buildingco.stakeholders;

/* PersonnelType:
 * Workers
 * Managers
 * Architects
 * Safety Inspectors
 * */

import com.solvd.buildingco.enums.PersonnelType;
import com.solvd.buildingco.finance.payrates.PayRate;
import com.solvd.buildingco.scheduling.Availability;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.Schedule.ScheduledActivity;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Personnel extends Stakeholder {
    private PayRate payRate;
    private Schedule schedule;
    private Availability availability;
    private PersonnelType personnelType;

    public Personnel(String[] nameParts, String[] postNominals, String[] organizationNames,
                     String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                     PayRate payRate, Schedule schedule,
                     Availability availability, PersonnelType personnelType) {
        super(nameParts, postNominals, organizationNames, roles,
                addresses, phoneNumbers, emails);

        this.personnelType = personnelType;

        this.payRate = payRate;

        this.schedule = schedule;
        this.availability = availability;
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

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public PersonnelType getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(PersonnelType personnelType) {
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
        String[] fieldNames = {"payRate", "schedule", "availability",
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
