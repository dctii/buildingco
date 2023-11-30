package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.FieldUtils;
import com.solvd.buildingco.utilities.MaterialOrderGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.buildings.BuildingConstants.INDUSTRIAL_BUILDING_TYPE;
import static com.solvd.buildingco.buildings.BuildingConstants.INDUSTRIAL_MIN_FLOORS;
import static com.solvd.buildingco.utilities.BuildingUtils.validateNumberOfFloors;
import static com.solvd.buildingco.utilities.BuildingUtils.validateSquareFootage;

public class IndustrialBuilding extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(IndustrialBuilding.class);
    private int squareFootage; // square footage user chooses building to be
    private int numberOfFloors; // number of floors user chooses building to be
    private int constructionDays; // number of business days to construct building

    public IndustrialBuilding() {
        super();
    }

    public IndustrialBuilding(int squareFootage) {
        super();

        validateSquareFootage(squareFootage);

        this.squareFootage = squareFootage;
        this.numberOfFloors = INDUSTRIAL_MIN_FLOORS;
    }

    public IndustrialBuilding(int squareFootage, int numberOfFloors) {
        super();

        validateSquareFootage(squareFootage);
        validateNumberOfFloors(numberOfFloors);

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
        validateSquareFootage(squareFootage);

        this.squareFootage = squareFootage;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        validateNumberOfFloors(numberOfFloors);

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
