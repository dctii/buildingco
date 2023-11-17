package com.solvd.buildingco.exception;

public class InvalidFloorNumberException extends RuntimeException {
    // invalid number of floors for building
    public InvalidFloorNumberException(String message) {
        super(message);
    }
}
