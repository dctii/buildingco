package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.BuildingUtils;
import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.MaterialOrderGenerator;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.buildings.ResidentialBuildingSpecifications.HOUSE;

public class House extends Building<BigDecimal> implements IEstimate {
    private int numRooms; // number of rooms user chooses in house
    private int numBathrooms; // number of bathrooms user chooses in house
    private int garageCapacity; // number of user wants to fit in garage
    private int constructionDays; // how many business days to build
    private int squareFootage;


    // constructor

    public House() {
        super();
    }

    public House(int numRooms, int numBathrooms, int garageCapacity) {
        super();

        BuildingUtils.validateNumberOfRooms(numRooms);
        BuildingUtils.validateNumberOfBathrooms(numBathrooms, numRooms);
        BuildingUtils.validateGarageCapacity(garageCapacity, numRooms);

        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.garageCapacity = garageCapacity;
    }

    public House(int numRooms, int numBathrooms, int garageCapacity, int squareFootage,
                 int constructionDays) {
        super();

        BuildingUtils.validateNumberOfRooms(numRooms);
        BuildingUtils.validateNumberOfBathrooms(numBathrooms, numRooms);
        BuildingUtils.validateGarageCapacity(garageCapacity, numRooms);

        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.garageCapacity = garageCapacity;
        this.squareFootage = squareFootage;
        this.constructionDays = constructionDays;
    }


    // create order for materials, contributes to material cost calculation
    @Override
    public Order generateMaterialOrder() {
        return MaterialOrderGenerator.generateMaterialOrder(this);
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
        int garageSquareFootage = (HOUSE.getExtraSquareFootagePerCar() * garageCapacity);
        int extraRoomsSquareFootage = // get the square footage for each additional room
                (HOUSE.getAverageRoomLength() * HOUSE.getAverageRoomWidth()) * (numRooms - 1);
        int squareFootage = HOUSE.getBaseSquareFootage() + extraRoomsSquareFootage + garageSquareFootage;

        // set scaled amount of days to complete construction
        int extraDaysForGarage = HOUSE.getExtraConstructionDaysPerCar() * garageCapacity;
        int extraDaysForMoreRooms = 20 * (numRooms - 1);
        int constructionDays = HOUSE.getBaseConstructionDays() + extraDaysForMoreRooms + extraDaysForGarage;

        return new House(
                numRooms,
                numBathrooms,
                garageCapacity,
                squareFootage,
                constructionDays);
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

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        BuildingUtils.validateNumberOfRooms(numRooms);

        this.numRooms = numRooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        BuildingUtils.validateNumberOfBathrooms(numBathrooms, this.numRooms);

        this.numBathrooms = numBathrooms;
    }


    public int getGarageCapacity() {
        return garageCapacity;
    }

    public void setGarageCapacity(int garageCapacity) {
        BuildingUtils.validateGarageCapacity(garageCapacity, this.numRooms);

        this.garageCapacity = garageCapacity;
    }


    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(super.toString()); // Start with the Building's toString information

        // Append House-specific field information
        String[] fieldNames = {"squareFootage", "numRooms", "numBathrooms", "garageCapacity", "constructionDays"};

        for (String fieldName : fieldNames) {
            Object fieldValue = ReflectionUtils.getField(this, fieldName);
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
