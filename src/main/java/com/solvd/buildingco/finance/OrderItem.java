package com.solvd.buildingco.finance;

import com.solvd.buildingco.inventory.Priceable;
import com.solvd.buildingco.utilities.FieldUtils;

import java.math.BigDecimal;

public class OrderItem {
    private Priceable item;
    private int quantity;

    public OrderItem(Priceable item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    // get price per unit and multiply by qty
    public BigDecimal getTotalPrice() {
        return item.getPrice().multiply(new BigDecimal(quantity));
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
