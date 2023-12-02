package com.solvd.buildingco.finance;

import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HourlyRate extends PayRate<BigDecimal> {
    private BigDecimal ratePerHour;

    public HourlyRate() {
        super();
    }

    public HourlyRate(BigDecimal ratePerHour) {
        super(ratePerHour);
        this.ratePerHour = ratePerHour;
    }

    // calculates wage, hours multiplied by rate
    @Override
    public BigDecimal calculatePay(int workedHours) {
        BigDecimal regularHours = new BigDecimal(workedHours);

        return regularHours.multiply(ratePerHour); // total wage to pay
    }

    // getters and setters
    public BigDecimal getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(BigDecimal ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public String toString() {
        String[] fieldNames = {"ratePerHour"};

        String className = this.getClass().getSimpleName();
        String superResult = StringFormatters.removeEdges(super.toString());

        String result = Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(this, fieldName);
                    return fieldValue != null
                            ? StringFormatters.stateEquivalence(fieldName, fieldValue)
                            : StringConstants.EMPTY_STRING;
                })
                .filter(fieldValue -> !fieldValue.isEmpty())
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

        return className
                + StringFormatters.nestInCurlyBraces(
                superResult + StringConstants.COMMA_DELIMITER + result
        );
    }


}
