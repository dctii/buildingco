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

public class Skyscraper extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(Skyscraper.class);

    private int squareFootagePerLevel;
    private int numberOfLevels;
    private int constructionDays;
    private BigDecimal lobbyCost; // lobby is a unique floor, has an arbitrary fixed cost
    private BigDecimal foundationCost; // foundation cost depends on amount of levels


    final static BigDecimal LOBBY_FIXED_COST = new BigDecimal("200000");
    final static BigDecimal FOUNDATION_COST_FACTOR = new BigDecimal("10000");

    final static String INVALID_DIMENSIONS_MESSAGE =
            "Invalid dimensions for Skyscraper.";
    final static String INVALID_NUM_LEVELS_MESSAGE =
            "Invalid number of levels for Skyscraper.";

    public Skyscraper(int squareFootagePerLevel, int numberOfLevels) {
        super();
        if (squareFootagePerLevel < SKYSCRAPER_MIN_SQUARE_FOOTAGE_PER_LEVEL || squareFootagePerLevel > SKYSCRAPER_MAX_SQUARE_FOOTAGE_PER_LEVEL) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
        if (numberOfLevels < SKYSCRAPER_MIN_LEVELS || numberOfLevels > SKYSCRAPER_MAX_LEVELS) {
            LOGGER.warn(INVALID_NUM_LEVELS_MESSAGE);
            throw new InvalidFloorNumberException(INVALID_NUM_LEVELS_MESSAGE);
        }

        this.squareFootagePerLevel = squareFootagePerLevel;
        this.numberOfLevels = numberOfLevels;
        // base cost for the lobby
        this.lobbyCost = LOBBY_FIXED_COST;
        // base foundation cost multiplied by the number of levels
        this.foundationCost = new BigDecimal(numberOfLevels).multiply(FOUNDATION_COST_FACTOR);
    }

    // like siblings, but higher priced than IndustrialBuilding due to premium needs of Skyscraper
    @Override
    public Order generateMaterialOrder() {
        Order order = new Order();
        ArrayList<OrderItem> orderItems = new ArrayList<>(); // Use ArrayList instead of array

        // Add items to the list

        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STEEL_BEAMS_HIGH_GRADE),
                        squareFootagePerLevel * numberOfLevels / 40
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE_HIGH_GRADE),
                        squareFootagePerLevel * numberOfLevels / 15
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.GLASS_HIGH_GRADE_INDUSTRIAL),
                        squareFootagePerLevel * numberOfLevels / 8
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INSULATION_MATERIALS),
                        squareFootagePerLevel * numberOfLevels
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CLADDING_MATERIAL),
                        squareFootagePerLevel * numberOfLevels / 2
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL),
                        numberOfLevels
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                        numberOfLevels
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.HVAC_SUPPLIES),
                        numberOfLevels
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INTERIOR_FINISHING_MATERIALS),
                        squareFootagePerLevel * numberOfLevels
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.TOWER_CRANE),
                        1,
                        2
                )
        );


        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }

    @Override
    public Schedule generateEmployeeSchedule(ZonedDateTime customerEndDate) {
        Schedule schedule = new Schedule();

        int totalConstructionDays = calculateConstructionDays();
        setConstructionDays(totalConstructionDays);

        worker = ConstructionWorker.createEmployee(schedule, new BigDecimal("20.0"));
        engineer = Engineer.createEmployee(schedule, new BigDecimal("35.0"));
        architect = Architect.createEmployee(schedule, new BigDecimal("40.0"));
        manager = ProjectManager.createEmployee(schedule, new BigDecimal("45.0"));

        ZonedDateTime requiredStartTime = customerEndDate.minusDays(totalConstructionDays);
        ZonedDateTime architectEndTime = requiredStartTime.plusDays(totalConstructionDays / 5);

        schedule.addActivity(new ScheduledActivity("Architectural Design", requiredStartTime, architectEndTime));
        schedule.addActivity(new ScheduledActivity("Construction Work", requiredStartTime, customerEndDate));
        schedule.addActivity(new ScheduledActivity("Engineering Work", requiredStartTime, customerEndDate));
        schedule.addActivity(new ScheduledActivity("Project Management", requiredStartTime, customerEndDate));

        return schedule;
    }

    // similar to IndustrialBuilding for emulating scaling up of time based on size
    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {
        Schedule schedule = generateEmployeeSchedule(customerEndDate);

        if (schedule == null) {
            return BigDecimal.ZERO;
        }

        int baseConstructionDays = squareFootagePerLevel / 50;
        int additionalTimePerLevel = baseConstructionDays * numberOfLevels;
        int totalConstructionDays = baseConstructionDays + additionalTimePerLevel;

        DateTimeFormatter dateFormat = getDateFormat();

        ZonedDateTime requiredStartDate = customerEndDate.minusDays(totalConstructionDays);
        String startDateStr = requiredStartDate.toLocalDate().format(dateFormat);
        String endDateStr = customerEndDate.toLocalDate().format(dateFormat);

        int architectDays = (int) Math.ceil(constructionDays / 5.0);
        String architectEndDateStr =
                customerEndDate.minusDays(architectDays).toLocalDate().format(dateFormat);

        Employee[] employees = {worker, engineer, architect, manager};
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Employee employee : employees) {
            String employeeEndDateStr = (employee instanceof Architect) ? architectEndDateStr : endDateStr;
            BigDecimal employeeHours
                    = new BigDecimal(employee.getWorkHours(startDateStr, employeeEndDateStr));
            BigDecimal employeeCost
                    = employee.getPayRate().multiply(employeeHours);
            totalCost = totalCost.add(employeeCost);
        }
        return totalCost;
    }

    // like siblings, except factors in fixed cost of lobby and calculated foundation cost
    @Override
    public BigDecimal calculateMaterialCost() {
        Order order = generateMaterialOrder();
        return order.getTotalCost().add(lobbyCost).add(foundationCost);
    }

    // like IndustrialBuilding
    public int calculateConstructionDays() {
        int baseConstructionDays = squareFootagePerLevel / 50;
        int additionalTimePerLevel = (int) Math.ceil(baseConstructionDays * numberOfLevels);
        return baseConstructionDays + additionalTimePerLevel;
    }

    // getters and setters
    public int getConstructionDays() {
        return constructionDays;
    }

    public void setConstructionDays(int constructionDays) {
        this.constructionDays = constructionDays;
    }

    public int getSquareFootagePerLevel() {
        return squareFootagePerLevel;
    }

    public void setSquareFootagePerLevel(int squareFootagePerLevel) {
        if (squareFootagePerLevel < SKYSCRAPER_MIN_SQUARE_FOOTAGE_PER_LEVEL || squareFootagePerLevel > SKYSCRAPER_MAX_SQUARE_FOOTAGE_PER_LEVEL) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
        this.squareFootagePerLevel = squareFootagePerLevel;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        if (numberOfLevels < SKYSCRAPER_MIN_LEVELS || numberOfLevels > SKYSCRAPER_MAX_LEVELS) {
            LOGGER.warn(INVALID_NUM_LEVELS_MESSAGE);
            throw new InvalidFloorNumberException(INVALID_NUM_LEVELS_MESSAGE);
        }
        this.numberOfLevels = numberOfLevels;
    }


    public BigDecimal getLobbyCost() {
        return lobbyCost;
    }

    public void setLobbyCost(BigDecimal lobbyCost) {
        this.lobbyCost = lobbyCost;
    }

    public BigDecimal getFoundationCost() {
        return foundationCost;
    }

    public void setFoundationCost(BigDecimal foundationCost) {
        this.foundationCost = foundationCost;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());

        String[] fieldNames = {
                "squareFootagePerLevel", "numberOfLevels", "constructionDays",
                "lobbyCost", "foundationCost"
        };

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
