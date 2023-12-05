package com.solvd.buildingco.inventory;

import com.solvd.buildingco.exception.InvalidItemTypeException;
import com.solvd.buildingco.exception.InventoryItemNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ItemRepository {
    private static final Logger LOGGER = LogManager.getLogger(ItemRepository.class);
    // initialize HashMap of the item name and its price
    private static final Map<String, Priceable<BigDecimal>> items = new HashMap<>();

    static {
        Stream.of(Item.values())
                .forEach(item -> loadItem(item));
    }

    private static void loadItem(Item item) {
        final String itemType = item.getItemType();

        if (Priceable.RENTABLE_TYPE.equals(itemType)) {
            items.put(
                    item.getName(),
                    new RentableItem<>(
                            item.getName(),
                            item.getPrice()
                    )
            );
        } else if (Priceable.BUYABLE_TYPE.equals(itemType)) {
            items.put(
                    item.getName(),
                    new BuyableItem<>(
                            item.getName(),
                            item.getPrice(),
                            item.getUnitMeasurement()
                    )
            );
        } else {
            final String INVALID_ITEM_TYPE_MESSAGE =
                    "Only 'rentable' or 'buyable' allowed.";
            LOGGER.warn(INVALID_ITEM_TYPE_MESSAGE);
            throw new InvalidItemTypeException(INVALID_ITEM_TYPE_MESSAGE);
        }
    }

    // retrieves item from ItemRepository by String name
    public static Priceable<BigDecimal> getItem(String name) {
        Priceable<BigDecimal> item = items.get(name);
        if (item == null) {
            final String ITEM_NOT_FOUND_LABEL = "Item not found: ";

            throw new InventoryItemNotFoundException(
                    ITEM_NOT_FOUND_LABEL + name
            );
        }
        return item;
    }

    private ItemRepository() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}


