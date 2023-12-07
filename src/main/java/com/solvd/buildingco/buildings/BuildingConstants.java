package com.solvd.buildingco.buildings;

import java.math.BigDecimal;

public final class BuildingConstants {

    // Industrial Buildings
    public static final int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_STEEL_COLUMN = 1000; // sq ft
    // how frequently industrial glass is needed by a certain amount of square feet
    public static final int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_GLASS = 50; // sq ft
    // how frequently cladding materials are needed by a certain amount of square feet
    public static final int INDUSTRIAL_BUILDING_SQUARE_FEET_PER_UNIT_OF_CLADDING = 2; // sq ft

    // Skyscraper

    // fixed cost for a skyscraper lobby
    public static final BigDecimal SKYSCRAPER_LOBBY_FIXED_COST = new BigDecimal("20000.00");

    // fixed cost for skyscraper foundation
    public static final BigDecimal SKYSCRAPER_FOUNDATION_COST_FACTOR = new BigDecimal("1000.00");


    private BuildingConstants() {
    }
}
