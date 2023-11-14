package com.solvd.buildingco.finance;

// TODO: add category for annual rate
// TODO: add overtime rate consideration

public abstract class PayRate<T> {
    protected T rate;

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
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className + "{");

        builder.append("rate=")
                .append(rate.toString());

        builder.append("}");
        return builder.toString();
    }
}
