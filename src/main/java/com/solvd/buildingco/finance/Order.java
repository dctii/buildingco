package com.solvd.buildingco.finance;

import com.solvd.buildingco.inventory.Item;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        return this;
    }

    public Order addOrderItem(Item item, int quantity) {
        OrderItem orderItem = new OrderItem(item, quantity);
        this.orderItems.add(orderItem);
        return this;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
