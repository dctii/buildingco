package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.finance.HourlyRate;
import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.utilities.StringFormatters;

import java.math.BigDecimal;

import static com.solvd.buildingco.stakeholders.employees.Personnel.ARCHITECT;

public class Architect extends Employee {

    public Architect() {
        super();
    }

    public Architect(String[] nameParts) {
        super(nameParts);
    }

    public Architect(String[] nameParts, PayRate payRate) {
        super(nameParts, payRate);
    }

    public Architect(String[] nameParts, PayRate payRate, Schedule schedule) {
        super(nameParts, payRate, schedule);
    }

    public Architect(String[] nameParts, String[] postNominals, String[] organizationNames,
                     String[] roles, String[] addresses, String[] phoneNumbers, String[] emails,
                     PayRate payRate, Schedule schedule, String personnelType) {
        super(nameParts, postNominals, organizationNames, roles, addresses, phoneNumbers, emails
                , payRate, schedule, personnelType);
    }

    // factory method for Architect

    public static Architect createEmployee(Schedule schedule, BigDecimal ratePerHour) {
        String personnelType = ARCHITECT.getPersonnelType();
        String[] nameParts = {"Bing", "Bong", "Bilson", "Jr."};
        String[] postNominals = {"PhD"};
        String[] organizationNames = {BuildingCoConstants.ORGANIZATION_NAME};
        String[] roles = {personnelType};
        String[] addresses = {BuildingCoConstants.ADDRESS_HQ};
        String[] phoneNumbers = {BuildingCoConstants.PHONE_NUMBER_HQ};
        String[] emails = {ARCHITECT.getDepartmentEmailHandle() + BuildingCoConstants.DOMAIN_NAME};

        // hourly rate passed in upon instantiation
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);

        return new Architect(nameParts, postNominals, organizationNames, roles, addresses,
                phoneNumbers, emails, hourlyRate, schedule, personnelType);
    }

    @Override
    public String toString() {
        Class<?> currClass = Architect.class;

        String parentToString = super.toString();

        return StringFormatters.buildToString(
                currClass,
                parentToString
        );
    }
}
