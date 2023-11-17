package com.solvd.buildingco.exception;

public class InvalidNumRoomsException extends RuntimeException {
    // invalid number of rooms for building
    public InvalidNumRoomsException(String message) {
        super(message);
    }
}
