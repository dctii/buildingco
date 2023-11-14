package com.solvd.buildingco.inventory;

import com.solvd.buildingco.exception.InvalidPriceException;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class BuyableItem<T extends Number> implements Priceable<T> {
    private static final Logger LOGGER = LogManager.getLogger(BuyableItem.class);
    private String name;
    private T pricePerUnit;
    private String unitMeasurement;

    final static String INVALID_PRICE_MESSAGE =
            "Price per unit cannot be less than or equal to zero.";

    public BuyableItem(String name, T pricePerUnit, String unitMeasurement) {
        if (pricePerUnit instanceof BigDecimal && ((BigDecimal) pricePerUnit).compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.warn(INVALID_PRICE_MESSAGE);
            throw new InvalidPriceException(INVALID_PRICE_MESSAGE);
        }

        this.name = name;
        this.pricePerUnit = pricePerUnit; // price per unit
        this.unitMeasurement = unitMeasurement; // define unit measurement
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public T getPrice() {
        return getPricePerUnit();
    }

    public void setPricePerUnit(T pricePerUnit) {
        if (pricePerUnit instanceof BigDecimal && ((BigDecimal) pricePerUnit).compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.warn(INVALID_PRICE_MESSAGE);
            throw new InvalidPriceException(INVALID_PRICE_MESSAGE);
        }

        this.pricePerUnit = pricePerUnit;
    }


    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }


    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className + "{");

        String[] fieldNames = {"item", "quantity"};

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
