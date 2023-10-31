package com.solvd.buildingco.finance.payrates;

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

    @Override
    public BigDecimal calculatePay(int workedHours, BigDecimal overtimeRate) {
        BigDecimal regularHours = new BigDecimal(Math.min(workedHours, 40));
        BigDecimal overtimeHours = new BigDecimal(Math.max(workedHours - 40, 0));

        BigDecimal regularPay = regularHours.multiply(ratePerHour);
        BigDecimal overtimePay = overtimeHours.multiply(ratePerHour).multiply(overtimeRate);

        return regularPay.add(overtimePay);
    }
}
