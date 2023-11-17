package com.solvd.buildingco.inventory;

import com.solvd.buildingco.exception.InventoryItemNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemRepository {
    // static data for items available for order in inventory

    // initialize HashMap of the item name and its price
    private static final Map<String, Priceable<BigDecimal>> items = new HashMap<>();

    static {
        // Buyable Items to load into repository
        loadItem(ItemNames.CONCRETE, ItemPrices.CONCRETE, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.STRUCTURAL_WOOD, ItemPrices.STRUCTURAL_WOOD, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.ROOFING_HOUSE, ItemPrices.ROOFING_HOUSE, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.DRYWALL, ItemPrices.DRYWALL, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.INSULATION_MATERIALS, ItemPrices.INSULATION_MATERIALS, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.FLOORING, ItemPrices.FLOORING, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.PAINT, ItemPrices.PAINT, UnitMeasurement.GALLON);
        loadItem(ItemNames.PLUMBING_SUPPLIES, ItemPrices.PLUMBING_SUPPLIES, UnitMeasurement.UNIT);
        loadItem(ItemNames.ELECTRICAL_SUPPLIES_HOUSE, ItemPrices.ELECTRICAL_SUPPLIES_HOUSE, UnitMeasurement.UNIT);
        loadItem(ItemNames.STEEL_BEAMS, ItemPrices.STEEL_BEAMS, UnitMeasurement.UNIT);
        loadItem(ItemNames.STEEL_COLUMNS, ItemPrices.STEEL_COLUMNS, UnitMeasurement.UNIT);
        loadItem(ItemNames.CONCRETE_INDUSTRIAL, ItemPrices.CONCRETE_INDUSTRIAL, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.GLASS_INDUSTRIAL, ItemPrices.GLASS_INDUSTRIAL, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.ROOFING_INDUSTRIAL, ItemPrices.ROOFING_INDUSTRIAL, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.CLADDING_MATERIAL, ItemPrices.CLADDING_MATERIAL, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL, ItemPrices.ELECTRICAL_SUPPLIES_INDUSTRIAL,
                UnitMeasurement.UNIT);
        loadItem(ItemNames.HVAC_SUPPLIES, ItemPrices.HVAC_SUPPLIES, UnitMeasurement.UNIT);
        loadItem(ItemNames.INTERIOR_FINISHING_MATERIALS, ItemPrices.INTERIOR_FINISHING_MATERIALS, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.STEEL_BEAMS_HIGH_GRADE, ItemPrices.STEEL_BEAMS_HIGH_GRADE,
                UnitMeasurement.UNIT);
        loadItem(ItemNames.CONCRETE_HIGH_GRADE, ItemPrices.CONCRETE_HIGH_GRADE, UnitMeasurement.SQUARE_FOOT);
        loadItem(ItemNames.GLASS_HIGH_GRADE_INDUSTRIAL, ItemPrices.GLASS_HIGH_GRADE_INDUSTRIAL, UnitMeasurement.SQUARE_FOOT);


        // Rentable Items to load into repository
        loadItem(ItemNames.CONCRETE_MIXER, ItemPrices.CONCRETE_MIXER, UnitMeasurement.UNIT,
                Priceable.RENTABLE_TYPE);
        loadItem(ItemNames.FRONT_LOADER_TRUCK, ItemPrices.FRONT_LOADER_TRUCK, UnitMeasurement.UNIT,
                Priceable.RENTABLE_TYPE);
        loadItem(ItemNames.TOWER_CRANE, ItemPrices.TOWER_CRANE, UnitMeasurement.UNIT,
                Priceable.RENTABLE_TYPE);

    }

    // loadItem functions that load items into repository
    private static void loadItem(String name, String pricePer, String unitMeasurement) {
        // if no itemType is passed in, then just assume we want to load a BuyableItem
        loadItem(
                name,
                pricePer,
                unitMeasurement,
                Priceable.BUYABLE_TYPE
        );
    }

    private static void loadItem(String name, String pricePer, String unitMeasurement, String itemType) {
        if (Priceable.RENTABLE_TYPE.equalsIgnoreCase(itemType)) {
            // if denoted as "rentable", then load a RentableItem into the repository
            items.put(
                    name,
                    new RentableItem<>(
                            name,

                            new BigDecimal(pricePer)
                    )
            );
        } else {
            // if not denoted as "rentable", then load a BuyableItem into the repository
            items.put(
                    name,
                    new BuyableItem<>(
                            name,
                            new BigDecimal(pricePer),
                            unitMeasurement
                    )
            );
        }
    }

    // retrieves item from ItemRepository by String name
    public static Priceable<BigDecimal> getItem(String name) {
        Priceable<BigDecimal> item = items.get(name);
        if (item == null) {
            final String ITEM_NOT_FOUND_PREFIX = "Item not found: ";

            throw new InventoryItemNotFoundException(
                    ITEM_NOT_FOUND_PREFIX + name
            );
        }
        return item;
    }
}


