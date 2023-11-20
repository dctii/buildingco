package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;
import com.solvd.buildingco.exception.BuildingTypeException;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.ItemNames;
import com.solvd.buildingco.inventory.ItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static com.solvd.buildingco.buildings.BuildingConstants.*;

//TODO: Use BigDecimal instead of primitive types, then round them up with java.math.RoundingMode
//TODO: Create more methods to reduce the amount of lines for the generateMaterialOrder() method
public class MaterialOrderGenerator {

    private static final Logger LOGGER = LogManager.getLogger(MaterialOrderGenerator.class);

    public static Order generateMaterialOrder(Building building) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();


        if (building instanceof House) {
            House house = (House) building;

            int squareFootage = house.getSquareFootage();
            int garageCapacity = house.getGarageCapacity();
            int numRooms = house.getNumRooms();
            int numBathrooms = house.getNumBathrooms();


            // general calculations for rooms
            int averageRoomPerimeter = 2 * (HOUSE_AVERAGE_ROOM_LENGTH + HOUSE_AVERAGE_ROOM_WIDTH);
            double wallAreaPerRoom = averageRoomPerimeter * HOUSE_ROOM_HEIGHT;
            double ceilingAreaPerRoom = HOUSE_AVERAGE_ROOM_LENGTH * HOUSE_AVERAGE_ROOM_WIDTH;

            // general calculations for material quantities for rooms
            double woodFramingQuantityPerRoom = wallAreaPerRoom * HOUSE_WOOD_USAGE_FACTOR_PER_FOOT;
            double drywallQuantityPerRoom = wallAreaPerRoom + ceilingAreaPerRoom;
            double paintQuantityPerRoom =
                    (wallAreaPerRoom + ceilingAreaPerRoom) / HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON;
            double insulationQuantityPerRoom = wallAreaPerRoom * HOUSE_INSULATION_THICKNESS_IN_FEET;

            // general calculations for garage
            double garageSquareFootage, garageCeilingArea;
            garageSquareFootage = garageCeilingArea =
                    HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR * garageCapacity;
            int garagePerimeter = 2 * ((int) Math.sqrt(garageSquareFootage) + HOUSE_AVERAGE_ROOM_WIDTH);
            double garageWallArea = garagePerimeter * HOUSE_ROOM_HEIGHT;

            // general calculations for material quantities for garage
            double woodFramingQuantityForGarage = garageWallArea * HOUSE_WOOD_USAGE_FACTOR_PER_FOOT;
            double drywallQuantityForGarage = garageWallArea + garageCeilingArea;
            double paintQuantityForGarage =
                    (garageWallArea + garageCeilingArea) / HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON;
            double insulationQuantityForGarage = garageWallArea + HOUSE_INSULATION_THICKNESS_IN_FEET;

            // final quantities for each item
            int concreteQuantity, roofingQuantity;
            concreteQuantity = roofingQuantity = squareFootage;
            int structuralWoodQuantity =
                    ((int) woodFramingQuantityPerRoom * numRooms) + (int) woodFramingQuantityForGarage;
            int drywallQuantity =
                    ((int) drywallQuantityPerRoom * numRooms) + (int) drywallQuantityForGarage;
            int insulationQuantity =
                    ((int) insulationQuantityPerRoom * numRooms) + (int) insulationQuantityForGarage;
            int flooringQuantity = squareFootage - (int) garageSquareFootage;
            int paintQuantity = ((int) paintQuantityPerRoom * numRooms) + (int) paintQuantityForGarage;
            int plumbingSuppliesQuantity = numBathrooms + HOUSE_KITCHEN_QUANTITY;
            int electricalSuppliesQuantity = numRooms + HOUSE_GARAGE_QUANTITY;

            // add items to the list
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.CONCRETE),
                            concreteQuantity
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
                            ItemRepository.getItem(ItemNames.STRUCTURAL_WOOD),
                            structuralWoodQuantity
                    )
            );

            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.DRYWALL),
                            drywallQuantity
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
                            ItemRepository.getItem(ItemNames.FLOORING),
                            flooringQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.PAINT),
                            paintQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                            plumbingSuppliesQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.ELECTRICAL_SUPPLIES_HOUSE),
                            electricalSuppliesQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.CONCRETE_MIXER),
                            1, // one concrete mixer vehicle
                            1
                    )
            );
        } else if (building instanceof IndustrialBuilding) {
            IndustrialBuilding industrialBuilding = (IndustrialBuilding) building;

            int squareFootage = industrialBuilding.getSquareFootage();
            int numberOfFloors = industrialBuilding.getNumberOfFloors();


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

        } else if (building instanceof Skyscraper) {
            //TODO: add logic for Skyscraper

            Skyscraper skyscraper = (Skyscraper) building;

            int squareFootagePerLevel = skyscraper.getSquareFootagePerLevel();
            int numberOfLevels = skyscraper.getNumberOfLevels();

            // general calculations for building
            double levelSideLength = Math.sqrt(squareFootagePerLevel);
            double perimeter = 4 * levelSideLength;
            double wallArea = (perimeter * SKYSCRAPER_HEIGHT_PER_LEVEL) * numberOfLevels;

            // final quantities for each item
            int allSquareFootage, concreteQuantity, interiorFinishingQuantity;
            allSquareFootage = concreteQuantity = interiorFinishingQuantity = (squareFootagePerLevel * numberOfLevels);
            int steelBeamsQuantity = allSquareFootage / SKYSCRAPER_SQUARE_FEET_PER_STEEL_BEAM;
            int glassQuantity = (int) (perimeter * SKYSCRAPER_HEIGHT_PER_LEVEL * numberOfLevels);
            int insulationQuantity = (int) (wallArea * SKYSCRAPER_INSULATION_THICKNESS_IN_FEET);
            int claddingMaterialsQuantity = (squareFootagePerLevel * numberOfLevels) / 2;
            int electricalSuppliesQuantity, plumbingSuppliesQuantity, hvacSuppliesQuantity;
            electricalSuppliesQuantity = plumbingSuppliesQuantity = hvacSuppliesQuantity = numberOfLevels;

            // add items to the list
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.CONCRETE_HIGH_GRADE),
                            concreteQuantity
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
                            ItemRepository.getItem(ItemNames.STEEL_BEAMS_HIGH_GRADE),
                            steelBeamsQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.GLASS_HIGH_GRADE_INDUSTRIAL),
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
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.PLUMBING_SUPPLIES),
                            plumbingSuppliesQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.HVAC_SUPPLIES),
                            hvacSuppliesQuantity
                    )
            );
            orderItems.add(
                    new OrderItem(
                            ItemRepository.getItem(ItemNames.TOWER_CRANE),
                            1,
                            2
                    )
            );
        } else {
            final String BUILDING_TYPE_EXCEPTION_MESSAGE = "Building type not found.";
            LOGGER.warn(BUILDING_TYPE_EXCEPTION_MESSAGE);
            throw new BuildingTypeException(BUILDING_TYPE_EXCEPTION_MESSAGE);
        }

        // populate order with items
        Order loadedOrder = OrderUtils.loadOrder(orderItems);

        return loadedOrder;
    }


}
