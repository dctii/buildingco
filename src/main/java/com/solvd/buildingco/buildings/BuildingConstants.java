package com.solvd.buildingco.buildings;

import java.math.BigDecimal;

public final class BuildingConstants {

    // Houses
    public final static int HOUSE_MIN_NUM_ROOMS = 1;
    public final static int HOUSE_MAX_NUM_ROOMS = 8;
    public final static int HOUSE_MIN_NUM_BATHROOMS = 1;
    public final static int HOUSE_MAX_NUM_BATHROOMS = 8;
    public final static int HOUSE_MIN_NUM_GARAGE_CAP = 0;
    public final static int HOUSE_MAX_NUM_GARAGE_CAP = 4;

    // factory method with constants used in BuildingPrompt; values are arbitrary
    public final static int HOUSE_BASE_SQUARE_FOOTAGE = 550; // assumed one room home sq footage
    public final static int HOUSE_BASE_CONSTRUCTION_DAYS = 30;
    public final static int HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_ROOM = 200;
    public final static int HOUSE_ADDITIONAL_SQUARE_FOOTAGE_PER_CAR = 100;
    public final static int HOUSE_ADDITIONAL_CONSTRUCTION_DAYS_PER_CAR = 3;

    // Industrial Buildings
    public final static int INDUSTRIAL_MIN_SQUARE_FOOTAGE = 5000;
    public final static int INDUSTRIAL_MAX_SQUARE_FOOTAGE = 75000;
    public final static int INDUSTRIAL_MIN_FLOORS = 1;
    public final static int INDUSTRIAL_MAX_FLOORS = 4;

    // Skyscrapers
    public final static int SKYSCRAPER_MIN_SQUARE_FOOTAGE_PER_LEVEL = 10000;
    public final static int SKYSCRAPER_MAX_SQUARE_FOOTAGE_PER_LEVEL = 50000;
    public final static int SKYSCRAPER_MIN_LEVELS = 40;
    public final static int SKYSCRAPER_MAX_LEVELS = 100;

    public final static BigDecimal SKYSCRAPER_LOBBY_FIXED_COST = new BigDecimal("20000.00");
    public final static BigDecimal SKYSCRAPER_FOUNDATION_COST_FACTOR = new BigDecimal("1000.00");

    // Work Descriptions
    public final static String ARCHITECTURE_WORK_DESCRIPTION = "Architectural Design";
    public final static String CONSTRUCTION_WORK_DESCRIPTION = "Construction Work";
    public final static String ENGINEERING_DESCRIPTION = "Engineering Work";
    public final static String PROJECT_MANAGEMENT_DESCRIPTION = "Project Management";

    // Building Type strings
    public final static String INDUSTRIAL_BUILDING_TYPE = "industrial building";
    public final static String SKYSCRAPER_BUILDING_TYPE = "skyscraper";


    private BuildingConstants() {
    }
}
