package com.solvd.buildingco.finance;

import com.solvd.buildingco.utilities.StringFormatters;

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
        Class<?> currClass = PayRate.class;
        String[] fieldNames = {
                "rate"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
