package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.StringFormatters;

import java.time.ZonedDateTime;

public abstract class Building<T extends Number> {

    // generate quantity of materials and units to order for construction
    public abstract Order generateMaterialOrder();

    // calculate cost of material order
    public abstract T calculateMaterialCost();

    // calculate the cost of labor for construction
    public abstract T calculateLaborCost(ZonedDateTime customerEndDate);

    @Override
    public String toString() {
        Class<?> currClass = Building.class;

        return StringFormatters.buildToString(currClass);
    }
}
