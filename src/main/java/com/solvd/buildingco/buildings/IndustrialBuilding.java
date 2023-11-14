package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidDimensionException;
import com.solvd.buildingco.exception.InvalidFloorNumberException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.ItemNames;
import com.solvd.buildingco.inventory.ItemRepository;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.ScheduledActivity;
import com.solvd.buildingco.stakeholders.employees.*;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.solvd.buildingco.buildings.BuildingConstants.*;
import static com.solvd.buildingco.scheduling.ScheduleUtils.getDateFormat;

public class IndustrialBuilding extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.buildings");
    private int squareFootage;
    private int numberOfFloors;
    private int constructionDays;


    final static String INVALID_DIMENSIONS_MESSAGE =
            "Invalid dimensions for IndustrialBuilding.";
    final static String INVALID_NUM_FLOORS_MESSAGE =
            "Invalid number of floors for Industrial Building.";

    public IndustrialBuilding(int squareFootage, int numberOfFloors) {
        super();
        if (squareFootage < INDUSTRIAL_MIN_SQUARE_FOOTAGE || squareFootage > INDUSTRIAL_MAX_SQUARE_FOOTAGE) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
        if (numberOfFloors < INDUSTRIAL_MIN_FLOORS || numberOfFloors > INDUSTRIAL_MAX_FLOORS) {
            LOGGER.warn(INVALID_NUM_FLOORS_MESSAGE);
            throw new InvalidFloorNumberException(INVALID_NUM_FLOORS_MESSAGE);
        }
        this.squareFootage = squareFootage;
        this.numberOfFloors = numberOfFloors;
    }

    // same as House, but with IndustrialBuilding specific OrderItems. Values are arbitrary but
    // try to emulate an appearance of scaling.
    @Override
    public Order generateMaterialOrder() {

        Order order = new Order();
        ArrayList<OrderItem> orderItems = new ArrayList<>(); // Use ArrayList instead of array


        // Add items to the list
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STEEL_BEAMS),
                        squareFootage / 1000
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STEEL_COLUMNS),
                        squareFootage / 1000
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE_INDUSTRIAL),
                        squareFootage / 20
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.GLASS_INDUSTRIAL),
                        squareFootage / 50
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INSULATION_MATERIALS),
                        squareFootage * 2
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ROOFING_HOUSE),
                        squareFootage
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CLADDING_MATERIAL),
                        squareFootage / 2
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL),
                        1
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                        1
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.HVAC_SUPPLIES),
                        numberOfFloors
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INTERIOR_FINISHING_MATERIALS),
                        squareFootage
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.FRONT_LOADER_TRUCK),
                        1,
                        1
                )
        );

        for (OrderItem item : orderItems) {
            final int MIN_QUANTITY = 1;
            final int MAX_QUANTITY = 10000;
            if (item.getQuantity() < MIN_QUANTITY || item.getQuantity() > MAX_QUANTITY) {
                // Handle quantity check
            }
            order.addOrderItem(item);
        }

        return order;
    }

    // Same as `House`
    @Override
    public Schedule generateEmployeeSchedule(ZonedDateTime customerEndDate) {
        Schedule schedule = new Schedule();

        int totalConstructionDays = calculateConstructionDays();
        setConstructionDays(totalConstructionDays);


        this.worker = ConstructionWorker.createEmployee(schedule, new BigDecimal("15.0"));
        this.engineer = Engineer.createEmployee(schedule, new BigDecimal("30.0"));
        this.architect = Architect.createEmployee(schedule, new BigDecimal("35.0"));
        this.manager = ProjectManager.createEmployee(schedule, new BigDecimal("40.0"));

        ZonedDateTime requiredStartTime = customerEndDate.minusDays(totalConstructionDays);
        ZonedDateTime architectEndTime = requiredStartTime.plusDays(totalConstructionDays / 5);


        schedule.addActivity(new ScheduledActivity("Architectural Design",
                requiredStartTime, architectEndTime));
        schedule.addActivity(new ScheduledActivity("Construction Work", requiredStartTime, customerEndDate));
        schedule.addActivity(new ScheduledActivity("Engineering Work", requiredStartTime, customerEndDate));
        schedule.addActivity(new ScheduledActivity("Project Management", requiredStartTime, customerEndDate));

        return schedule;
    }

    // Similar to `House`, but values respective of IndustrialBuilding. Values are arbitrary, but
    // again, emulate scaling.
    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {
        int baseConstructionDays = squareFootage / 100;
        int additionalTimePerFloor = (int) Math.ceil(baseConstructionDays * (numberOfFloors - 1));
        int totalConstructionDays = baseConstructionDays + additionalTimePerFloor;

        Schedule schedule = generateEmployeeSchedule(customerEndDate);

        if (schedule == null) {
            return BigDecimal.ZERO;
        }

        DateTimeFormatter dateFormat = getDateFormat();
        String startDateStr =
                customerEndDate.minusDays(totalConstructionDays).toLocalDate().format(dateFormat);
        String endDateStr =
                customerEndDate.toLocalDate().format(dateFormat);

        // consider Architect only in the planning phase
        int architectDays = (int) Math.ceil(constructionDays / 5.0);
        String architectEndDateStr =
                customerEndDate.minusDays(architectDays).toLocalDate().format(dateFormat);

        // loop over Employees and calculate their pay
        Employee[] employees = {worker, engineer, architect, manager};
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Employee employee : employees) {
            String employeeEndDateStr = (employee instanceof Architect) ? architectEndDateStr : endDateStr;
            BigDecimal employeeHours = new BigDecimal(employee.getWorkHours(startDateStr, employeeEndDateStr));
            BigDecimal employeeCost = employee.getPayRate().multiply(employeeHours);

            totalCost = totalCost.add(employeeCost);
        }

        return totalCost;
    }

    // same as `House`
    @Override
    public BigDecimal calculateMaterialCost() {
        Order order = generateMaterialOrder();
        return order.getTotalCost();
    }

    // calculate construction days with respect to IndustrialBuilding, used for generating
    // employee schedule
    public int calculateConstructionDays() {
        // uses arbitrary divisor to calculate construction days
        int baseConstructionDays = squareFootage / 100;

        // if additional levels are added, will scale amount of days up
        int additionalTimePerFloor = (int) Math.ceil(baseConstructionDays * (numberOfFloors - 1));

        return baseConstructionDays + additionalTimePerFloor;
    }

    // getters and setters

    public int getConstructionDays() {
        return constructionDays;
    }

    public void setConstructionDays(int constructionDays) {
        this.constructionDays = constructionDays;
    }


    public int getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        if (squareFootage < INDUSTRIAL_MIN_SQUARE_FOOTAGE || squareFootage > INDUSTRIAL_MAX_SQUARE_FOOTAGE) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
        this.squareFootage = squareFootage;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        if (numberOfFloors < INDUSTRIAL_MIN_FLOORS || numberOfFloors > INDUSTRIAL_MAX_FLOORS) {
            LOGGER.warn(INVALID_NUM_FLOORS_MESSAGE);
            throw new InvalidFloorNumberException(INVALID_NUM_FLOORS_MESSAGE);
        }
        this.numberOfFloors = numberOfFloors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());

        String[] fieldNames = {"squareFootage", "numberOfFloors", "constructionDays"};

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
            if (fieldValue != null) {
                builder
                        .append(", ")
                        .append(fieldName)
                        .append("=")
                        .append(fieldValue);
            }
        }

        builder.append("}");

        int startIndex = builder.indexOf("Building{") + "Building".length();
        builder.replace(startIndex, startIndex + 1, this.getClass().getSimpleName() + "{");

        return builder.toString();
    }

}
