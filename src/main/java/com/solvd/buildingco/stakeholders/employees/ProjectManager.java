package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.finance.HourlyRate;
import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;

public class ProjectManager extends Employee {
    public ProjectManager(String[] nameParts, String[] postNominals, String[] organizationNames,
                     String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                     PayRate payRate, Schedule schedule, String personnelType) {
        super(nameParts, postNominals, organizationNames, roles, addresses, phoneNumbers, emails
                , payRate, schedule, personnelType);
    }

    // factory method for ProjectManager
    public static ProjectManager createProjectManager(Schedule schedule, BigDecimal ratePerHour) {
        String personnelType = "Project Manager";
        String[] nameParts = {"John", "J", "Doe", null};
        String[] postNominals = {"PhD"};
        String[] organizationNames = {"Building Co."};
        String[] roles = {personnelType};
        String[] addresses = {"123456 Builder Lane, Los Angeles, CA 90210"};
        String[] phoneNumbers = {"+13105551234"};
        String[] emails = {"projectmanagers@buildingco123.com"};

        // hourly rate passed in upon instantiation
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);

        return new ProjectManager(nameParts, postNominals, organizationNames, roles, addresses,
                phoneNumbers, emails, hourlyRate, schedule, personnelType);
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        String stakeholderStr = super.toString();
        String[] fieldNames = {"payRate", "schedule", "personnelType"};

        StringBuilder builder = new StringBuilder(className + "{");
        builder.append(stakeholderStr.substring(stakeholderStr.indexOf("{") + 1));

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
            if (fieldValue != null) {
                builder.append(",")
                        .append(fieldName)
                        .append("=")
                        .append(fieldValue);
            }
        }

        builder.append("}");
        return builder.toString();
    }
}
