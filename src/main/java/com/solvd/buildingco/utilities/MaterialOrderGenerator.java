package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;
import com.solvd.buildingco.exception.BuildingTypeException;
import com.solvd.buildingco.finance.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.solvd.buildingco.buildings.BuildingConstants.*;
import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.INDUSTRIAL_BUILDING;
import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.SKYSCRAPER;
import static com.solvd.buildingco.buildings.ResidentialBuildingSpecifications.HOUSE;
import static com.solvd.buildingco.inventory.Item.*;

public class MaterialOrderGenerator {
    private static final Logger LOGGER = LogManager.getLogger(MaterialOrderGenerator.class);
    private static final String HOUSE_TYPE_EXCEPTION_MESSAGE =
            "Only accepts House type";
    private static final String INDUSTRIAL_TYPE_EXCEPTION_MESSAGE =
            "Only accepts IndustrialBuilding type";
    private static final String SKYSCRAPER_TYPE_EXCEPTION_MESSAGE =
            "Only accepts Skyscraper type";
    private static final String WRONG_BUILDING_TYPE_EXCEPTION_MESSAGE =
            "That is not a supported Building type";

    public static Order generateMaterialOrder(Building building) {
        if (BooleanUtils.isValidBuildingType(building)) {
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

        items.put(CONCRETE.getName(), concreteQuantity);
        items.put(ROOFING_HOUSE.getName(), roofingQuantity);
        items.put(STRUCTURAL_WOOD.getName(), structuralWoodQuantity);
        items.put(DRYWALL.getName(), drywallQuantity);
        items.put(INSULATION_MATERIALS.getName(), insulationQuantity);
        items.put(FLOORING.getName(), flooringQuantity);
        items.put(PAINT.getName(), paintQuantity);
        items.put(PLUMBING_SUPPLIES.getName(), plumbingSuppliesQuantity);
        items.put(ELECTRICAL_SUPPLIES_HOUSE.getName(), electricalSuppliesQuantity);

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

        items.put(CONCRETE_MIXER.getName(), new Integer[]{CONCRETE_MIXER_QUANTITY,
                CONCRETE_MIXER_MONTHS_TO_RENT});

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

        items.put(STEEL_BEAMS.getName(), steelBeamsQuantity);
        items.put(STEEL_COLUMNS.getName(), steelColumnsQuantity);
        items.put(CONCRETE_INDUSTRIAL.getName(), concreteQuantity);
        items.put(GLASS_INDUSTRIAL.getName(), glassQuantity);
        items.put(INSULATION_MATERIALS.getName(), insulationQuantity);
        items.put(ROOFING_HOUSE.getName(), roofingQuantity);
        items.put(INTERIOR_FINISHING_MATERIALS.getName(), interiorFinishingQuantity);
        items.put(CLADDING_MATERIAL.getName(), claddingMaterialsQuantity);
        items.put(ELECTRICAL_SUPPLIES_INDUSTRIAL.getName(), electricalSuppliesQuantity);
        items.put(PLUMBING_SUPPLIES.getName(), plumbingSuppliesQuantity);
        items.put(HVAC_SUPPLIES.getName(), hvacSuppliesQuantity);

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
                FRONT_LOADER_TRUCK.getName(),
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

        items.put(CONCRETE_HIGH_GRADE.getName(), concreteQuantity);
        items.put(INTERIOR_FINISHING_MATERIALS.getName(), interiorFinishingQuantity);
        items.put(STEEL_BEAMS_HIGH_GRADE.getName(), steelBeamsQuantity);
        items.put(GLASS_HIGH_GRADE_INDUSTRIAL.getName(), glassQuantity);
        items.put(INSULATION_MATERIALS.getName(), insulationQuantity);
        items.put(CLADDING_MATERIAL.getName(), claddingMaterialsQuantity);
        items.put(ELECTRICAL_SUPPLIES_INDUSTRIAL.getName(), electricalSuppliesQuantity);
        items.put(PLUMBING_SUPPLIES.getName(), plumbingSuppliesQuantity);
        items.put(HVAC_SUPPLIES.getName(), hvacSuppliesQuantity);

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
                TOWER_CRANE.getName(),
                new Integer[]{TOWER_CRANE_QUANTITY, TOWER_CRANE_MONTHS_TO_RENT}
        );

        return items;
    }

    private static int calculateStructuralWoodQuantity(House house) {
        BigDecimal woodFramingQuantityPerRoom =
                BigDecimalUtils.multiply(calculateWallAreaPerRoom(), HOUSE.getWoodUsageFactorPerFoot());

        BigDecimal woodFramingQuantityForGarage =
                BigDecimalUtils.multiply(calculateGarageWallArea(house),
                        HOUSE.getWoodUsageFactorPerFoot());

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
                BigDecimalUtils.multiply(calculateWallAreaPerRoom(), HOUSE.getInsulationThickness());

        BigDecimal insulationQuantityForGarage =
                BigDecimalUtils.add(
                        calculateGarageWallArea(house),
                        HOUSE.getInsulationThickness()
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
                        HOUSE.getPaintCoverageBySquareFeet()
                );

        BigDecimal paintQuantityForGarage =
                BigDecimalUtils.divide(
                        BigDecimalUtils.add(calculateGarageWallArea(house), calculateGarageCeilingArea(house)),
                        HOUSE.getPaintCoverageBySquareFeet()
                );

        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.add(
                        BigDecimalUtils.multiply(paintQuantityPerRoom, house.getNumRooms()),
                        paintQuantityForGarage
                )
        );
    }

    private static int calculatePlumbingSuppliesQuantity(House house) {
        return house.getNumRooms() + HOUSE.getMinNumKitchens();
    }

    private static int calculateElectricSuppliesQuantity(House house) {
        int garageQuantity;

        if (
                HOUSE.getMinNumGarages() == 0
                        && house.getGarageCapacity() > 0
        ) {
            garageQuantity = 1;
        } else {
            garageQuantity = HOUSE.getMinNumGarages();
        }

        return house.getNumRooms() + garageQuantity;
    }

    private static BigDecimal calculateAverageRoomPerimeter() {
        return BigDecimalUtils.multiply(
                2,
                BigDecimalUtils.add(HOUSE.getAverageRoomLength(), HOUSE.getAverageRoomWidth())
        );
    }

    private static BigDecimal calculateWallAreaPerRoom() {
        return BigDecimalUtils.multiply(
                calculateAverageRoomPerimeter(),
                HOUSE.getRoomHeight()
        );
    }

    private static BigDecimal calculateCeilingAreaPerRoom() {
        return BigDecimalUtils.multiply(HOUSE.getAverageRoomLength(), HOUSE.getAverageRoomWidth());
    }

    private static BigDecimal calculateGarageSquareFootage(House house) {
        return BigDecimalUtils.multiply(HOUSE.getExtraConstructionDaysPerCar(), house.getGarageCapacity());
    }

    private static BigDecimal calculateGarageCeilingArea(House house) {
        return calculateGarageSquareFootage(house);
    }

    private static BigDecimal calculateGaragePerimeter(House house) {
        return BigDecimalUtils.multiply(
                2,
                BigDecimalUtils.add(
                        BigDecimalUtils.sqrt(calculateGarageSquareFootage(house)),
                        HOUSE.getAverageRoomWidth()
                )
        );
    }

    private static BigDecimal calculateGarageWallArea(House house) {
        return BigDecimalUtils.multiply(calculateGaragePerimeter(house),
                HOUSE.getRoomHeight());
    }

    private static int calculateInsulationQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiply(calculateWallArea(industrialBuilding),
                        INDUSTRIAL_BUILDING.getInsulationThickness()
                )
        );
    }

    private static int calculateSteelBeamsQuantity(IndustrialBuilding industrialBuilding) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.divide(industrialBuilding.getSquareFootage(),
                        INDUSTRIAL_BUILDING.getSquareFeetPerSteelBeam()
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
                        INDUSTRIAL_BUILDING.getHeightPerLevel(),
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
                        SKYSCRAPER.getSquareFeetPerSteelBeam()
                )
        );
    }

    private static int calculateGlassQuantity(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiplyAll(
                        calculatePerimeter(skyscraper),
                        SKYSCRAPER.getHeightPerLevel(),
                        skyscraper.getNumberOfLevels()
                )
        );
    }

    private static int calculateInsulationQuantity(Skyscraper skyscraper) {
        return BigDecimalUtils.roundToInt(
                BigDecimalUtils.multiply(calculateWallArea(skyscraper),
                        SKYSCRAPER.getInsulationThickness()
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
                SKYSCRAPER.getHeightPerLevel(),
                skyscraper.getNumberOfLevels()
        );
    }

    private MaterialOrderGenerator() {
        final String NO_GENERATOR_INSTANTIATION_MESSAGE =
                "This is a generator class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_GENERATOR_INSTANTIATION_MESSAGE);
    }

}
