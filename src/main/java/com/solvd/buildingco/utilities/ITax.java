package com.solvd.buildingco.utilities;

import java.math.BigDecimal;

@FunctionalInterface
public interface ITax {
    BigDecimal calculateTax(BigDecimal amount);
}
