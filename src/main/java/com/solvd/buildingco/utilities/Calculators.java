package com.solvd.buildingco.utilities;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;

import java.math.BigDecimal;

public class Calculators {
    public static class OrderCalculator {
        public BigDecimal calculate(Order order) {
            BigDecimal totalPrice = new BigDecimal("0.00");
            for (OrderItem orderItem : order.getOrderItems()) {
                totalPrice = totalPrice.add(orderItem.getTotalPrice());
            }
            return totalPrice;
        }
    }

    public static class WageCalculator {

        public BigDecimal calculate(BigDecimal hourlyRate, long hoursWorked) {
            return hourlyRate.multiply(new BigDecimal(hoursWorked));
        }
    }

}

