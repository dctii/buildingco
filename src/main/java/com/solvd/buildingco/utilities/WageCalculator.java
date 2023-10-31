package com.solvd.buildingco.utilities;

import java.math.BigDecimal;

public class WageCalculator {

    public BigDecimal calculate(BigDecimal hourlyRate, long hoursWorked) {
        return hourlyRate.multiply(new BigDecimal(hoursWorked));
    }
}
