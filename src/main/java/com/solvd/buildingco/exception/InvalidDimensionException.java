package com.solvd.buildingco.exception;

public class InvalidDimensionException extends RuntimeException {
    // invalid dimensions for building
    public InvalidDimensionException(String message) {
        super(message);
    }
}
