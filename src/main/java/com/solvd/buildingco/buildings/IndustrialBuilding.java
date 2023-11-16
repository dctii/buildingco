package com.solvd.buildingco.buildings;

import com.solvd.buildingco.exception.InvalidDimensionException;
import com.solvd.buildingco.exception.InvalidFloorNumberException;
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

public class IndustrialBuilding extends Building<BigDecimal> implements IEstimate {
    private static final Logger LOGGER = LogManager.getLogger(IndustrialBuilding.class);
    private int squareFootage;
    private int numberOfFloors;
    private int constructionDays;


    private final static String INVALID_DIMENSIONS_MESSAGE = "Invalid dimensions for IndustrialBuilding.";
    private final static String INVALID_NUM_FLOORS_MESSAGE = "Invalid number of floors for Industrial Building.";

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

        // general calculations for building
        double sideLength = Math.sqrt(squareFootage);
        double buildingPerimeter = 4 * sideLength;
        double wallArea = buildingPerimeter * (INDUSTRIAL_BUILDING_HEIGHT_PER_FLOOR * numberOfFloors);

        // final quantities for each item
        int insulationQuantity = (int) (wallArea * INDUSTRIAL_BUILDING_INSULATION_THICKNESS_IN_FEET);
        int steelBeamsQuantity = squareFootage / INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_BEAM;
        int steelColumnsQuantity = squareFootage / INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_COLUMN;
        int concreteQuantity = squareFootage * numberOfFloors;
        int glassQuantity = squareFootage / INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_GLASS;
        int roofingQuantity, interiorFinishingQuantity;
        roofingQuantity = interiorFinishingQuantity = squareFootage;
        int claddingMaterialsQuantity = squareFootage / INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_CLADDING;
        int electricalSuppliesQuantity, plumbingSuppliesQuantity, hvacSuppliesQuantity;
        electricalSuppliesQuantity = plumbingSuppliesQuantity = hvacSuppliesQuantity = numberOfFloors;

        // initialize the ArrayList of OrderItems
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        // add items to the list
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STEEL_BEAMS),
                        steelBeamsQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.STEEL_COLUMNS),
                        steelColumnsQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CONCRETE_INDUSTRIAL),
                        concreteQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.GLASS_INDUSTRIAL),
                        glassQuantity
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
                        ItemRepository.getItem(ItemNames.ROOFING_HOUSE),
                        roofingQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.INTERIOR_FINISHING_MATERIALS),
                        interiorFinishingQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.CLADDING_MATERIAL),
                        claddingMaterialsQuantity
                )
        );
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL),
                        electricalSuppliesQuantity
                )
        );
        orderItems.add(
                new OrderItem(ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                        plumbingSuppliesQuantity
                )
        );
        orderItems.add(
                new OrderItem(ItemRepository.getItem(ItemNames.HVAC_SUPPLIES),
                        hvacSuppliesQuantity
                )
        );
        orderItems.add(
                new OrderItem(ItemRepository.getItem(ItemNames.FRONT_LOADER_TRUCK),
                        1,
                        1
                )
        );

        // populate order with items
        Order loadedOrder = OrderUtils.loadOrder(orderItems);

        return loadedOrder;
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
