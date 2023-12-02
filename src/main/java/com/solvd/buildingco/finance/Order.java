package com.solvd.buildingco.finance;

import com.solvd.buildingco.exception.InventoryItemNotFoundException;
import com.solvd.buildingco.inventory.BuyableItem;
import com.solvd.buildingco.inventory.Priceable;
import com.solvd.buildingco.inventory.RentableItem;
import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Order {
    private static final Logger LOGGER = LogManager.getLogger(Order.class);
    private List<OrderItem> orderItems;

    // exception messages
    final static String NOT_BUYABLE_ITEM_MESSAGE = "Must be a buyable type of item.";
    final static String NOT_RENTABLE_ITEM_MESSAGE = "Must be a rentable type of item.";

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
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            BigDecimal lineTotal = item.getTotalPrice();
            totalCost = totalCost.add(lineTotal);
        }
        return totalCost;
    }

    @Override
    public String toString() {

        String[] fieldNames = {"orderItems"};

        String className = this.getClass().getSimpleName();

        String result = Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(this, fieldName);
                    return fieldValue != null
                            ? StringFormatters.stateEquivalence(
                            fieldName,
                            StringFormatters.listToString((List<?>) fieldValue)
                            )
                            : StringConstants.EMPTY_STRING;
                })
                .filter(fieldValue -> !fieldValue.isEmpty())
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

        return className + StringFormatters.nestInCurlyBraces(result);
    }


}
