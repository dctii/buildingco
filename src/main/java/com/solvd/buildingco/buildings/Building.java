package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.stakeholders.employees.Employee;
import com.solvd.buildingco.utilities.FieldUtils;

import java.time.ZonedDateTime;

public abstract class Building<T extends Number> {

    // different employee types required for any (custom) construction project
    protected Employee worker;
    protected Employee engineer;
    protected Employee architect;
    protected Employee manager;

    public Building() {
        this.worker = null;
        this.engineer = null;
        this.architect = null;
        this.manager = null;
    }

    // for building materials
    public abstract Order generateMaterialOrder();

    public abstract T calculateMaterialCost();


    public abstract T calculateLaborCost(ZonedDateTime customerEndDate);

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className + "{");

        String[] fieldNames = {"worker", "engineer", "architect", "manager"};

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
            if (fieldValue != null) {
                builder
                        .append(fieldName)
                        .append("=")
                        .append(fieldValue)
                        .append(", ");
            }
        }

        if (builder.length() > className.length() + 1) {
            builder.setLength(builder.length() - 2);
        }

        builder.append("}");

        return builder.toString();
    }

}
