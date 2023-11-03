package com.solvd.buildingco.finance;

import java.math.BigDecimal;

// TODO: add category for annual rate
// TODO: add overtime rate consideration

public abstract class PayRate {
    protected BigDecimal rate;

    public PayRate(BigDecimal baseRate) {
        this.rate = baseRate;
    }

    // abstract class for calculating the pay
    public abstract BigDecimal calculatePay(int timeWorked);

    // getters and setters
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal baseRate) {
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
