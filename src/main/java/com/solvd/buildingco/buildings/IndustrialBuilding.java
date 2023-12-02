package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.INDUSTRIAL_BUILDING;

public class IndustrialBuilding extends Building<BigDecimal> implements IEstimate {
    private int squareFootage; // square footage user chooses building to be
    private int numberOfFloors; // number of floors user chooses building to be
    private int constructionDays; // number of business days to construct building

    public IndustrialBuilding() {
        super();
    }

    public IndustrialBuilding(int squareFootage) {
        super();

        BuildingUtils.validateSquareFootage(squareFootage);

        this.squareFootage = squareFootage;
        this.numberOfFloors = INDUSTRIAL_BUILDING.getMinLevels();
    }

    public IndustrialBuilding(int squareFootage, int numberOfFloors) {
        super();

        BuildingUtils.validateSquareFootage(squareFootage);
        BuildingUtils.validateNumberOfFloors(numberOfFloors);

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
                        INDUSTRIAL_BUILDING.getBuildingType(),
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
        BuildingUtils.validateSquareFootage(squareFootage);

        this.squareFootage = squareFootage;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        BuildingUtils.validateNumberOfFloors(numberOfFloors);

        this.numberOfFloors = numberOfFloors;
    }

    @Override
    public String toString() {
        String[] fieldNames = {
                "squareFootage",
                "numberOfFloors",
                "constructionDays"
        };

        String className = this.getClass().getSimpleName();
        String superResult = StringFormatters.removeEdges(super.toString());

        String result = Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(this, fieldName);
                    return fieldValue != null
                            ? StringFormatters.stateEquivalence(fieldName, fieldValue)
                            : StringConstants.EMPTY_STRING;
                })
                .filter(fieldValue -> !fieldValue.isEmpty())
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

        return className
                + StringFormatters.nestInCurlyBraces(
                superResult + StringConstants.COMMA_DELIMITER + result
        );
    }

}
