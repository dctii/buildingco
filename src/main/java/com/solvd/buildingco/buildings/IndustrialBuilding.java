package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidDimensionException;
import com.solvd.buildingco.exception.InvalidFloorNumberException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.BuyableItem;
import com.solvd.buildingco.inventory.RentableItem;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.stakeholders.employees.*;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.solvd.buildingco.scheduling.ScheduleUtils.getDateFormat;

public class IndustrialBuilding extends Building implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.buildings");
    private int squareFootage;
    private int numberOfFloors;
    private int constructionDays;

    final static int MIN_SQUARE_FOOTAGE = 5000;
    final static int MAX_SQUARE_FOOTAGE = 75000;
    final static int MIN_FLOORS = 1;
    final static int MAX_FLOORS = 4;

    final static String INVALID_DIMENSIONS_MESSAGE =
            "Invalid dimensions for IndustrialBuilding.";
    final static String INVALID_NUM_FLOORS_MESSAGE =
            "Invalid number of floors for Industrial Building.";

    public IndustrialBuilding(int squareFootage, int numberOfFloors) {
        super();
        if (squareFootage < MIN_SQUARE_FOOTAGE || squareFootage > MAX_SQUARE_FOOTAGE) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
        if (numberOfFloors < MIN_FLOORS || numberOfFloors > MAX_FLOORS) {
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

        OrderItem[] orderItems = {
                new OrderItem(new BuyableItem("Steel Beams", new BigDecimal("15.0"), "ton"),
                        squareFootage / 1000),
                new OrderItem(new BuyableItem("Steel Columns", new BigDecimal("20.0"), "ton"),
                        squareFootage / 1000),
                new OrderItem(new BuyableItem("Concrete", new BigDecimal("70.0"), "cubic meter"),
                        squareFootage / 20),
                new OrderItem(new BuyableItem("Industrial Glass", new BigDecimal("200.0"), "square meter"),
                        squareFootage / 50),
                new OrderItem(new BuyableItem("Insulation Material", new BigDecimal("5.0"), "square meter"),
                        squareFootage * 2),
                new OrderItem(new BuyableItem("Roofing", new BigDecimal("10.0"), "square meter"),
                        squareFootage),
                new OrderItem(new BuyableItem("Cladding Material", new BigDecimal("25.0"), "square meter"),
                        squareFootage / 2),
                new OrderItem(new BuyableItem("Electrical Supplies", new BigDecimal("5000.0"), "unit"),
                        1),
                new OrderItem(new BuyableItem("Plumbing Supplies", new BigDecimal("3000.0"), "unit"),
                        1),
                new OrderItem(new BuyableItem("HVAC Supplies", new BigDecimal("10000.0"), "unit"),
                        numberOfFloors),
                new OrderItem(new BuyableItem("Interior Finishing Materials", new BigDecimal("50.0"), "square meter"),
                        squareFootage),
                new OrderItem(
                        new RentableItem("Front Loader Truck", new BigDecimal("3800.0")),
                        1, 1) // rent a front loader truck for 2 months
        };

        for (OrderItem item : orderItems) {
            final int MIN_QUANTITY = 1;
            final int MAX_QUANTITY = 10000;
            if (item.getQuantity() < MIN_QUANTITY || item.getQuantity() > MAX_QUANTITY) {

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


        schedule.addActivity(new Schedule.ScheduledActivity("Architectural Design",
                requiredStartTime, architectEndTime));
        schedule.addActivity(new Schedule.ScheduledActivity("Construction Work", requiredStartTime, customerEndDate));
        schedule.addActivity(new Schedule.ScheduledActivity("Engineering Work", requiredStartTime, customerEndDate));
        schedule.addActivity(new Schedule.ScheduledActivity("Project Management", requiredStartTime, customerEndDate));

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
        if (squareFootage < MIN_SQUARE_FOOTAGE || squareFootage > MAX_SQUARE_FOOTAGE) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
        this.squareFootage = squareFootage;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        if (numberOfFloors < MIN_FLOORS || numberOfFloors > MAX_FLOORS) {
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
