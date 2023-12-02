package com.solvd.buildingco.inventory;

import com.solvd.buildingco.exception.InvalidValueException;
import com.solvd.buildingco.exception.InvalidPriceException;
import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BuyableItem<T extends Number> implements Priceable<T> {
    private static final Logger LOGGER = LogManager.getLogger(BuyableItem.class);
    private String name;
    private T pricePerUnit; // price per unit
    private String unitMeasurement; // defined unit measurement

    // exception message
    final static String INVALID_PRICE_MESSAGE =
            "Price per unit cannot be less than or equal to zero.";
    final static String BLANK_NAME_MESSAGE = "The 'name' cannot be blank or empty.";

    public BuyableItem() {
    }

    public BuyableItem(String name) {
        if (StringUtils.isBlank(name)) {
            LOGGER.warn(BLANK_NAME_MESSAGE);
            throw new InvalidValueException(BLANK_NAME_MESSAGE);
        }

        this.name = name;
    }

    public BuyableItem(String name, T pricePerUnit, String unitMeasurement) {
        if (StringUtils.isBlank(name)) {
            LOGGER.warn(BLANK_NAME_MESSAGE);
            throw new InvalidValueException(BLANK_NAME_MESSAGE);
        }

        if (pricePerUnit instanceof BigDecimal && ((BigDecimal) pricePerUnit).compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.warn(INVALID_PRICE_MESSAGE);
            throw new InvalidPriceException(INVALID_PRICE_MESSAGE);
        }

        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.unitMeasurement = unitMeasurement;
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
        String[] fieldNames = {"name", "pricePerUnit", "unitMeasurement"};

        String className = this.getClass().getSimpleName();

        String result = Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(this, fieldName);
                    return fieldValue != null
                            ? StringFormatters.stateEquivalence(fieldName, fieldValue)
                            : StringConstants.EMPTY_STRING;
                })
                .filter(fieldValue -> !fieldValue.isEmpty())
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

        return className + StringFormatters.nestInCurlyBraces(result);
    }
}
