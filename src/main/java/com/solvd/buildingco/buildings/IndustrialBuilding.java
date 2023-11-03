package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.Item;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.stakeholders.employees.*;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.solvd.buildingco.scheduling.ScheduleUtils.getDateFormat;

public class IndustrialBuilding extends Building {
    private int squareFootage;
    private int numberOfFloors;
    private int constructionDays;

    public IndustrialBuilding(int squareFootage, int numberOfFloors) {
        super();
        this.squareFootage = squareFootage;
        this.numberOfFloors = numberOfFloors;
    }

    // same as House, but with IndustrialBuilding specific OrderItems. Values are arbitrary but
    // try to emulate an appearance of scaling.
    @Override
    public Order generateMaterialOrder() {

        Order order = new Order();

        OrderItem[] orderItems = {
                new OrderItem(new Item("Steel Beams", new BigDecimal("15.0"), "ton"),
                        squareFootage / 1000),
                new OrderItem(new Item("Steel Columns", new BigDecimal("20.0"), "ton"),
                        squareFootage / 1000),
                new OrderItem(new Item("Concrete", new BigDecimal("70.0"), "cubic meter"),
                        squareFootage / 20),
                new OrderItem(new Item("Industrial Glass", new BigDecimal("200.0"), "square meter"),
                        squareFootage / 50),
                new OrderItem(new Item("Insulation Material", new BigDecimal("5.0"), "square meter"),
                        squareFootage * 2),
                new OrderItem(new Item("Roofing", new BigDecimal("10.0"), "square meter"),
                        squareFootage),
                new OrderItem(new Item("Cladding Material", new BigDecimal("25.0"), "square meter"),
                        squareFootage / 2),
                new OrderItem(new Item("Electrical Supplies", new BigDecimal("5000.0"), "unit"),
                        1),
                new OrderItem(new Item("Plumbing Supplies", new BigDecimal("3000.0"), "unit"),
                        1),
                new OrderItem(new Item("HVAC Supplies", new BigDecimal("10000.0"), "unit"),
                        numberOfFloors),
                new OrderItem(new Item("Interior Finishing Materials", new BigDecimal("50.0"), "square meter"),
                        squareFootage)
        };

        for (OrderItem item : orderItems) {
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


        this.worker = ConstructionWorker.createConstructionWorker(schedule, new BigDecimal("15.0"));
        this.engineer = Engineer.createEngineer(schedule, new BigDecimal("30.0"));
        this.architect = Architect.createArchitect(schedule, new BigDecimal("35.0"));
        this.manager = ProjectManager.createProjectManager(schedule, new BigDecimal("40.0"));

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
        this.squareFootage = squareFootage;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
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
