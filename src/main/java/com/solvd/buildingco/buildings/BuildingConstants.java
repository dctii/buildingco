package com.solvd.buildingco.buildings;

public final class BuildingConstants {

    // Houses
    public final static int HOUSE_MIN_NUM_ROOMS = 1;
    public final static int HOUSE_MAX_NUM_ROOMS = 8;
    public final static int HOUSE_MIN_NUM_BATHROOMS = 1;
    public final static int HOUSE_MAX_NUM_BATHROOMS = 8;
    public final static int HOUSE_MIN_NUM_GARAGE_CAP = 0;
    public final static int HOUSE_MAX_NUM_GARAGE_CAP = 4;

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


    private BuildingConstants() {}
}
