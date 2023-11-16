package com.solvd.buildingco.utilities;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;

import java.util.ArrayList;

public class OrderUtils {
    public static Order loadOrder (ArrayList<OrderItem> orderItems){
        // initialize order
        Order order = new Order();

        // populate order with each item in orderItems
        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }
}
