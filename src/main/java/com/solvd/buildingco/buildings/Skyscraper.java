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

public class Skyscraper extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(Skyscraper.class);

    private int squareFootagePerLevel;
    private int numberOfLevels;
    private int constructionDays;
    private BigDecimal lobbyCost; // lobby is a unique floor, has an arbitrary fixed cost
    private BigDecimal foundationCost; // foundation cost depends on amount of levels

    // exception messages
    private final static String INVALID_DIMENSIONS_MESSAGE = "Invalid dimensions for Skyscraper.";
    private final static String INVALID_NUM_LEVELS_MESSAGE = "Invalid number of levels for Skyscraper.";

    public Skyscraper() {
        super();
    }

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
        this.lobbyCost = SKYSCRAPER_LOBBY_FIXED_COST;
        // base foundation cost multiplied by the number of levels
        this.foundationCost = new BigDecimal(numberOfLevels).multiply(SKYSCRAPER_FOUNDATION_COST_FACTOR);
    }

    // create order for materials, contributes to material cost calculation
    @Override
    public Order generateMaterialOrder() {
        return MaterialOrderGenerator.generateMaterialOrder(this);
    }

    @Override
    public BigDecimal calculateMaterialCost() {
        BigDecimal[] additionalCosts = {lobbyCost, foundationCost};

        Order skyScraperMaterialOrder = this.generateMaterialOrder();

        return BuildingCostCalculator.calculateMaterialCost(
                skyScraperMaterialOrder,
                additionalCosts
        );
    }

    @Override
    public BigDecimal calculateLaborCost(ZonedDateTime customerEndDate) {

        int calculatedConstructionDays =
                BuildingCostCalculator.calculateConstructionDays(
                        SKYSCRAPER_BUILDING_TYPE,
                        squareFootagePerLevel, // square footage and number of floors passed in by user
                        numberOfLevels
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

        String[] fieldNames = {"squareFootagePerLevel", "numberOfLevels", "constructionDays", "lobbyCost", "foundationCost"};

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
