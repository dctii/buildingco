package com.solvd.buildingco.finance;

import java.math.BigDecimal;

public abstract class PayRate {
    protected BigDecimal rate;

    public PayRate(BigDecimal baseRate) {
        this.rate = baseRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal baseRate) {
        this.rate = baseRate;
    }

    public abstract BigDecimal calculatePay(int timeWorked);
}
