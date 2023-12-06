package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.exception.BuildingTypeException;
import com.solvd.buildingco.exception.ScheduleMismatchException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.stakeholders.employees.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.INDUSTRIAL_BUILDING;
import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.SKYSCRAPER;
import static com.solvd.buildingco.stakeholders.employees.Personnel.*;

public class BuildingCostCalculator {
    private static final Logger LOGGER = LogManager.getLogger(BuildingCostCalculator.class);

    public static BigDecimal calculateMaterialCost(Order order, BigDecimal[] additionalCosts) {
        BigDecimal totalMaterialCost = order.getTotalCost();

        BigDecimal sum = Arrays.stream(additionalCosts)
                .reduce(BigDecimal.ZERO, BigDecimalUtils.ADD_OPERATION);

        totalMaterialCost = totalMaterialCost.add(sum);

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
                ARCHITECT.getDefaultActivityDescription()
        );
        Schedule constructionWorkerSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                CONSTRUCTION_WORKER.getDefaultActivityDescription()
        );
        Schedule projectManagerSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                PROJECT_MANAGER.getDefaultActivityDescription()
        );
        Schedule engineerSchedule = ScheduleUtils.generateEmployeeSchedule(
                customerEndDate,
                constructionDays,
                ENGINEER.getDefaultActivityDescription()
        );

        try {
            Schedule[] employeeSchedules = {
                    constructionWorkerSchedule,
                    projectManagerSchedule,
                    engineerSchedule
            };

            validateSchedule(
                    architectureSchedule,
                    NumberUtils.roundToInt(constructionDays * 0.25)
            );

            Arrays.stream(employeeSchedules)
                    .forEach(schedule -> validateSchedule(schedule, constructionDays));
        } catch (ScheduleMismatchException e) {
            LOGGER.error(e);
        }

        Employee architect, manager, engineer, worker;

        architect = Architect.createEmployee(architectureSchedule, new BigDecimal("35.0"));
        manager = ProjectManager.createEmployee(projectManagerSchedule, new BigDecimal("40.0"));
        engineer = Engineer.createEmployee(engineerSchedule, new BigDecimal("30.0"));
        worker = ConstructionWorker.createEmployee(constructionWorkerSchedule, new BigDecimal("15.0"));

        final DateTimeFormatter dateFormat = ScheduleUtils.getDateFormat();

        String startDateStr =
                customerEndDate
                        .minusDays(constructionDays)
                        .toLocalDate()
                        .format(dateFormat);
        String endDateStr =
                customerEndDate
                        .toLocalDate()
                        .format(dateFormat);

        int architectDays = NumberUtils.roundToInt(Math.ceil(constructionDays / 5.0));
        String architectEndDateStr =
                customerEndDate
                        .minusDays(architectDays)
                        .toLocalDate()
                        .format(dateFormat);

        Employee[] employees = {worker, engineer, architect, manager};

        BigDecimal totalCost =
                Arrays.stream(employees)
                        .map(employee -> {
                            // get end date for employee, if Architect, use respective spec
                            String employeeEndDateStr =
                                    (employee instanceof Architect)
                                            ? architectEndDateStr
                                            : endDateStr;
                            // calculate number of hours, use getWorkHours and pass in date
                            // boundaries
                            BigDecimal employeeHours = new BigDecimal(
                                    employee.getWorkHours(
                                            startDateStr,
                                            employeeEndDateStr
                                    )
                            );
                            // calculate the cost for the employee by multiplying their pay rate
                            // by hours worked
                            BigDecimal employeeCost =
                                    employee.getPayRate()
                                            .getRate()
                                            .multiply(employeeHours);
                            return employeeCost;
                        })
                        // sum up all individual employee costs to get the total labor cost
                        .reduce(BigDecimal.ZERO, BigDecimalUtils.ADD_OPERATION);

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

        if (type.equalsIgnoreCase(SKYSCRAPER.getBuildingType())) {

            baseConstructionDays = squareFootage / 50;
            additionalTimePerLevel =
                    NumberUtils.roundToInt((double) (baseConstructionDays * numberOfLevels));

        } else if (type.equalsIgnoreCase(INDUSTRIAL_BUILDING.getBuildingType())) {

            baseConstructionDays = squareFootage / 100;
            additionalTimePerLevel =
                    NumberUtils.roundToInt((double) (baseConstructionDays * (numberOfLevels - 1)));

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

    private static void validateSchedule(Schedule schedule, int constructionDays) {
        int scheduleDays = ScheduleUtils.countWorkDays(schedule);
        if (scheduleDays != constructionDays) {
            String scheduleMismatchMessage =
                    String.format(
                            "%d != %d; Scheduled days do not match construction days.",
                            scheduleDays,
                            constructionDays
                    );
            throw new ScheduleMismatchException(scheduleMismatchMessage);
        }
    }

    private BuildingCostCalculator() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }

}

