package com.solvd.buildingco.exception;

public class BuildingTypeException extends RuntimeException {
    // if not the correct building type
    public BuildingTypeException(String message) {
        super(message);
    }
}
