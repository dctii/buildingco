package com.solvd.buildingco.utilities;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;

import java.util.ArrayList;

public class OrderUtils {
    public static Order loadOrder (ArrayList<OrderItem> orderItems){
        // initialize order
        Order order = ReflectionUtils.createObject(Order.class);

        // populate order with each item in orderItems
        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }

    private OrderUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
