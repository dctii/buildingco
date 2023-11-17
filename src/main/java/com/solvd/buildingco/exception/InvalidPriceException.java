package com.solvd.buildingco.exception;

public class InvalidPriceException extends RuntimeException {
    // invalid price or number type for price
    public InvalidPriceException(String message) {
        super(message);
    }
}
