package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidNumRoomsException;
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
import java.util.List;

import static com.solvd.buildingco.buildings.BuildingConstants.*;
import static com.solvd.buildingco.scheduling.ScheduleUtils.getDateFormat;

public class House extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.buildings");

    private int squareFootage;
    private int numRooms;
    private int numBathrooms;
    private int garageCapacity; // amount of cars that can fit in garage
    private int constructionDays; // how many business days to build


    private final static String INVALID_NUM_ROOMS_MESSAGE = "Invalid number of rooms";
    private final static String INVALID_NUM_BATHROOMS_MESSAGE = "Invalid number of bathrooms";
    private final static String INVALID_NUM_GARAGE_CAP_MESSAGE = "Invalid number for garage " +
            "capacity";

    // constructor
    public House(int squareFootage, int numRooms, int numBathrooms, int constructionDays, int garageCapacity) {
        super();

        if (numRooms > HOUSE_MAX_NUM_ROOMS || numRooms < HOUSE_MIN_NUM_ROOMS) {
            LOGGER.warn(INVALID_NUM_ROOMS_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_ROOMS_MESSAGE);
        }

        if (numBathrooms < HOUSE_MIN_NUM_BATHROOMS || numBathrooms > HOUSE_MAX_NUM_BATHROOMS || numBathrooms > numRooms) {
            LOGGER.warn(INVALID_NUM_BATHROOMS_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_BATHROOMS_MESSAGE);
        }

        if (garageCapacity < HOUSE_MIN_NUM_GARAGE_CAP || garageCapacity > HOUSE_MAX_NUM_GARAGE_CAP || garageCapacity > numRooms) {
            LOGGER.warn(INVALID_NUM_GARAGE_CAP_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_GARAGE_CAP_MESSAGE);
        }

        this.squareFootage = squareFootage;
        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.garageCapacity = garageCapacity;
        this.constructionDays = constructionDays;
    }

    // create order for materials, contributes to material cost calculation
    @Override
    public Order generateMaterialOrder() {
        Order order = new Order();

        int ADDITIONAL_SQUARE_FOOTAGE_PER_CAR = 100;
        int garageSquareFootage = ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity;

        // Initialize the ArrayList
        List<OrderItem> orderItems = new ArrayList<>();


        // Add items to the list
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE),
                        squareFootage
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STRUCTURAL_WOOD),
                        numRooms * 2
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
                        ItemRepository.getItem(ItemNames.DRYWALL),
                        (numRooms + (garageCapacity / 2)) * 4
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INSULATION_MATERIALS),
                        (numRooms + 1) * 2
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.FLOORING),
                        squareFootage - garageSquareFootage
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.PAINT),
                        numRooms + 2
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                        numBathrooms + 1
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ELECTRICAL_SUPPLIES_HOUSE),
                        numRooms + 1
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE_MIXER),
                        1,
                        1
                )
        );


        // Loop to populate Order instance
        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }


    // create an employee schedule, provides qty of hours with rates to calculate labor costs
    @Override
    public Schedule generateEmployeeSchedule(ZonedDateTime customerEndDate) {

        Schedule schedule = new Schedule();


        this.worker = ConstructionWorker.createEmployee(schedule, new BigDecimal("15.0"));
        this.engineer = Engineer.createEmployee(schedule, new BigDecimal("30.0"));
        this.architect = Architect.createEmployee(schedule, new BigDecimal("35.0"));
        this.manager = ProjectManager.createEmployee(schedule, new BigDecimal("40.0"));

        ZonedDateTime requiredStartTime = customerEndDate.minusDays(constructionDays);
        ZonedDateTime architectEndTime = requiredStartTime.plusDays(constructionDays / 5);

        schedule.addActivity(new ScheduledActivity("Construction Work", requiredStartTime, customerEndDate))
                .addActivity(new ScheduledActivity("Engineering Work", requiredStartTime, customerEndDate))
                .addActivity(new ScheduledActivity("Architectural Design", requiredStartTime, architectEndTime))
                .addActivity(new ScheduledActivity("Project Management", requiredStartTime, customerEndDate));

        return schedule;
    }


    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {

        // generate schedule, gets loaded into employee instances
        Schedule schedule = generateEmployeeSchedule(customerEndDate);

        if (schedule == null) {
            return BigDecimal.ZERO;
        }

        // set date range
        DateTimeFormatter dateFormat = getDateFormat();
        String startDateStr =
                customerEndDate.minusDays(constructionDays).toLocalDate().format(dateFormat);
        String endDateStr =
                customerEndDate.toLocalDate().format(dateFormat);

        // assume Architect is only in the initial planning phase, 20% of the project time
        int architectDays = (int) Math.ceil(constructionDays / 5.0);
        String architectEndDateStr =
                customerEndDate.minusDays(architectDays).toLocalDate().format(dateFormat);

        // create array of employees to loop over
        Employee[] employees = {worker, engineer, architect, manager};
        BigDecimal totalCost = BigDecimal.ZERO;

        /*
            Used to accumulate an amount of hours per Employee's pay rate multiplied by the
            amount of hours for the construction project. Uses ternary operator to input the
            planning time of the Architect if employee is said type.
        */
        for (Employee employee : employees) {
            String employeeEndDateStr = (employee instanceof Architect) ? architectEndDateStr : endDateStr;
            BigDecimal employeeHours =
                    new BigDecimal(employee.getWorkHours(startDateStr, employeeEndDateStr));
            BigDecimal employeeCost =
                    employee.getPayRate().multiply(employeeHours);

            // update total labor cost
            totalCost = totalCost.add(employeeCost);
        }

        return totalCost;
    }

    // calculates the material cost with Order instance
    @Override
    public BigDecimal calculateMaterialCost() {
        // generate the order
        Order order = generateMaterialOrder();
        return order.getTotalCost();
    }

    // factory method with constants used in BuildingPrompt; values are arbitrary
    final static int BASE_SQUARE_FOOTAGE = 550; // assumed one room home sq footage
    final static int BASE_CONSTRUCTION_DAYS = 30;
    final static int ADDITIONAL_SQUARE_FOOTAGE_PER_ROOM = 200;
    final static int ADDITIONAL_SQUARE_FOOTAGE_PER_CAR = 100;
    final static int ADDITIONAL_CONSTRUCTION_DAYS_PER_CAR = 3;

    public static House createHouse(int numRooms, int numBathrooms, int garageCapacity) {
        // set scaled amount of square footage for the building
        int extraRoomsSquareFootage = ADDITIONAL_SQUARE_FOOTAGE_PER_ROOM * (numRooms - 1);
        int garageSquareFootage = (ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity);
        int squareFootage = BASE_SQUARE_FOOTAGE + extraRoomsSquareFootage + garageSquareFootage;

        // set scaled amount of days to complete construction
        int extraDaysForGarage = ADDITIONAL_CONSTRUCTION_DAYS_PER_CAR * garageCapacity;
        int extraDaysForMoreRooms = 20 * (numRooms - 1);
        int constructionDays = BASE_CONSTRUCTION_DAYS + extraDaysForMoreRooms + extraDaysForGarage;

        return new House(squareFootage, numRooms, numBathrooms, constructionDays, garageCapacity);
    }

    // getters and setters

    public int getConstructionDays() {
        return constructionDays;
    }

    public int setConstructionDays(int constructionDays) {
        return this.constructionDays = constructionDays;
    }


    public int getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        if (numRooms > HOUSE_MAX_NUM_ROOMS || numRooms < HOUSE_MIN_NUM_ROOMS) {
            LOGGER.warn(INVALID_NUM_ROOMS_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_ROOMS_MESSAGE);
        }

        this.numRooms = numRooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        if (numBathrooms < HOUSE_MIN_NUM_BATHROOMS || numBathrooms > HOUSE_MAX_NUM_BATHROOMS || numBathrooms >= this.numRooms) {
            LOGGER.warn(INVALID_NUM_BATHROOMS_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_BATHROOMS_MESSAGE);
        }

        this.numBathrooms = numBathrooms;
    }


    public int getGarageCapacity() {
        return garageCapacity;
    }

    public void setGarageCapacity(int garageCapacity) {
        if (garageCapacity < HOUSE_MIN_NUM_GARAGE_CAP || garageCapacity > HOUSE_MAX_NUM_GARAGE_CAP || garageCapacity > this.numRooms) {
            LOGGER.warn(INVALID_NUM_GARAGE_CAP_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_GARAGE_CAP_MESSAGE);
        }

        this.garageCapacity = garageCapacity;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(super.toString()); // Start with the Building's toString information

        // Append House-specific field information
        String[] fieldNames = {"squareFootage", "numRooms", "numBathrooms", "garageCapacity", "constructionDays"};

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
        builder.replace(startIndex, startIndex + 1, className + "{");

        return builder.toString();
    }
}
