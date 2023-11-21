package com.solvd.buildingco.utilities;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.finance.OrderItem;
import com.solvd.buildingco.inventory.ItemRepository;

import java.util.ArrayList;

public class OrderBuilder {
    private final ArrayList<OrderItem> orderItems = new ArrayList<>();

    public OrderBuilder addItem(String itemName, Integer quantity) {
        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(itemName),
                        quantity
                )
        );

        return this;
    }

    public OrderBuilder addItem(String itemName, Integer[] values) {
        if (values == null || values.length != 2) {
            throw new IllegalArgumentException("Integer[] array needs to contain exactly 2 items");
        }

        Integer quantity = values[0];
        Integer monthsToRent = values[1];

        orderItems.add(
                new OrderItem(
                        ItemRepository.getItem(itemName),
                        quantity,
                        monthsToRent)
        );

        return this;
    }

    public Order build() {
        return OrderUtils.loadOrder(orderItems);
    }
}
