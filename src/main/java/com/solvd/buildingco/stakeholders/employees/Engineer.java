package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.finance.HourlyRate;
import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.utilities.ReflectionUtils;

import java.math.BigDecimal;

import static com.solvd.buildingco.stakeholders.employees.Personnel.ENGINEER;

// TODO: add options for different kinds of Engineers

public class Engineer extends Employee {

    public Engineer(){
        super();
    }

    public Engineer(String[] nameParts){
        super(nameParts);
    }

    public Engineer(String[] nameParts, PayRate payRate) {
        super(nameParts, payRate);
    }

    public Engineer(String[] nameParts, PayRate payRate, Schedule schedule) {
        super(nameParts, payRate, schedule);
    }
    public Engineer(String[] nameParts, String[] postNominals, String[] organizationNames,
                    String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                    PayRate payRate, Schedule schedule, String personnelType) {
        super(nameParts, postNominals, organizationNames, roles, addresses, phoneNumbers, emails
                , payRate, schedule, personnelType);
    }

    // factory method for Engineer
    public static Engineer createEmployee(Schedule schedule, BigDecimal ratePerHour) {
        String personnelType = ENGINEER.getPersonnelType();
        String[] nameParts = {null, null, null, null};
        String[] postNominals = {};
        String[] organizationNames = {BuildingCoConstants.ORGANIZATION_NAME};
        String[] roles = {personnelType};
        String[] addresses = {BuildingCoConstants.ADDRESS_HQ};
        String[] phoneNumbers = {BuildingCoConstants.PHONE_NUMBER_HQ};
        String[] emails = {ENGINEER.getDepartmentEmailHandle() + BuildingCoConstants.DOMAIN_NAME};

        // hourly rate passed in upon instantiation
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);

        return new Engineer(nameParts, postNominals, organizationNames, roles, addresses,
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
            Object fieldValue = ReflectionUtils.getField(this, fieldName);
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
