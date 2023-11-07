package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.finance.HourlyRate;
import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;

public class Architect extends Employee {
    public Architect(String[] nameParts, String[] postNominals, String[] organizationNames,
                     String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                     PayRate payRate, Schedule schedule, String personnelType) {
        super(nameParts, postNominals, organizationNames, roles, addresses, phoneNumbers, emails
                , payRate, schedule, personnelType);
    }

    // factory method for Architect

    public static Architect createEmployee(Schedule schedule, BigDecimal ratePerHour) {
        String personnelType = "Architect";
        String[] nameParts = {null, null, null, null};
        String[] postNominals = {};
        String[] organizationNames = {"Building Co."};
        String[] roles = {personnelType};
        String[] addresses = {"123456 Builder Lane, Los Angeles, CA 90210"};
        String[] phoneNumbers = {"+13105551234"};
        String[] emails = {"architects@buildingco123.com"};

        // hourly rate passed in upon instantiation
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);

        return new Architect(nameParts, postNominals, organizationNames, roles, addresses,
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
