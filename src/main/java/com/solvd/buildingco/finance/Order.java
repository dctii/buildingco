package com.solvd.buildingco.finance;

import com.solvd.buildingco.exception.InventoryItemNotFoundException;
import com.solvd.buildingco.inventory.BuyableItem;
import com.solvd.buildingco.inventory.Priceable;
import com.solvd.buildingco.inventory.RentableItem;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class Order {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.finance");
    private List<OrderItem> orderItems;
    private int count;

    final static String NOT_BUYABLE_ITEM_MESSAGE = "Must be a buyable type of item.";
    final static String NOT_RENTABLE_ITEM_MESSAGE = "Must be a rentable type of item.";

    public Order() {
        this.orderItems = new LinkedList<>();
    }

    // chainable addOrderItem
    public Order addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        return this;
    }

    // overloaded method, allows for Item to be passed and quantity instead of having to pass in
    // an OrderItem
    public Order addOrderItem(Priceable item, int quantity) {
        if (!(item instanceof BuyableItem)) {
            LOGGER.warn(NOT_BUYABLE_ITEM_MESSAGE);
            throw new InventoryItemNotFoundException(NOT_BUYABLE_ITEM_MESSAGE);
        }
        return addOrderItem(new OrderItem(item, quantity));
    }

    public Order addOrderItem(Priceable item, int quantity, int monthsToRent) {
        if (!(item instanceof RentableItem)) {
            LOGGER.warn(NOT_RENTABLE_ITEM_MESSAGE);
            throw new InventoryItemNotFoundException(NOT_RENTABLE_ITEM_MESSAGE);
        }
        return addOrderItem(new OrderItem(item, quantity, monthsToRent));
    }

    // getters and setter
    public List<OrderItem> getOrderItems() {
        return new LinkedList<>(orderItems);
    }

    // calculates the total cost of the order
    public BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            BigDecimal lineTotal = item.getTotalPrice();
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
