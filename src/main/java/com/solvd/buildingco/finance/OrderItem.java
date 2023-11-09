package com.solvd.buildingco.finance;

import com.solvd.buildingco.exception.OrderItemTypeException;
import com.solvd.buildingco.inventory.BuyableItem;
import com.solvd.buildingco.inventory.Priceable;
import com.solvd.buildingco.inventory.RentableItem;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class OrderItem {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.finance");
    private Priceable item;
    private int quantity;
    private int monthsToRent;

    final static String NOT_BUYABLE_ITEM_MESSAGE = "Must be a buyable type of item.";
    final static String NOT_RENTABLE_ITEM_MESSAGE = "Must be a rentable type of item.";

    public OrderItem(Priceable item, int quantity) {
        if (!(item instanceof BuyableItem)) {
            LOGGER.warn(NOT_BUYABLE_ITEM_MESSAGE);
            throw new OrderItemTypeException(NOT_BUYABLE_ITEM_MESSAGE);
        }
        this.item = item;
        this.quantity = quantity;
    }

    public OrderItem(Priceable item, int quantity, int monthsToRent) {
        if (!(item instanceof RentableItem)) {
            LOGGER.warn(NOT_RENTABLE_ITEM_MESSAGE);
            throw new OrderItemTypeException(NOT_RENTABLE_ITEM_MESSAGE);
        }
        this.item = item;
        this.quantity = quantity;
        this.monthsToRent = monthsToRent;
    }


    // get price per unit and multiply by qty
    public BigDecimal getTotalPrice() {
        BigDecimal price = item.getPrice().multiply(new BigDecimal(quantity));

        if ((item instanceof RentableItem) && monthsToRent > 0) {
            price = price.multiply(new BigDecimal(monthsToRent));
        }

        return price;
    }

    // getters and setters

    public Priceable getItem() {
        return item;
    }

    public void setItem(Priceable item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className + "{");

        String[] fieldNames = {"item", "quantity"};

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
            if (fieldValue != null) {
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
