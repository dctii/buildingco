package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.BuildingUtils;
import com.solvd.buildingco.utilities.MaterialOrderGenerator;
import com.solvd.buildingco.utilities.StringFormatters;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.buildings.BuildingConstants.SKYSCRAPER_FOUNDATION_COST_FACTOR;
import static com.solvd.buildingco.buildings.BuildingConstants.SKYSCRAPER_LOBBY_FIXED_COST;
import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.SKYSCRAPER;

public class Skyscraper extends Building<BigDecimal> implements IEstimate {
    private int squareFootagePerLevel;
    private int numberOfLevels;
    private BigDecimal lobbyCost; // lobby is a unique floor, has an arbitrary fixed cost
    private BigDecimal foundationCost; // foundation cost depends on amount of levels


    public Skyscraper() {
        super();
    }

    public Skyscraper(int squareFootagePerLevel, int numberOfLevels) {
        super();

        BuildingUtils.validateSquareFootagePerLevel(squareFootagePerLevel);
        BuildingUtils.validateNumberOfLevels(numberOfLevels);

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

        BuildingUtils.validateSquareFootagePerLevel(squareFootagePerLevel);
        BuildingUtils.validateNumberOfLevels(numberOfLevels);

        this.squareFootagePerLevel = squareFootagePerLevel;
        this.numberOfLevels = numberOfLevels;
        this.lobbyCost = lobbyCost;
        this.foundationCost = foundationCost;
    }

    public Skyscraper(int squareFootagePerLevel, int numberOfLevels, BigDecimal lobbyCost,
                      BigDecimal foundationCost, int constructionDays) {
        super(constructionDays);

        BuildingUtils.validateSquareFootagePerLevel(squareFootagePerLevel);
        BuildingUtils.validateNumberOfLevels(numberOfLevels);

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

        setConstructionDays(calculatedConstructionDays);

        return BuildingCostCalculator.calculateLaborCost(
                this,
                customerEndDate
        );
    }


    // getters and setters

    public int getSquareFootagePerLevel() {
        return squareFootagePerLevel;
    }

    public void setSquareFootagePerLevel(int squareFootagePerLevel) {
        BuildingUtils.validateSquareFootagePerLevel(squareFootagePerLevel);

        this.squareFootagePerLevel = squareFootagePerLevel;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        BuildingUtils.validateNumberOfLevels(numberOfLevels);

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
        Class<?> currClass = Skyscraper.class;
        String[] fieldNames = {
                "squareFootagePerLevel",
                "numberOfLevels",
                "lobbyCost",
                "foundationCost"
        };

        String parentToString = super.toString();
        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, parentToString,
                fieldsString);
    }

}
