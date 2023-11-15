package com.solvd.buildingco.inventory;

public interface Priceable<T extends Number> {
    T getPrice();

    String getName();

    String RENTABLE_TYPE = "rentable";
    String BUYABLE_TYPE = "buyable";
}
