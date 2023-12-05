package com.solvd.buildingco.utilities;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderUtils {

    public static Order loadOrder(ArrayList<OrderItem> orderItems) {
        // initialize order
        Order order = ReflectionUtils.createObject(Order.class);

        // populate order with each item in orderItems
        orderItems.forEach(orderItem -> order.addOrderItem(orderItem));

        return order;
    }

    public static BigDecimal sumItemCosts(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getTotalPrice())
                // (total, price) -> total.add(price)
                .reduce(BigDecimal.ZERO, BigDecimalUtils.ADD_OPERATION);
    }

    private OrderUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
