package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;
import com.solvd.buildingco.exception.BuildingTypeException;
import com.solvd.buildingco.exception.ScheduleMismatchException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.stakeholders.employees.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.INDUSTRIAL_BUILDING;
import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.SKYSCRAPER;
import static com.solvd.buildingco.buildings.ResidentialBuildingSpecifications.HOUSE;
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
        return order.getTotalCost();
    }

    public static BigDecimal calculateLaborCost(House house, ZonedDateTime endDate) {
        int totalSquareFootage = house.getSquareFootage();
        int numArchitects =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * HOUSE.getArchitectsPerSquareFoot()), 1);
        int numProjectManagers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * HOUSE.getProjectManagersPerSquareFoot()), 1);
        int numEngineers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * HOUSE.getEngineersPerSquareFoot()), 1);
        int numWorkers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * HOUSE.getWorkersPerSquareFoot()), 1);


        BigDecimal totalLaborCost = calculateEmployeesLaborCost(
                house,
                endDate,
                ARCHITECT,
                numArchitects
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        house,
                        endDate,
                        PROJECT_MANAGER,
                        numProjectManagers
                )
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        house,
                        endDate,
                        ENGINEER,
                        numEngineers
                )
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        house,
                        endDate,
                        CONSTRUCTION_WORKER,
                        numWorkers
                )
        );

        return totalLaborCost;
    }

    public static BigDecimal calculateLaborCost(IndustrialBuilding industrialBuilding, ZonedDateTime endDate) {
        int totalSquareFootage = industrialBuilding.getSquareFootage();
        int numArchitects =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * INDUSTRIAL_BUILDING.getArchitectsPerSquareFoot()), 1);
        int numProjectManagers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * INDUSTRIAL_BUILDING.getProjectManagersPerSquareFoot()), 1);
        int numEngineers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * INDUSTRIAL_BUILDING.getEngineersPerSquareFoot()), 1);
        int numWorkers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * INDUSTRIAL_BUILDING.getWorkersPerSquareFoot())
                        , 1);

        BigDecimal totalLaborCost = calculateEmployeesLaborCost(
                industrialBuilding,
                endDate,
                ARCHITECT,
                numArchitects
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        industrialBuilding,
                        endDate,
                        PROJECT_MANAGER,
                        numProjectManagers
                )
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        industrialBuilding,
                        endDate,
                        ENGINEER,
                        numEngineers
                )
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        industrialBuilding,
                        endDate,
                        CONSTRUCTION_WORKER,
                        numWorkers
                )
        );

        return totalLaborCost;
    }

    public static BigDecimal calculateLaborCost(Skyscraper skyscraper, ZonedDateTime endDate) {
        int totalSquareFootage = skyscraper.getSquareFootagePerLevel();
        int numArchitects =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * SKYSCRAPER.getArchitectsPerSquareFoot()), 1);
        int numProjectManagers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * SKYSCRAPER.getProjectManagersPerSquareFoot()), 1);
        int numEngineers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * SKYSCRAPER.getEngineersPerSquareFoot()), 1);
        int numWorkers =
                Math.max(NumberUtils.roundToInt(totalSquareFootage * SKYSCRAPER.getWorkersPerSquareFoot())
                        , 1);

        BigDecimal totalLaborCost = calculateEmployeesLaborCost(
                skyscraper,
                endDate,
                ARCHITECT,
                numArchitects
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        skyscraper,
                        endDate,
                        PROJECT_MANAGER,
                        numProjectManagers
                )
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        skyscraper,
                        endDate,
                        ENGINEER,
                        numEngineers
                )
        );

        totalLaborCost = totalLaborCost.add(
                calculateEmployeesLaborCost(
                        skyscraper,
                        endDate,
                        CONSTRUCTION_WORKER,
                        numWorkers
                )
        );

        return totalLaborCost;
    }

    private static BigDecimal calculateEmployeesLaborCost(
            Building building,
            ZonedDateTime endDate,
            Personnel PERSONNEL,
            int numEmployees
    ) {
        int constructionDays = building.getConstructionDays();

        return IntStream.range(0, numEmployees).mapToObj(i -> {
                    Schedule schedule = ScheduleUtils.generateEmployeeSchedule(
                            endDate,
                            constructionDays,
                            PERSONNEL.getDefaultActivityDescription()
                    );
                    validateSchedule(
                            schedule,
                            !PERSONNEL.equals(ARCHITECT)
                                    ? constructionDays
                                    : NumberUtils.roundToInt(constructionDays * 0.25)
                    );

                    Employee employee = generateEmployee(PERSONNEL, schedule);

                    return calculateEmployeeCost(employee, building, endDate);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Employee generateEmployee(Personnel PERSONNEL, Schedule schedule) {
        Employee employee = null;

        if (PERSONNEL.equals(ARCHITECT)) {
            employee = Architect.createEmployee(
                    schedule,
                    PERSONNEL.getAverageRatePerHour()
            );
        } else if (PERSONNEL.equals(PROJECT_MANAGER)) {
            employee = ProjectManager.createEmployee(
                    schedule,
                    PERSONNEL.getAverageRatePerHour()
            );
        } else if (PERSONNEL.equals(ENGINEER)) {
            employee = Engineer.createEmployee(
                    schedule,
                    PERSONNEL.getAverageRatePerHour()
            );
        } else if (PERSONNEL.equals(CONSTRUCTION_WORKER)) {
            employee = ConstructionWorker.createEmployee(
                    schedule,
                    PERSONNEL.getAverageRatePerHour()
            );
        }
        return employee;
    }

    private static BigDecimal calculateEmployeeCost(
            Employee employee,
            Building building,
            ZonedDateTime endDate
    ) {
        BigDecimal employeeHours = calculateEmployeeWorkHours(employee, building, endDate);

        // return employee cost
        return employee.getPayRate()
                .getRate()
                .multiply(employeeHours);
    }

    private static BigDecimal calculateEmployeeWorkHours(
            Employee employee,
            Building building,
            ZonedDateTime endDate
    ) {
        int constructionDays = building.getConstructionDays();
        int architectDays = NumberUtils.roundToInt(Math.ceil(constructionDays / 5.0));
        ZonedDateTime startDate = calculateStartDate(endDate, constructionDays);
        ZonedDateTime architectEndDate = calculateStartDate(endDate, architectDays);

        String startDateStr = toLocalDateString(startDate);
        String endDateStr = employee instanceof Architect
                ? toLocalDateString(architectEndDate)
                : toLocalDateString(endDate);

        return new BigDecimal(
                employee.getWorkHours(
                        startDateStr,
                        endDateStr
                )
        );
    }

    private static ZonedDateTime calculateStartDate(ZonedDateTime endDate, int constructionDays) {
        return endDate
                .minusDays(constructionDays);
    }

    private static String toLocalDateString(ZonedDateTime datetime) {
        return datetime
                .toLocalDate()
                .format(ScheduleUtils.getDateFormat());
    }


    // calculate construction days with respect to Building, used for generating
    // employee schedule
    public static int calculateConstructionDays(
            String type,
            int squareFootage,
            int numberOfLevels) {

        final String BUILDING_TYPE_EXCEPTION_MESSAGE = "Building type not found.";

        int baseConstructionDays;
        int additionalTimePerLevel;

        if (type.equalsIgnoreCase(SKYSCRAPER.getBuildingType())) {

            baseConstructionDays = squareFootage / 275;
            additionalTimePerLevel =
                    NumberUtils.roundToInt((double) (baseConstructionDays * numberOfLevels) / 3.5);

        } else if (type.equalsIgnoreCase(INDUSTRIAL_BUILDING.getBuildingType())) {

            baseConstructionDays = squareFootage / 100;
            additionalTimePerLevel =
                    NumberUtils.roundToInt((double) (baseConstructionDays * (numberOfLevels - 1)) / 2);

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

        // difference can be within 2 days
        if (Math.abs(scheduleDays - constructionDays) > 1) {
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

