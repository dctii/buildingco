package com.solvd.buildingco.finance;

import com.solvd.buildingco.utilities.StringFormatters;

import java.math.BigDecimal;

public class HourlyRate extends PayRate<BigDecimal> {
    private BigDecimal ratePerHour;

    public HourlyRate() {
        super();
    }

    public HourlyRate(BigDecimal ratePerHour) {
        super(ratePerHour);
        this.ratePerHour = ratePerHour;
    }

    // calculates wage, hours multiplied by rate
    @Override
    public BigDecimal calculatePay(int workedHours) {
        BigDecimal regularHours = new BigDecimal(workedHours);

        return regularHours.multiply(ratePerHour); // total wage to pay
    }

    // getters and setters
    public BigDecimal getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(BigDecimal ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public String toString() {
        Class<?> currClass = HourlyRate.class;
        String[] fieldNames = {"ratePerHour"};

        String parentToString = super.toString();
        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, parentToString,
                fieldsString);
    }
}
