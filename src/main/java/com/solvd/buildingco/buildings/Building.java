package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.utilities.StringFormatters;

import java.time.ZonedDateTime;

public abstract class Building<T extends Number> {

    // generate quantity of materials and units to order for construction
    private int constructionDays;

    public Building() {
        this.constructionDays = 0;
    }

    public Building(int constructionDays) {
        this.constructionDays = constructionDays;
    }

    public abstract Order generateMaterialOrder();

    // calculate cost of material order
    public abstract T calculateMaterialCost();

    // calculate the cost of labor for construction
    public abstract T calculateLaborCost(ZonedDateTime customerEndDate);



    public int getConstructionDays() {
        return constructionDays;
    }

    public void setConstructionDays(int constructionDays) {
        this.constructionDays = constructionDays;
    }

    @Override
    public String toString() {
        Class<?> currClass = Building.class;
        String[] fieldNames = {
                "constructionDays"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
