package com.solvd.buildingco.finance;

import com.solvd.buildingco.inventory.Item;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;
import java.util.Arrays;

public class Order {
    private OrderItem[] orderItems;
    private int count;

    public Order() {
        this.orderItems = new OrderItem[10];
        this.count = 0;
    }

    // chainable addOrderItem
    public Order addOrderItem(OrderItem orderItem) {
        // if size is different, then resize
        if (count == orderItems.length) {
            // expand array size so there can be more items
            OrderItem[] newItems = new OrderItem[orderItems.length * 2];
            // copy previous items into new array
            System.arraycopy(orderItems, 0, newItems, 0, orderItems.length);
            orderItems = newItems;
        }
        // add new item
        orderItems[count] = orderItem;
        // increase item count
        count++;

        return this; // enables chaining by returning Order.
    }

    // overloaded method, allows for Item to be passed and quantity instead of having to pass in
    // an OrderItem
    public Order addOrderItem(Item item, int quantity) {
        return addOrderItem(new OrderItem(item, quantity));
    }

    // getters and setter
    public OrderItem[] getOrderItems() {
        // returns array without any
        return Arrays.copyOf(orderItems, count);
    }

    // calculates the total cost of the order
    public BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        // loop over the items of the Order and update the total material cost
        for (int i = 0; i < count; i++) {
            BigDecimal lineTotal = orderItems[i].getTotalPrice();
            totalCost = totalCost.add(lineTotal);
        }
        return totalCost;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className + "{");

        // Append fields using FieldUtils
        String[] fieldNames = {"orderItems", "count"};

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
            if (fieldValue != null) {
                if (fieldValue instanceof Object[] && "orderItems".equals(fieldName)) {
                    fieldValue = Arrays.toString(Arrays.copyOf((OrderItem[]) fieldValue, count));
                }
                builder
                        .append(fieldName)
                        .append("=")
                        .append(fieldValue)
                        .append(", ");
            }
        }

        if (builder.length() > className.length() + 1) {
            builder.setLength(builder.length() - 2);
        }

        builder.append("}");

        return builder.toString();
    }
}
