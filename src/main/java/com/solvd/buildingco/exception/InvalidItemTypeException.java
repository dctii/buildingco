package com.solvd.buildingco.exception;

public class InvalidItemTypeException extends RuntimeException {
    // Invalid Priceable type
    public InvalidItemTypeException(String message) {
        super(message);
    }
}
