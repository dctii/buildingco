package com.solvd.buildingco.finance;

import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;

public class HourlyRate<T> extends PayRate<BigDecimal> {
    private BigDecimal ratePerHour;

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
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(super.toString()); // Start with the PayRate's toString information

        // Append HourlyRate-specific field information
        String[] fieldNames = {"ratePerHour"};

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
            if (fieldValue != null) {
                builder.append(", ")
                        .append(fieldName)
                        .append("=")
                        .append(fieldValue);
            }
        }

        builder.append("}");

        int startIndex = builder.indexOf("PayRate{") + "PayRate".length();
        builder.replace(startIndex, startIndex + 1, className + "{");

        return builder.toString();
    }


}
