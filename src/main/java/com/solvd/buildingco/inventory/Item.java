package com.solvd.buildingco.inventory;

import java.math.BigDecimal;

import static com.solvd.buildingco.inventory.UnitMeasurement.*;

public enum Item {
    CONCRETE("Concrete",
            "3.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    STRUCTURAL_WOOD(
            "Structural Wood",
            "5.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    ROOFING_HOUSE(
            "House Roofing",
            "10.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    DRYWALL(
            "Drywall",
            "2.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    INSULATION_MATERIALS(
            "Insulation Materials",
            "3.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    FLOORING(
            "Flooring",
            "20.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    PAINT(
            "Paint",
            "25.00",
            GALLON.getName(),
            Priceable.BUYABLE_TYPE
    ),
    PLUMBING_SUPPLIES(
            "Plumbing Supplies",
            "500.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    ELECTRICAL_SUPPLIES_HOUSE(
            "House Electrical Supplies",
            "300.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    STEEL_BEAMS(
            "Steel Beams",
            "15.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    STEEL_COLUMNS(
            "Steel Columns",
            "20.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    CONCRETE_INDUSTRIAL(
            "Industrial Concrete",
            "5.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    GLASS_INDUSTRIAL(
            "Industrial Glass",
            "50.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    ROOFING_INDUSTRIAL(
            "Industrial Roofing",
            "10.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    CLADDING_MATERIAL(
            "Cladding Material",
            "25.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    ELECTRICAL_SUPPLIES_INDUSTRIAL(
            "Industrial Electrical Supplies",
            "2000.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    HVAC_SUPPLIES(
            "HVAC Supplies",
            "1000.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    INTERIOR_FINISHING_MATERIALS(
            "Interior Finishing Materials",
            "25.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    STEEL_BEAMS_HIGH_GRADE(
            "High Grade Steel Beams",
            "200.00",
            UNIT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    CONCRETE_HIGH_GRADE(
            "High Grade Concrete",
            "7.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    GLASS_HIGH_GRADE_INDUSTRIAL(
            "High Grade Industrial Glass",
            "100.00",
            SQUARE_FOOT.getName(),
            Priceable.BUYABLE_TYPE
    ),
    CONCRETE_MIXER(
            "Concrete Mixer",
            "800.00",
            UNIT.getName(),
            Priceable.RENTABLE_TYPE
    ),
    FRONT_LOADER_TRUCK(
            "Front Loader Truck",
            "3800.00",
            UNIT.getName(),
            Priceable.RENTABLE_TYPE
    ),
    TOWER_CRANE(
            "Tower Crane",
            "15000.00",
            UNIT.getName(),
            Priceable.RENTABLE_TYPE
    );

    private final String name;
    private final BigDecimal price;
    private final String unitMeasurement;
    private final String itemType;

    Item(String name, String price, String unitMeasurement, String itemType) {
        this.name = name;
        this.price = new BigDecimal(price);
        this.unitMeasurement = unitMeasurement;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public String getItemType() {
        return itemType;
    }
    
}
