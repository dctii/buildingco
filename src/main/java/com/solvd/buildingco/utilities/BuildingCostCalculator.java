package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.buildings.BuildingConstants;
import com.solvd.buildingco.exception.BuildingTypeException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.stakeholders.employees.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.solvd.buildingco.utilities.ScheduleUtils.getDateFormat;

public class BuildingCostCalculator {

    public static BigDecimal calculateMaterialCost(Order order, BigDecimal[] additionalCosts) {
        BigDecimal totalMaterialCost = order.getTotalCost();

        for (BigDecimal additionalCost : additionalCosts) {
            totalMaterialCost.add(additionalCost);
        }

        return totalMaterialCost;
    }

    public static BigDecimal calculateMaterialCost(Order order) {
        BigDecimal totalMaterialCost = order.getTotalCost();

        return totalMaterialCost;
    }

    public static BigDecimal calculateLaborCost(ZonedDateTime customerEndDate,
                                                int constructionDays) {

        Schedule architectureSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                BuildingConstants.ARCHITECTURE_WORK_DESCRIPTION
        );
        Schedule constructionWorkerSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                BuildingConstants.CONSTRUCTION_WORK_DESCRIPTION
        );
        Schedule projectManagerSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                BuildingConstants.PROJECT_MANAGEMENT_DESCRIPTION
        );
        Schedule engineerSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                BuildingConstants.ENGINEERING_DESCRIPTION
        );

        Employee architect, manager, engineer, worker;

        architect = Architect.createEmployee(architectureSchedule, new BigDecimal("35.0"));
        manager = ProjectManager.createEmployee(projectManagerSchedule, new BigDecimal("40.0"));
        engineer = Engineer.createEmployee(engineerSchedule, new BigDecimal("30.0"));
        worker = ConstructionWorker.createEmployee(constructionWorkerSchedule, new BigDecimal("15.0"));

        final DateTimeFormatter dateFormat = getDateFormat();

        String startDateStr =
                customerEndDate
                        .minusDays(constructionDays)
                        .toLocalDate()
                        .format(dateFormat);
        String endDateStr =
                customerEndDate
                        .toLocalDate()
                        .format(dateFormat);

        int architectDays = (int) Math.ceil(constructionDays / 5.0);
        String architectEndDateStr =
                customerEndDate
                        .minusDays(architectDays)
                        .toLocalDate()
                        .format(dateFormat);

        Employee[] employees = {worker, engineer, architect, manager};

        BigDecimal totalCost = BigDecimal.ZERO;

        /*
            Used to accumulate an amount of hours per Employee's pay rate multiplied by the
            amount of hours for the construction project. Uses ternary operator to input the
            planning time of the Architect if employee is said type.
        */

        for (Employee employee : employees) {
            String employeeEndDateStr =
                    (employee instanceof Architect)
                            ? architectEndDateStr
                            : endDateStr;
            BigDecimal employeeHours =
                    new BigDecimal(
                            employee
                                    .getWorkHours(startDateStr, employeeEndDateStr));
            BigDecimal employeeCost =
                    employee
                            .getPayRate()
                            .multiply(employeeHours);

            // update total labor cost
            totalCost = totalCost.add(employeeCost);
        }

        return totalCost;
    }

    // calculate construction days with respect to Building, used for generating
    // employee schedule
    public static int calculateConstructionDays(
            String type,
            int squareFootage,
            int numberOfLevels) {

        final String BUILDING_TYPE_EXCEPTION_MESSAGE = "Building type not found.";

        int baseConstructionDays = 0;
        int additionalTimePerLevel = 0;

        if (type.equalsIgnoreCase(BuildingConstants.SKYSCRAPER_BUILDING_TYPE)) {

            baseConstructionDays = squareFootage / 50;
            additionalTimePerLevel = (int) Math.ceil(baseConstructionDays * numberOfLevels);

        } else if (type.equalsIgnoreCase(BuildingConstants.INDUSTRIAL_BUILDING_TYPE)) {

            baseConstructionDays = squareFootage / 100;
            additionalTimePerLevel = (int) Math.ceil(baseConstructionDays * (numberOfLevels - 1));

        } else {
            throw new BuildingTypeException(BUILDING_TYPE_EXCEPTION_MESSAGE);
        }
        return baseConstructionDays + additionalTimePerLevel;
    }


    public static BigDecimal calculateBuildingCost(Building<BigDecimal> building,
                                                   ZonedDateTime customerEndDate) {
        // get Order for materials
        Order materialOrder = building.generateMaterialOrder();
        // get total cost of Order
        BigDecimal materialCost = materialOrder.getTotalCost();
        // get labor cost of construction project
        BigDecimal laborCost = building.calculateLaborCost(customerEndDate);

        // sums up material and labor costs
        return materialCost.add(laborCost);
    }


}

