package com.solvd.buildingco.finance;

import com.solvd.buildingco.finance.payrates.PayRate;

import java.util.ArrayList;
import java.util.List;

public abstract class PayStructure {
    protected List<PayRate> payRates;

    public PayStructure() {
        this.payRates = new ArrayList<>();
    }

    public void addPayRate(PayRate payRate) {
        this.payRates.add(payRate);
    }

    public abstract double calculateTotalPay(int workedHours, double overtimeRate);
}
