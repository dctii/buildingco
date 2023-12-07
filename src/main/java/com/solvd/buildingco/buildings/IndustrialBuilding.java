package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import com.solvd.buildingco.utilities.BuildingUtils;
import com.solvd.buildingco.utilities.MaterialOrderGenerator;
import com.solvd.buildingco.utilities.StringFormatters;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.INDUSTRIAL_BUILDING;

public class IndustrialBuilding extends Building<BigDecimal> implements IEstimate {
    private int squareFootage; // square footage user chooses building to be
    private int numberOfFloors; // number of floors user chooses building to be

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

    public IndustrialBuilding(int squareFootage, int numberOfFloors, int constructionDays) {
        super(constructionDays);

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

        setConstructionDays(calculatedConstructionDays);

        return BuildingCostCalculator.calculateLaborCost(
                this,
                customerEndDate
        );
    }


    // getters and setters

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
        Class<?> currClass = IndustrialBuilding.class;
        String[] fieldNames = {
                "squareFootage",
                "numberOfFloors",
        };

        String parentToString = super.toString();
        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, parentToString,
                fieldsString);
    }

}
