package com.solvd.buildingco.buildings;

import java.math.BigDecimal;

public final class BuildingConstants {

    // Houses
    // min number of rooms a house can have
    public final static int HOUSE_MIN_NUM_ROOMS = 1; // unit
    // max number of rooms a house can have
    public final static int HOUSE_MAX_NUM_ROOMS = 8; // unit
    // each house has one kitchen
    public final static int HOUSE_KITCHEN_QUANTITY = 1; // unit
    // each house has one garage
    public final static int HOUSE_GARAGE_QUANTITY = 1; // unit
    // min number of bathrooms a house can have
    public final static int HOUSE_MIN_NUM_BATHROOMS = 1; // unit
    // max number of bathrooms a house can have
    public final static int HOUSE_MAX_NUM_BATHROOMS = 8; // unit
    // min size of garage capacity, 0 means no garage
    public final static int HOUSE_MIN_NUM_GARAGE_CAP = 0; // unit
    // max size of garage capacity, 4 means it can fit 4 cars
    public final static int HOUSE_MAX_NUM_GARAGE_CAP = 4; // unit

    // factory method with constants used in BuildingPrompt; values are relatively arbitrary

    // Houses
    // if one room, assume the house is 550 square feet excluding the garage
    public final static int HOUSE_BASE_SQUARE_FOOTAGE = 550; // sq ft
    // additional sq ft for each additional car, garage cap increment
    public final static int HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR = 100; // sq ft
    // height of the rooms of the house
    public final static int HOUSE_ROOM_HEIGHT = 9; // ft
    // average room length of house
    public final static int HOUSE_AVERAGE_ROOM_LENGTH = 15; // ft
    // average room width of house
    public final static int HOUSE_AVERAGE_ROOM_WIDTH = 12; // ft
    // amount of wood used for each foot of a wall length
    public final static double HOUSE_WOOD_USAGE_FACTOR_PER_FOOT = 0.5; // ft
    // thickness of house insulation in feet
    public final static double HOUSE_INSULATION_THICKNESS_IN_FEET = 3 / 12; // ft
    // how much square footage is covered by one gallon of paint
    public final static int HOUSE_PAINT_COVERAGE_BY_SQUARE_FEET_PER_GALLON = 375; // sq ft
    // starting construction time of a house
    public final static int HOUSE_BASE_CONSTRUCTION_DAYS = 30;
    // additional construction days per increment of garage cap
    public final static int HOUSE_ADDITIONAL_CONSTRUCTION_DAYS_PER_CAR = 3;

    // Industrial Buildings
    // min square footage for industrial building
    public final static int INDUSTRIAL_MIN_SQUARE_FOOTAGE = 5000;
    // max square footage for industrial building
    public final static int INDUSTRIAL_MAX_SQUARE_FOOTAGE = 75000;
    // min number of floors for industrial building
    public final static int INDUSTRIAL_MIN_FLOORS = 1;
    // max number of floors for industrial building
    public final static int INDUSTRIAL_MAX_FLOORS = 4;
    // height of each floor for industrial building
    public final static double INDUSTRIAL_BUILDING_HEIGHT_PER_FLOOR = 18; // ft
    // how frequently a steel beam is needed by a certain amount of square feet
    public final static int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_BEAM = 1000; // sq ft
    // how frequently a steel column is needed by a certain amount of square feet
    public final static int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_COLUMN = 1000; // sq ft
    // how frequently industrial glass is needed by a certain amount of square feet
    public final static int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_GLASS = 50; // sq ft
    // how frequently cladding materials are needed by a certain amount of square feet
    public final static int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_CLADDING = 2; // sq ft
    // thickness of industrial building insulation in feet
    public final static double INDUSTRIAL_BUILDING_INSULATION_THICKNESS_IN_FEET = 6 / 12; // 6 in

    // Skyscrapers
    // min square footage per level for skyscraper
    public final static int SKYSCRAPER_MIN_SQUARE_FOOTAGE_PER_LEVEL = 10000;
    // max square footage per level for skyscraper
    public final static int SKYSCRAPER_MAX_SQUARE_FOOTAGE_PER_LEVEL = 50000;
    // height of each floor of the Skyscraper
    public final static double SKYSCRAPER_HEIGHT_PER_LEVEL = 9; // ft
    // thickness of skyscraper insulation in feet
    public final static double SKYSCRAPER_INSULATION_THICKNESS_IN_FEET = 1 / 12; // in
    // how frequently a steel beam is needed by a certain amount of square feet
    public final static int SKYSCRAPER_SQUARE_FEET_PER_STEEL_BEAM = 40; // sq ft
    // min amount of levels the skyscraper can have
    public final static int SKYSCRAPER_MIN_LEVELS = 15;
    // max amount of levels the skyscraper can have
    public final static int SKYSCRAPER_MAX_LEVELS = 100;
    // fixed cost for a skyscraper lobby
    public final static BigDecimal SKYSCRAPER_LOBBY_FIXED_COST = new BigDecimal("20000.00");
    // fixed cost for skyscraper foundation
    public final static BigDecimal SKYSCRAPER_FOUNDATION_COST_FACTOR = new BigDecimal("1000.00");


    // Building Type strings
    public final static String INDUSTRIAL_BUILDING_TYPE = "industrial building";
    public final static String SKYSCRAPER_BUILDING_TYPE = "skyscraper";

    // Work Descriptions
    public final static String ARCHITECTURE_WORK_DESCRIPTION = "Architectural Design";
    public final static String CONSTRUCTION_WORK_DESCRIPTION = "Construction Work";
    public final static String ENGINEERING_DESCRIPTION = "Engineering Work";
    public final static String PROJECT_MANAGEMENT_DESCRIPTION = "Project Management";


    private BuildingConstants() {
    }
}
