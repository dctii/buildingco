package com.solvd.buildingco.finance.payrates;

import java.math.BigDecimal;
import java.time.Year;

public class SalaryRate extends PayRate {
    private BigDecimal annualSalary;
    private int year;

    public SalaryRate(BigDecimal annualSalary) {
        super(annualSalary.divide(new BigDecimal(52)));
        this.annualSalary = annualSalary;
    }

    private static BigDecimal calculateWeeklyRate(BigDecimal annualSalary, int year) {
        return Year.isLeap(year)
                ? annualSalary.divide(new BigDecimal("52.29"))
                : annualSalary.divide(new BigDecimal(52));
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
        this.year = year;
        setRate(calculateWeeklyRate(annualSalary, year));
    }

    @Override
    public BigDecimal calculatePay(int workedHours, BigDecimal overtimeRate) {
        return getRate();
    }
}
