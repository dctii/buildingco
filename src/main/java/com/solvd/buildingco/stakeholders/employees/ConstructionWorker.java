package com.solvd.buildingco.stakeholders.employees;

import com.solvd.buildingco.finance.HourlyRate;
import com.solvd.buildingco.finance.PayRate;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.utilities.StringFormatters;

import java.math.BigDecimal;

import static com.solvd.buildingco.stakeholders.employees.Personnel.CONSTRUCTION_WORKER;

public class ConstructionWorker extends Employee {

    public ConstructionWorker() {
        super();
    }

    public ConstructionWorker(String[] nameParts) {
        super(nameParts);
    }

    public ConstructionWorker(String[] nameParts, PayRate payRate) {
        super(nameParts, payRate);
    }

    public ConstructionWorker(String[] nameParts, PayRate payRate, Schedule schedule) {
        super(nameParts, payRate, schedule);
    }

    public ConstructionWorker(String[] nameParts, String[] postNominals, String[] organizationNames,
                              String[] roles, String[] addresses, String[] phoneNumbers,
                              String[] emails, PayRate payRate, Schedule schedule, String personnelType) {
        super(nameParts, postNominals, organizationNames, roles, addresses, phoneNumbers, emails
                , payRate, schedule, personnelType);
    }

    // factory method for ConstructionWorker

    public static ConstructionWorker createEmployee(Schedule schedule,
                                                    BigDecimal ratePerHour) {
        String personnelType = CONSTRUCTION_WORKER.getPersonnelType();
        String[] nameParts = {null, null, null, null};
        String[] postNominals = {};
        String[] organizationNames = {BuildingCoConstants.ORGANIZATION_NAME};
        String[] roles = {personnelType};
        String[] addresses = {BuildingCoConstants.ADDRESS_HQ};
        String[] phoneNumbers = {BuildingCoConstants.PHONE_NUMBER_HQ};
        String[] emails =
                {CONSTRUCTION_WORKER.getDepartmentEmailHandle() + BuildingCoConstants.DOMAIN_NAME};

        // hourly rate passed in upon instantiation
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);

        return new ConstructionWorker(nameParts, postNominals, organizationNames, roles, addresses,
                phoneNumbers, emails, hourlyRate, schedule, personnelType);
    }

    @Override
    public String toString() {
        Class<?> currClass = ConstructionWorker.class;

        String parentToString = super.toString();

        return StringFormatters.buildToString(
                currClass,
                parentToString
        );
    }
}
