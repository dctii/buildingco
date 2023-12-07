package com.solvd.buildingco.finance;

import com.solvd.buildingco.exception.InventoryItemNotFoundException;
import com.solvd.buildingco.inventory.BuyableItem;
import com.solvd.buildingco.inventory.Priceable;
import com.solvd.buildingco.inventory.RentableItem;
import com.solvd.buildingco.utilities.OrderUtils;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private static final Logger LOGGER = LogManager.getLogger(Order.class);
    private final List<OrderItem> orderItems;

    // exception messages
    static final String NOT_BUYABLE_ITEM_MESSAGE = "Must be a buyable type of item.";
    static final String NOT_RENTABLE_ITEM_MESSAGE = "Must be a rentable type of item.";

    public Order() {
        this.orderItems = new LinkedList<>();
    }

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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
        return OrderUtils.sumItemCosts(orderItems);
    }

    @Override
    public String toString() {
        Class<?> currClass = Order.class;
        String[] fieldNames = {
                "orderItems"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }

}
