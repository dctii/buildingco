package com.solvd.buildingco.exception;

public class InventoryItemNotFoundException extends RuntimeException {
    // inventory item unable to be found in ItemRepository
    public InventoryItemNotFoundException(String message) {
        super(message);
    }
}
