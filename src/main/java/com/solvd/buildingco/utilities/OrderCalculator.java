package com.solvd.buildingco.utilities;

import com.solvd.buildingco.inventory.orders.Order;
import com.solvd.buildingco.inventory.orders.OrderItem;

import java.math.BigDecimal;

public class OrderCalculator {

    public BigDecimal calculate(Order order) {
        BigDecimal totalPrice = new BigDecimal("0.00");
        for (OrderItem orderItem : order.getOrderItems()) {
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
        }
        return totalPrice;
    }
}
