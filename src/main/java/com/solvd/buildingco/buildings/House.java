package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidNumRoomsException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.ItemNames;
import com.solvd.buildingco.inventory.ItemRepository;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.FieldUtils;
import com.solvd.buildingco.utilities.OrderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static com.solvd.buildingco.buildings.BuildingConstants.*;

public class House extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(House.class);
    //
    private int squareFootage;
    private int numRooms; // number of rooms user chooses in house
    private int numBathrooms; // number of bathrooms user chooses in house
    private int garageCapacity; // number of user wants to fit in garage
    private int constructionDays; // how many business days to build

    // exception messages
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
        // general calculations for rooms
        int averageRoomPerimeter = 2 * (HOUSE_AVERAGE_ROOM_LENGTH + HOUSE_AVERAGE_ROOM_WIDTH);
        double wallAreaPerRoom = averageRoomPerimeter * HOUSE_ROOM_HEIGHT;
        double ceilingAreaPerRoom = HOUSE_AVERAGE_ROOM_LENGTH * HOUSE_AVERAGE_ROOM_WIDTH;

        // general calculations for material quantities for rooms
        double woodFramingQuantityPerRoom = wallAreaPerRoom * HOUSE_WOOD_USAGE_FACTOR_PER_FOOT;
        double drywallQuantityPerRoom = wallAreaPerRoom + ceilingAreaPerRoom;
        double paintQuantityPerRoom =
                (wallAreaPerRoom + ceilingAreaPerRoom) / HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON;
        double insulationQuantityPerRoom = wallAreaPerRoom * HOUSE_INSULATION_THICKNESS_IN_FEET;

        // general calculations for garage
        double garageSquareFootage, garageCeilingArea;
        garageSquareFootage = garageCeilingArea =
                HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity;
        int garagePerimeter = 2 * ((int) Math.sqrt(garageSquareFootage) + HOUSE_AVERAGE_ROOM_WIDTH);
        double garageWallArea = garagePerimeter * HOUSE_ROOM_HEIGHT;

        // general calculations for material quantities for garage
        double woodFramingQuantityForGarage = garageWallArea * HOUSE_WOOD_USAGE_FACTOR_PER_FOOT;
        double drywallQuantityForGarage = garageWallArea + garageCeilingArea;
        double paintQuantityForGarage =
                (garageWallArea + garageCeilingArea) / HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON;
        double insulationQuantityForGarage = garageWallArea + HOUSE_INSULATION_THICKNESS_IN_FEET;

        // final quantities for each item
        int concreteQuantity, roofingQuantity;
        concreteQuantity = roofingQuantity = squareFootage;
        int structuralWoodQuantity =
                ((int) woodFramingQuantityPerRoom * numRooms) + (int) woodFramingQuantityForGarage;
        int drywallQuantity =
                ((int) drywallQuantityPerRoom * numRooms) + (int) drywallQuantityForGarage;
        int insulationQuantity =
                ((int) insulationQuantityPerRoom * numRooms) + (int) insulationQuantityForGarage;
        int flooringQuantity = squareFootage - (int) garageSquareFootage;
        int paintQuantity = ((int) paintQuantityPerRoom * numRooms) + (int) paintQuantityForGarage;
        int plumbingSuppliesQuantity = numBathrooms + HOUSE_KITCHEN_QUANTITY;
        int electricalSuppliesQuantity = numRooms + HOUSE_GARAGE_QUANTITY;


        // initialize the ArrayList of OrderItems
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        // add items to the list
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE),
                        concreteQuantity
                )
        );

        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ROOFING_HOUSE),
                        roofingQuantity
                )
        );

        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STRUCTURAL_WOOD),
                        structuralWoodQuantity
                )
        );

        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.DRYWALL),
                        drywallQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INSULATION_MATERIALS),
                        insulationQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.FLOORING),
                        flooringQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.PAINT),
                        paintQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                        plumbingSuppliesQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ELECTRICAL_SUPPLIES_HOUSE),
                        electricalSuppliesQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE_MIXER),
                        1, // one concrete mixer vehicle
                        1
                )
        );

        // populate order with items
        Order loadedOrder = OrderUtils.loadOrder(orderItems);

        return loadedOrder;
    }

    @Override
    public BigDecimal calculateMaterialCost() {
        Order houseOrder = this.generateMaterialOrder();

        return BuildingCostCalculator.calculateMaterialCost(
                houseOrder
        );
    }

    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {
        return BuildingCostCalculator.calculateLaborCost(
                customerEndDate,
                constructionDays
        );
    }


    public static House createHouse(int numRooms, int numBathrooms, int garageCapacity) {
        // calculate square footage for house
        int garageSquareFootage = (HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity);
        int extraRoomsSquareFootage = // get the square footage for each additional room
                (HOUSE_AVERAGE_ROOM_LENGTH * HOUSE_AVERAGE_ROOM_WIDTH) * (numRooms - 1);
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
