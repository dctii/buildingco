package com.solvd.buildingco.inventory.orders;

import com.solvd.buildingco.inventory.items.Item;

import java.math.BigDecimal;

public class OrderItem {
    private Item item;
    private int quantity;

    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return item.getPricePerUnit().multiply(new BigDecimal(quantity));
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
