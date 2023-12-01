package com.solvd.buildingco.inventory;

public enum UnitMeasurement {

    SQUARE_FOOT("square foot"),
    GALLON("gallon"),
    UNIT("unit");

    private final String name;

    UnitMeasurement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
