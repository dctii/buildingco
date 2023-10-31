package com.solvd.buildingco.inventory;

import java.math.BigDecimal;

public class Item {
    private String name;
    private BigDecimal pricePerUnit;
    private String unitMeasurement;
    private String provider;

    public Item(String name, BigDecimal pricePerUnit, String unitMeasurement, String provider) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.unitMeasurement = unitMeasurement;
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public String getProviderId() {
        return provider;
    }

    public void setProviderId(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", unitMeasurement='" + unitMeasurement + '\'' +
                ", providerId='" + provider + '\'' +
                '}';
    }
}
