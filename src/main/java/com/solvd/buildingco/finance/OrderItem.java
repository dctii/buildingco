package com.solvd.buildingco.finance;

import com.solvd.buildingco.exception.InventoryItemNotFoundException;
import com.solvd.buildingco.inventory.BuyableItem;
import com.solvd.buildingco.inventory.Priceable;
import com.solvd.buildingco.inventory.RentableItem;
import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.BigDecimalUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

public class OrderItem {
    private static final Logger LOGGER = LogManager.getLogger(OrderItem.class);
    private Priceable item; // item
    private int quantity; // amount of items to buy or rent
    private int monthsToRent; // amount of months to rent RentableItem

    // exception messages
    final static String NOT_BUYABLE_ITEM_MESSAGE = "Must be a buyable type of item.";
    final static String NOT_RENTABLE_ITEM_MESSAGE = "Must be a rentable type of item.";

    public OrderItem() {
    }

    public OrderItem(Priceable<BigDecimal> item, Number quantity) {
        if (!(item instanceof BuyableItem)) {
            LOGGER.warn(NOT_BUYABLE_ITEM_MESSAGE);
            throw new InventoryItemNotFoundException(NOT_BUYABLE_ITEM_MESSAGE);
        }
        this.item = item;
        this.quantity = BigDecimalUtils.roundToInt(quantity);
    }

    public OrderItem(Priceable<BigDecimal> item, Number quantity, int monthsToRent) {
        if (!(item instanceof RentableItem)) {
            LOGGER.warn(NOT_RENTABLE_ITEM_MESSAGE);
            throw new InventoryItemNotFoundException(NOT_RENTABLE_ITEM_MESSAGE);
        }
        this.item = item;
        this.quantity = BigDecimalUtils.roundToInt(quantity);
        this.monthsToRent = monthsToRent;
    }


    // get price per unit and multiply by qty
    public BigDecimal getTotalPrice() {
        BigDecimal price = ((BigDecimal) item.getPrice()).multiply(new BigDecimal(quantity));

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

    public void setQuantity(Number quantity) {
        this.quantity = BigDecimalUtils.roundToInt(quantity);
    }

    @Override
    public String toString() {
        String[] fieldNames = {"item", "quantity"};

        String className = this.getClass().getSimpleName();

        String result = Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(this, fieldName);
                    return fieldValue != null
                            ? StringFormatters.stateEquivalence(fieldName, fieldValue)
                            : StringConstants.EMPTY_STRING;
                })
                .filter(fieldValue -> !fieldValue.isEmpty())
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

        return className + StringFormatters.nestInCurlyBraces(result);
    }

}
