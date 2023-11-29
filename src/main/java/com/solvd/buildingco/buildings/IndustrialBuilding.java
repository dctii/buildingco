package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidDimensionException;
import com.solvd.buildingco.exception.InvalidFloorNumberException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.FieldUtils;
import com.solvd.buildingco.utilities.MaterialOrderGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.buildings.BuildingConstants.*;

public class IndustrialBuilding extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(IndustrialBuilding.class);
    private int squareFootage; // square footage user chooses building to be
    private int numberOfFloors; // number of floors user chooses building to be
    private int constructionDays; // number of business days to construct building

    // exception messages
    private final static String INVALID_DIMENSIONS_MESSAGE = "Invalid dimensions for IndustrialBuilding.";
    private final static String INVALID_NUM_FLOORS_MESSAGE = "Invalid number of floors for Industrial Building.";

    public IndustrialBuilding() {
        super();
    }

    public IndustrialBuilding(int squareFootage) {
        super();
        if (squareFootage < INDUSTRIAL_MIN_SQUARE_FOOTAGE || squareFootage > INDUSTRIAL_MAX_SQUARE_FOOTAGE) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }

        this.squareFootage = squareFootage;
        this.numberOfFloors = INDUSTRIAL_MIN_FLOORS;
    }

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

    // create order for materials, contributes to material cost calculation
    @Override
    public Order generateMaterialOrder() {
        return MaterialOrderGenerator.generateMaterialOrder(this);
    }

    @Override
    public BigDecimal calculateMaterialCost() {
        Order industrialBuildingOrder = this.generateMaterialOrder();

        return BuildingCostCalculator.calculateMaterialCost(
                industrialBuildingOrder
        );
    }

    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {
        int calculatedConstructionDays =
                BuildingCostCalculator.calculateConstructionDays(
                        INDUSTRIAL_BUILDING_TYPE,
                        squareFootage, // square footage and number of floors passed in by user
                        numberOfFloors
                );

        return BuildingCostCalculator.calculateLaborCost(
                customerEndDate,
                calculatedConstructionDays
        );
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
                builder.append(", ").append(fieldName).append("=").append(fieldValue);
            }
        }

        builder.append("}");

        int startIndex = builder.indexOf("Building{") + "Building".length();
        builder.replace(startIndex, startIndex + 1, this.getClass().getSimpleName() + "{");

        return builder.toString();
    }

}
