package com.solvd.buildingco.finance;

// TODO: add category for annual rate
// TODO: add overtime rate consideration

import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class PayRate<T extends Number> {
    private T rate;

    public PayRate() {
    }

    public PayRate(T baseRate) {
        this.rate = baseRate;
    }

    // abstract class for calculating the pay
    public abstract T calculatePay(int timeWorked);

    // getters and setters
    public T getRate() {
        return rate;
    }

    public void setRate(T baseRate) {
        this.rate = baseRate;
    }


    @Override
    public String toString() {
        String[] fieldNames = {"rate"};

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
