package com.solvd.buildingco.finance;

import java.math.BigDecimal;

public class HourlyRate extends PayRate{
    private BigDecimal ratePerHour;

    public HourlyRate(BigDecimal ratePerHour) {
        super(ratePerHour);
        this.ratePerHour = ratePerHour;
    }

    public BigDecimal getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(BigDecimal ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public BigDecimal calculatePay(int workedHours) {
        BigDecimal regularHours = new BigDecimal(Math.min(workedHours, 40));

        BigDecimal regularPay = regularHours.multiply(ratePerHour);

        return regularPay;
    }
}
