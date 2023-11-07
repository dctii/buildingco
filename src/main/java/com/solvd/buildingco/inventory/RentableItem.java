package com.solvd.buildingco.inventory;

import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;

public class RentableItem implements Priceable {
    private String name;
    private BigDecimal pricePerMonth;

    public RentableItem(String name, BigDecimal pricePerMonth) {
        this.name = name;
        this.pricePerMonth = pricePerMonth; // rental price per month
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    @Override
    public BigDecimal getPrice() {
        return getPricePerMonth();
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }


    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className + "{");

        String[] fieldNames = {"name", "pricePerMonth"};

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
