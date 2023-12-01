package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.FieldUtils;
import com.solvd.buildingco.utilities.MaterialOrderGenerator;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.buildings.BuildingConstants.SKYSCRAPER_FOUNDATION_COST_FACTOR;
import static com.solvd.buildingco.buildings.BuildingConstants.SKYSCRAPER_LOBBY_FIXED_COST;
import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.SKYSCRAPER;
import static com.solvd.buildingco.utilities.BuildingUtils.validateNumberOfLevels;
import static com.solvd.buildingco.utilities.BuildingUtils.validateSquareFootagePerLevel;

public class Skyscraper extends Building<BigDecimal> implements IEstimate {
    private int squareFootagePerLevel;
    private int numberOfLevels;
    private int constructionDays;
    private BigDecimal lobbyCost; // lobby is a unique floor, has an arbitrary fixed cost
    private BigDecimal foundationCost; // foundation cost depends on amount of levels


    public Skyscraper() {
        super();
    }

    public Skyscraper(int squareFootagePerLevel, int numberOfLevels) {
        super();

        validateSquareFootagePerLevel(squareFootagePerLevel);
        validateNumberOfLevels(numberOfLevels);

        this.squareFootagePerLevel = squareFootagePerLevel;
        this.numberOfLevels = numberOfLevels;
        // base cost for the lobby
        this.lobbyCost = SKYSCRAPER_LOBBY_FIXED_COST;
        // base foundation cost multiplied by the number of levels
        this.foundationCost = new BigDecimal(numberOfLevels).multiply(SKYSCRAPER_FOUNDATION_COST_FACTOR);
    }

    public Skyscraper(int squareFootagePerLevel, int numberOfLevels, BigDecimal lobbyCost,
                      BigDecimal foundationCost) {
        super();

        validateSquareFootagePerLevel(squareFootagePerLevel);
        validateNumberOfLevels(numberOfLevels);

        this.squareFootagePerLevel = squareFootagePerLevel;
        this.numberOfLevels = numberOfLevels;
        this.lobbyCost = lobbyCost;
        this.foundationCost = foundationCost;
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
                        SKYSCRAPER.getBuildingType(),
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
        validateSquareFootagePerLevel(squareFootagePerLevel);

        this.squareFootagePerLevel = squareFootagePerLevel;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        validateNumberOfLevels(numberOfLevels);

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
