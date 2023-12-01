package com.solvd.buildingco.utilities;

public enum TaxRate {
    LOS_ANGELES("Los Angeles", 0.095), // 9.5%
    SAN_DIEGO("San Diego", 0.0775), // 7.75%
    ORANGE("Orange", 0.0823), // 8.23%
    RIVERSIDE("Riverside", 0.0875), // 8.75%
    SAN_BERNADINO("San Bernadino", 0.0875)// 8.75%
    ;

    private final String countyName;
    private final double taxRate;

    TaxRate(String countyName, double taxRate) {
        this.countyName = countyName;
        this.taxRate = taxRate;
    }

    public String getCountyName() {
        return countyName;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
