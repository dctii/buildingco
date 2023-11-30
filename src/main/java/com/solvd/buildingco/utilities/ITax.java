package com.solvd.buildingco.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

@FunctionalInterface
public interface ITax {
    BigDecimal calculateTax(BigDecimal amount);
    /*
        "Can @FunctionalInterfaces have default methods?"
        https://stackoverflow.com/questions/30165060/can-functionalinterfaces-have-default-methods
    */
    static BigDecimal calculateLosAngelesTax(BigDecimal amount) {
        final double LA_TAX_RATE = 0.095;

        return BigDecimalUtils
                .multiply(amount, LA_TAX_RATE)
                .setScale(2, RoundingMode.UP);
    }
}
