package com.solvd.buildingco.inventory;

import com.solvd.buildingco.exception.InvalidPriceException;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class RentableItem implements Priceable {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.inventory");
    private String name;
    private BigDecimal pricePerMonth;

    final static String INVALID_PRICE_MESSAGE =
            "Price per month cannot be less than or equal to zero.";

    public RentableItem(String name, BigDecimal pricePerMonth) {
        if (pricePerMonth.compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.warn(INVALID_PRICE_MESSAGE);
            throw new InvalidPriceException(INVALID_PRICE_MESSAGE);
        }

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
        if (pricePerMonth.compareTo(BigDecimal.ZERO) < 0) {
            LOGGER.warn(INVALID_PRICE_MESSAGE);
            throw new InvalidPriceException(INVALID_PRICE_MESSAGE);
        }
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
