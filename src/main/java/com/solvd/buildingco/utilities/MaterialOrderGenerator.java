package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;
import com.solvd.buildingco.exception.BuildingTypeException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.inventory.ItemNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.solvd.buildingco.buildings.BuildingConstants.*;
import static com.solvd.buildingco.utilities.BooleanUtils.isValidBuildingType;

public class MaterialOrderGenerator {
    private static final Logger LOGGER = LogManager.getLogger(MaterialOrderGenerator.class);
    private static final String HOUSE_TYPE_EXCEPTION_MESSAGE = "Only accepts House type";
    private static final String INDUSTRIAL_TYPE_EXCEPTION_MESSAGE = "Only accepts " +
            "IndustrialBuilding type";
    private static final String SKYSCRAPER_TYPE_EXCEPTION_MESSAGE = "Only accepts Skyscraper type";
    private static final String WRONG_BUILDING_TYPE_EXCEPTION_MESSAGE = "That is not a supported " +
            "Building type";

    public static Order generateMaterialOrder(Building building) {
        if (isValidBuildingType(building)) {
            return generateBuildingOrder(building);
        } else {
            LOGGER.warn(WRONG_BUILDING_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(WRONG_BUILDING_TYPE_EXCEPTION_MESSAGE);
        }
    }

    private static Order generateBuildingOrder(Building building) {
        OrderBuilder orderBuilder = new OrderBuilder();

        Map<String, Integer> buyableItems = null;
        Map<String, Integer[]> rentableItems = null;

        if (building instanceof House) {
            buyableItems = generateBuyableItemMap((House) building);
            rentableItems = generateRentableItemMap((House) building);
        } else if (building instanceof IndustrialBuilding) {
            buyableItems = generateBuyableItemMap((IndustrialBuilding) building);
            rentableItems = generateRentableItemMap((IndustrialBuilding) building);
        } else if (building instanceof Skyscraper) {
            buyableItems = generateBuyableItemMap((Skyscraper) building);
            rentableItems = generateRentableItemMap((Skyscraper) building);
        } else {
            LOGGER.warn(WRONG_BUILDING_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(WRONG_BUILDING_TYPE_EXCEPTION_MESSAGE);
        }

        buyableItems.forEach(
                orderBuilder::addItem
        );
        rentableItems.forEach(
                orderBuilder::addItem
        );

        Order order = orderBuilder.build();

        return order;
    }

    private static Map<String, Integer> generateBuyableItemMap(House house) {
        if (!(house instanceof House)) {
            LOGGER.warn(HOUSE_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(HOUSE_TYPE_EXCEPTION_MESSAGE);
        }

        Map<String, Integer> items = new HashMap<>();

        int concreteQuantity, roofingQuantity;
        concreteQuantity = roofingQuantity = house.getSquareFootage();
        int structuralWoodQuantity = calculateStructuralWoodQuantity(house);
        int drywallQuantity = calculateDrywallQuantity(house);
        int insulationQuantity = calculateInsulationQuantity(house);
        int flooringQuantity = calculateFlooringQuantity(house);
        int paintQuantity = calculatePaintQuantity(house);
        int plumbingSuppliesQuantity = calculatePlumbingSuppliesQuantity(house);
        int electricalSuppliesQuantity = calculateElectricSuppliesQuantity(house);

        items.put(ItemNames.CONCRETE, concreteQuantity);
        items.put(ItemNames.ROOFING_HOUSE, roofingQuantity);
        items.put(ItemNames.STRUCTURAL_WOOD, structuralWoodQuantity);
        items.put(ItemNames.DRYWALL, drywallQuantity);
        items.put(ItemNames.INSULATION_MATERIALS, insulationQuantity);
        items.put(ItemNames.FLOORING, flooringQuantity);
        items.put(ItemNames.PAINT, paintQuantity);
        items.put(ItemNames.PLUMBING_SUPPLIES, plumbingSuppliesQuantity);
        items.put(ItemNames.ELECTRICAL_SUPPLIES_HOUSE, electricalSuppliesQuantity);

        return items;
    }

    private static Map<String, Integer[]> generateRentableItemMap(House house) {
        if (!(house instanceof House)) {
            LOGGER.warn(HOUSE_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(HOUSE_TYPE_EXCEPTION_MESSAGE);
        }

        Map<String, Integer[]> items = new HashMap<>();

        final int CONCRETE_MIXER_QUANTITY = 1;
        final int CONCRETE_MIXER_MONTHS_TO_RENT = 1;

        items.put(ItemNames.CONCRETE_MIXER, new Integer[]{CONCRETE_MIXER_QUANTITY, CONCRETE_MIXER_MONTHS_TO_RENT});

        return items;
    }

    private static Map<String, Integer> generateBuyableItemMap(IndustrialBuilding industrialBuilding) {
        if (!(industrialBuilding instanceof IndustrialBuilding)) {
            LOGGER.warn(INDUSTRIAL_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(INDUSTRIAL_TYPE_EXCEPTION_MESSAGE);
        }

        Map<String, Integer> items = new HashMap<>();

        int insulationQuantity = calculateInsulationQuantity(industrialBuilding);
        int steelBeamsQuantity = calculateSteelBeamsQuantity(industrialBuilding);
        int steelColumnsQuantity = calculateSteelColumnsQuantity(industrialBuilding);
        int concreteQuantity = calculateConcreteQuantity(industrialBuilding);
        int glassQuantity = calculateGlassQuantity(industrialBuilding);
        int roofingQuantity, interiorFinishingQuantity;
        roofingQuantity = interiorFinishingQuantity = industrialBuilding.getSquareFootage();
        int claddingMaterialsQuantity =
                calculateCladdingMaterialsQuantity(industrialBuilding);
        int electricalSuppliesQuantity, plumbingSuppliesQuantity, hvacSuppliesQuantity;
        electricalSuppliesQuantity = plumbingSuppliesQuantity = hvacSuppliesQuantity =
                industrialBuilding.getNumberOfFloors();

        items.put(ItemNames.STEEL_BEAMS, steelBeamsQuantity);
        items.put(ItemNames.STEEL_COLUMNS, steelColumnsQuantity);
        items.put(ItemNames.CONCRETE_INDUSTRIAL, concreteQuantity);
        items.put(ItemNames.GLASS_INDUSTRIAL, glassQuantity);
        items.put(ItemNames.INSULATION_MATERIALS, insulationQuantity);
        items.put(ItemNames.ROOFING_HOUSE, roofingQuantity);
        items.put(ItemNames.INTERIOR_FINISHING_MATERIALS, interiorFinishingQuantity);
        items.put(ItemNames.CLADDING_MATERIAL, claddingMaterialsQuantity);
        items.put(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL, electricalSuppliesQuantity);
        items.put(ItemNames.PLUMBING_SUPPLIES, plumbingSuppliesQuantity);
        items.put(ItemNames.HVAC_SUPPLIES, hvacSuppliesQuantity);

        return items;
    }

    private static Map<String, Integer[]> generateRentableItemMap(IndustrialBuilding industrialBuilding) {
        if (!(industrialBuilding instanceof IndustrialBuilding)) {
            LOGGER.warn(INDUSTRIAL_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(INDUSTRIAL_TYPE_EXCEPTION_MESSAGE);
        }

        Map<String, Integer[]> items = new HashMap<>();

        final int FRONT_LOADER_TRUCK_QUANTITY = 1;
        final int FRONT_LOADER_TRUCK_MONTHS_TO_RENT = 1;

        items.put(
                ItemNames.FRONT_LOADER_TRUCK,
                new Integer[]{FRONT_LOADER_TRUCK_QUANTITY, FRONT_LOADER_TRUCK_MONTHS_TO_RENT}
        );

        return items;
    }

    private static Map<String, Integer> generateBuyableItemMap(Skyscraper skyscraper) {
        if (!(skyscraper instanceof Skyscraper)) {
            LOGGER.warn(SKYSCRAPER_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(SKYSCRAPER_TYPE_EXCEPTION_MESSAGE);
        }

        Map<String, Integer> items = new HashMap<>();

        int concreteQuantity, interiorFinishingQuantity;
        concreteQuantity = interiorFinishingQuantity = calculateAllSquareFootage(skyscraper);
        int steelBeamsQuantity = calculateSteelBeamsQuantity(skyscraper);
        int glassQuantity = calculateGlassQuantity(skyscraper);
        int insulationQuantity = calculateInsulationQuantity(skyscraper);
        int claddingMaterialsQuantity = calculateCladdingMaterialsQuantity(skyscraper);
        int electricalSuppliesQuantity, plumbingSuppliesQuantity, hvacSuppliesQuantity;
        electricalSuppliesQuantity = plumbingSuppliesQuantity = hvacSuppliesQuantity =
                skyscraper.getNumberOfLevels();

        items.put(ItemNames.CONCRETE_HIGH_GRADE, concreteQuantity);
        items.put(ItemNames.INTERIOR_FINISHING_MATERIALS, interiorFinishingQuantity);
        items.put(ItemNames.STEEL_BEAMS_HIGH_GRADE, steelBeamsQuantity);
        items.put(ItemNames.GLASS_HIGH_GRADE_INDUSTRIAL, glassQuantity);
        items.put(ItemNames.INSULATION_MATERIALS, insulationQuantity);
        items.put(ItemNames.CLADDING_MATERIAL, claddingMaterialsQuantity);
        items.put(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL, electricalSuppliesQuantity);
        items.put(ItemNames.PLUMBING_SUPPLIES, plumbingSuppliesQuantity);
        items.put(ItemNames.HVAC_SUPPLIES, hvacSuppliesQuantity);

        return items;
    }

    private static Map<String, Integer[]> generateRentableItemMap(Skyscraper skyscraper) {
        if (!(skyscraper instanceof Skyscraper)) {
            LOGGER.warn(SKYSCRAPER_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(SKYSCRAPER_TYPE_EXCEPTION_MESSAGE);
        }

        Map<String, Integer[]> items = new HashMap<>();

        final int TOWER_CRANE_QUANTITY = 1;
        final int TOWER_CRANE_MONTHS_TO_RENT = 1;

        items.put(
                ItemNames.TOWER_CRANE,
                new Integer[]{TOWER_CRANE_QUANTITY, TOWER_CRANE_MONTHS_TO_RENT}
        );

        return items;
    }

    private static int calculateStructuralWoodQuantity(House house) {
        BigDecimal woodFramingQuantityPerRoom =
                BigDecimalUtils.multiply(calculateWallAreaPerRoom(), HOUSE_WOOD_USAGE_FACTOR_PER_FOOT);

        BigDecimal woodFramingQuantityForGarage =
                BigDecimalUtils.multiply(calculateGarageWallArea(house),
                        HOUSE_WOOD_USAGE_FACTOR_PER_FOOT);

        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.add(
                        BigDecimalUtils.multiply(woodFramingQuantityPerRoom, house.getNumRooms()),
                        woodFramingQuantityForGarage
                )
        );
    }

    private static int calculateDrywallQuantity(House house) {

        BigDecimal drywallQuantityPerRoom = BigDecimalUtils.add(
                calculateWallAreaPerRoom(),
                calculateCeilingAreaPerRoom()
        );

        BigDecimal drywallQuantityForGarage = BigDecimalUtils.add(
                calculateGarageWallArea(house),
                calculateGarageCeilingArea(house)
        );

        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.add(
                        BigDecimalUtils.multiply(drywallQuantityPerRoom, house.getNumRooms()),
                        drywallQuantityForGarage
                )
        );
    }

    private static int calculateInsulationQuantity(House house) {

        BigDecimal insulationQuantityPerRoom =
                BigDecimalUtils.multiply(calculateWallAreaPerRoom(), HOUSE_INSULATION_THICKNESS_IN_FEET);

        BigDecimal insulationQuantityForGarage =
                BigDecimalUtils.add(
                        calculateGarageWallArea(house),
                        HOUSE_INSULATION_THICKNESS_IN_FEET
                );


        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.add(
                        BigDecimalUtils.multiply(insulationQuantityPerRoom, house.getNumRooms()),
                        insulationQuantityForGarage
                )
        );
    }

    private static int calculateFlooringQuantity(House house) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.subtract(
                        house.getSquareFootage(),
                        calculateGarageSquareFootage(house)
                )
        );
    }

    private static int calculatePaintQuantity(House house) {

        BigDecimal paintQuantityPerRoom =
                BigDecimalUtils.divide(
                        BigDecimalUtils.add(calculateWallAreaPerRoom(), calculateCeilingAreaPerRoom()),
                        HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON
                );

        BigDecimal paintQuantityForGarage =
                BigDecimalUtils.divide(
                        BigDecimalUtils.add(calculateGarageWallArea(house), calculateGarageCeilingArea(house)),
                        HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON
                );

        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.add(
                        BigDecimalUtils.multiply(paintQuantityPerRoom, house.getNumRooms()),
                        paintQuantityForGarage
                )
        );
    }

    private static int calculatePlumbingSuppliesQuantity(House house) {
        return house.getNumRooms() + HOUSE_KITCHEN_QUANTITY;
    }

    private static int calculateElectricSuppliesQuantity(House house) {
        return house.getNumRooms() + HOUSE_GARAGE_QUANTITY;
    }

    private static BigDecimal calculateAverageRoomPerimeter() {
        return BigDecimalUtils.multiply(
                2,
                BigDecimalUtils.add(HOUSE_AVERAGE_ROOM_LENGTH, HOUSE_AVERAGE_ROOM_WIDTH)
        );
    }

    private static BigDecimal calculateWallAreaPerRoom() {
        return BigDecimalUtils.multiply(
                calculateAverageRoomPerimeter(),
                HOUSE_ROOM_HEIGHT
        );
    }

    private static BigDecimal calculateCeilingAreaPerRoom() {
        return BigDecimalUtils.multiply(HOUSE_AVERAGE_ROOM_LENGTH, HOUSE_AVERAGE_ROOM_WIDTH);
    }

    private static BigDecimal calculateGarageSquareFootage(House house) {
        return BigDecimalUtils.multiply(HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR, house.getGarageCapacity());
    }

    private static BigDecimal calculateGarageCeilingArea(House house) {
        return calculateGarageSquareFootage(house);
    }

    private static BigDecimal calculateGaragePerimeter(House house) {
        return BigDecimalUtils.multiply(
                2,
                BigDecimalUtils.add(
                        BigDecimalUtils.sqrt(calculateGarageSquareFootage(house)),
                        HOUSE_AVERAGE_ROOM_WIDTH
                )
        );
    }

    private static BigDecimal calculateGarageWallArea(House house) {
        return BigDecimalUtils.multiply(calculateGaragePerimeter(house),
                HOUSE_ROOM_HEIGHT);
    }

    private static int calculateInsulationQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiply(calculateWallArea(industrialBuilding),
                        INDUSTRIAL_BUILDING_INSULATION_THICKNESS_IN_FEET
                )
        );
    }

    private static int calculateSteelBeamsQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(industrialBuilding.getSquareFootage(),
                        INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_BEAM
                )
        );
    }

    private static int calculateSteelColumnsQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(industrialBuilding.getSquareFootage(),
                        INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_COLUMN
                )
        );
    }

    private static int calculateConcreteQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiply(industrialBuilding.getSquareFootage(),
                        industrialBuilding.getNumberOfFloors()
                )
        );
    }

    private static int calculateGlassQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(industrialBuilding.getSquareFootage(),
                        INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_GLASS
                )
        );
    }

    private static int calculateCladdingMaterialsQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(industrialBuilding.getSquareFootage(),
                        INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_CLADDING
                )
        );
    }


    private static BigDecimal calculateWallArea(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.multiply(
                calculateBuildingPerimeter(industrialBuilding),
                BigDecimalUtils.multiply(
                        INDUSTRIAL_BUILDING_HEIGHT_PER_FLOOR,
                        industrialBuilding.getNumberOfFloors()
                )
        );
    }

    private static BigDecimal calculateBuildingPerimeter(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.multiply(
                4,
                calculateSideLength(industrialBuilding)
        );
    }

    private static BigDecimal calculateSideLength(IndustrialBuilding industrialBuilding) {
        BigDecimal squareFootage =
                BigDecimal.valueOf(
                        industrialBuilding.getSquareFootage()
                );
        return (BigDecimal) BigDecimalUtils.sqrt(squareFootage);
    }

    private static int calculateSteelBeamsQuantity(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(calculateAllSquareFootage(skyscraper),
                        SKYSCRAPER_SQUARE_FEET_PER_STEEL_BEAM
                )
        );
    }

    private static int calculateGlassQuantity(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiplyAll(
                        calculatePerimeter(skyscraper),
                        SKYSCRAPER_HEIGHT_PER_LEVEL,
                        skyscraper.getNumberOfLevels()
                )
        );
    }

    private static int calculateInsulationQuantity(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiply(calculateWallArea(skyscraper),
                        SKYSCRAPER_INSULATION_THICKNESS_IN_FEET
                )
        );
    }

    private static int calculateCladdingMaterialsQuantity(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(
                        BigDecimalUtils.multiply(skyscraper.getSquareFootagePerLevel(),
                                skyscraper.getNumberOfLevels()
                        ),
                        2
                )
        );
    }

    private static int calculateAllSquareFootage(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                (BigDecimalUtils.multiply(
                        skyscraper.getSquareFootagePerLevel(),
                        skyscraper.getNumberOfLevels())
                )
        );
    }

    private static BigDecimal calculatePerimeter(Skyscraper skyscraper) {
        return BigDecimalUtils.multiply(
                4,
                calculateLevelSideLength(skyscraper)
        );
    }

    private static BigDecimal calculateLevelSideLength(Skyscraper skyscraper) {
        BigDecimal squareFootage = BigDecimal.valueOf(skyscraper.getSquareFootagePerLevel());
        return (BigDecimal) BigDecimalUtils.sqrt(squareFootage);
    }

    private static BigDecimal calculateWallArea(Skyscraper skyscraper) {
        return BigDecimalUtils.multiplyAll(
                calculatePerimeter(skyscraper),
                SKYSCRAPER_HEIGHT_PER_LEVEL,
                skyscraper.getNumberOfLevels()
        );
    }

}
