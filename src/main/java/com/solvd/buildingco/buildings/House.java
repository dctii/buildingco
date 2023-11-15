package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidNumRoomsException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.ItemNames;
import com.solvd.buildingco.inventory.ItemRepository;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.solvd.buildingco.buildings.BuildingConstants.*;

public class House extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(House.class);

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

    @Override
    public BigDecimal calculateMaterialCost() {
        Order houseOrder = this.generateMaterialOrder();
        return BuildingCostCalculator.calculateMaterialCost(houseOrder);
    }

    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {
        return BuildingCostCalculator.calculateLaborCost(customerEndDate, constructionDays);
    }

    // calculates the material cost with Order instance


    public static House createHouse(int numRooms, int numBathrooms, int garageCapacity) {
        // set scaled amount of square footage for the building
        int extraRoomsSquareFootage = HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_ROOM * (numRooms - 1);
        int garageSquareFootage = (HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity);
        int squareFootage = HOUSE_BASE_SQUARE_FOOTAGE + extraRoomsSquareFootage + garageSquareFootage;

        // set scaled amount of days to complete construction
        int extraDaysForGarage = HOUSE_ADDITIONAL_CONSTRUCTION_DAYS_PER_CAR * garageCapacity;
        int extraDaysForMoreRooms = 20 * (numRooms - 1);
        int constructionDays = HOUSE_BASE_CONSTRUCTION_DAYS + extraDaysForMoreRooms + extraDaysForGarage;

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
