package com.solvd.buildingco.inventory;

import com.solvd.buildingco.exception.InventoryItemNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemRepository {
    private static final Map<String, Priceable<BigDecimal>> items = new HashMap<>();

    private final static String UM_SQUARE_FOOT = "square foot";
    private final static String UM_GALLON = "gallon";
    private final static String UM_TON = "ton";
    private final static String UM_CUBIC_METER = "cubic meter";
    private final static String UM_SQUARE_METER = "square meter";

    private final static String UM_UNIT = "unit";
    private final static String RENTABLE_TYPE = "rentable";
    private final static String BUYABLE_TYPE = "buyable";

    static {
        // Buyable Items
        loadItem(ItemNames.CONCRETE, ItemPrices.CONCRETE, UM_SQUARE_FOOT);
        loadItem(ItemNames.STRUCTURAL_WOOD, ItemPrices.STRUCTURAL_WOOD, UM_SQUARE_FOOT);
        loadItem(ItemNames.ROOFING_HOUSE, ItemPrices.ROOFING_HOUSE, UM_SQUARE_FOOT);
        loadItem(ItemNames.DRYWALL, ItemPrices.DRYWALL, UM_SQUARE_FOOT);
        loadItem(ItemNames.INSULATION_MATERIALS, ItemPrices.INSULATION_MATERIALS, UM_SQUARE_FOOT);
        loadItem(ItemNames.FLOORING, ItemPrices.FLOORING, UM_SQUARE_FOOT);
        loadItem(ItemNames.PAINT, ItemPrices.PAINT, UM_GALLON);
        loadItem(ItemNames.PLUMBING_SUPPLIES, ItemPrices.PLUMBING_SUPPLIES, UM_UNIT);
        loadItem(ItemNames.ELECTRICAL_SUPPLIES_HOUSE, ItemPrices.ELECTRICAL_SUPPLIES_HOUSE, UM_UNIT);
        loadItem(ItemNames.STEEL_BEAMS, ItemPrices.STEEL_BEAMS, UM_TON);
        loadItem(ItemNames.STEEL_COLUMNS, ItemPrices.STEEL_COLUMNS, UM_TON);
        loadItem(ItemNames.CONCRETE_INDUSTRIAL, ItemPrices.CONCRETE_INDUSTRIAL, UM_CUBIC_METER);
        loadItem(ItemNames.GLASS_INDUSTRIAL, ItemPrices.GLASS_INDUSTRIAL, UM_SQUARE_METER);
        loadItem(ItemNames.ROOFING_INDUSTRIAL, ItemPrices.ROOFING_INDUSTRIAL, UM_SQUARE_METER);
        loadItem(ItemNames.CLADDING_MATERIAL, ItemPrices.CLADDING_MATERIAL, UM_SQUARE_METER);
        loadItem(ItemNames.ELECTRICAL_SUPPLIES_INDUSTRIAL, ItemPrices.ELECTRICAL_SUPPLIES_INDUSTRIAL,
                UM_UNIT);
        loadItem(ItemNames.HVAC_SUPPLIES, ItemPrices.HVAC_SUPPLIES, UM_UNIT);
        loadItem(ItemNames.INTERIOR_FINISHING_MATERIALS, ItemPrices.INTERIOR_FINISHING_MATERIALS, UM_SQUARE_FOOT);
        loadItem(ItemNames.STEEL_BEAMS_HIGH_GRADE, ItemPrices.STEEL_BEAMS_HIGH_GRADE, UM_TON);
        loadItem(ItemNames.CONCRETE_HIGH_GRADE, ItemPrices.CONCRETE_HIGH_GRADE, UM_SQUARE_FOOT);
        loadItem(ItemNames.GLASS_HIGH_GRADE_INDUSTRIAL, ItemPrices.GLASS_HIGH_GRADE_INDUSTRIAL, UM_SQUARE_FOOT);


        // Rentable Items
        loadItem(ItemNames.CONCRETE_MIXER, ItemPrices.CONCRETE_MIXER, UM_UNIT, RENTABLE_TYPE);
        loadItem(ItemNames.FRONT_LOADER_TRUCK, ItemPrices.FRONT_LOADER_TRUCK, UM_UNIT, RENTABLE_TYPE);
        loadItem(ItemNames.TOWER_CRANE, ItemPrices.TOWER_CRANE, UM_UNIT, RENTABLE_TYPE);

    }

    private static void loadItem(String name, String pricePer, String unitMeasurement) {
        loadItem(name, pricePer, unitMeasurement, BUYABLE_TYPE);
    }

    private static void loadItem(String name, String pricePer, String unitMeasurement, String itemType) {
        if (RENTABLE_TYPE.equalsIgnoreCase(itemType)) {
            items.put(name, new RentableItem<>(name, new BigDecimal(pricePer)));
        } else {
            items.put(name, new BuyableItem<>(name, new BigDecimal(pricePer), unitMeasurement));
        }
    }

    public static Priceable<BigDecimal> getItem(String name) {
        Priceable<BigDecimal> item = items.get(name);
        if (item == null) {
            throw new InventoryItemNotFoundException("Item not found: " + name);
        }
        return item;
    }
}


