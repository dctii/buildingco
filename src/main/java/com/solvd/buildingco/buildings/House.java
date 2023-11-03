package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.Item;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.Schedule.ScheduledActivity;
import com.solvd.buildingco.stakeholders.employees.*;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.solvd.buildingco.scheduling.ScheduleUtils.getDateFormat;

public class House extends Building {

    private int squareFootage;
    private int numRooms;
    private int numBathrooms;
    private int garageCapacity; // amount of cars that can fit in garage
    private int constructionDays; // how many business days to build

    // constructor
    public House(int squareFootage, int numRooms, int numBathrooms, int constructionDays, int garageCapacity) {
        super();
        this.squareFootage = squareFootage;
        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.constructionDays = constructionDays;
        this.garageCapacity = garageCapacity;
    }

    // create order for materials, contributes to material cost calculation
    @Override
    public Order generateMaterialOrder() {
        Order order = new Order();

        // used to try and calculate a dynamic value since assumed concrete and not "Flooring"
        // item to be used in garage
        int ADDITIONAL_SQUARE_FOOTAGE_PER_CAR = 100;
        int garageSquareFootage = ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity;

        // List of items on order and their quantities
        // OrderItem (Item, quantity); calculation of qty is arbitrary
        OrderItem[] orderItems = {
                new OrderItem(
                        new Item("Concrete", new BigDecimal("15.0"), "square foot"),
                        squareFootage),
                new OrderItem(
                        new Item("Structural Wood", new BigDecimal("5.0"), "square foot"),
                        numRooms * 2),
                new OrderItem(
                        new Item("Roofing Material", new BigDecimal("10.0"), "square foot"),
                        squareFootage),
                new OrderItem(
                        new Item("Drywall", new BigDecimal("2.0"), "square foot"),
                        (numRooms + (garageCapacity / 2)) * 4),
                new OrderItem(
                        new Item("Insulation", new BigDecimal("3.0"), "square foot"),
                        (numRooms + 1) * 2), // includes rooms and living spaces
                new OrderItem(
                        new Item("Flooring", new BigDecimal("20.0"), "square foot"),
                        squareFootage - garageSquareFootage),
                new OrderItem(
                        new Item("Paint", new BigDecimal("25.0"), "gallon"),
                        numRooms + 2), // includes garage, living room, etc.
                new OrderItem(
                        new Item("Plumbing Materials", new BigDecimal("500.0"), "unit"),
                        numBathrooms + 1), // includes kitchen
                new OrderItem(
                        new Item("Electrical Supplies", new BigDecimal("300.0"), "unit"),
                        numRooms + 1) // includes garage
        };

        // loop to populate Order instance
        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }


    // create an employee schedule, provides qty of hours with rates to calculate labor costs
    @Override
    public Schedule generateEmployeeSchedule(ZonedDateTime customerEndDate) {

        Schedule schedule = new Schedule();

        this.worker = ConstructionWorker.createConstructionWorker(schedule, new BigDecimal("15.0"));
        this.engineer = Engineer.createEngineer(schedule, new BigDecimal("30.0"));
        this.architect = Architect.createArchitect(schedule, new BigDecimal("35.0"));
        this.manager = ProjectManager.createProjectManager(schedule, new BigDecimal("40.0"));

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
    public static House createHouse(int numRooms, int numBathrooms, int garageCapacity) {
        // constants to help with scaling
        final int BASE_SQUARE_FOOTAGE = 550; // assumed one room home sq footage
        final int BASE_CONSTRUCTION_DAYS = 30;
        final int ADDITIONAL_SQUARE_FOOTAGE_PER_ROOM = 200;
        final int ADDITIONAL_SQUARE_FOOTAGE_PER_CAR = 100;
        final int ADDITIONAL_CONSTRUCTION_DAYS_PER_CAR = 3;

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
        this.numRooms = numRooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }


    public int getGarageCapacity() {
        return garageCapacity;
    }

    public void setGarageCapacity(int garageCapacity) {
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
