package com.solvd.buildingco.buildings;

import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.stakeholders.employees.Employee;
import com.solvd.buildingco.utilities.ReflectionUtils;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Building<T extends Number> {

    // different employee types required for any (custom) construction project
    protected Employee worker;
    protected Employee engineer;
    protected Employee architect;
    protected Employee manager;

    public Building() {
        this.worker = null;
        this.engineer = null;
        this.architect = null;
        this.manager = null;
    }

    // generate quantity of materials and units to order for construction
    public abstract Order generateMaterialOrder();

    // calculate cost of material order
    public abstract T calculateMaterialCost();

    // calculate the cost of labor for construction
    public abstract T calculateLaborCost(ZonedDateTime customerEndDate);

    @Override
    public String toString() {
        String[] fieldNames = {"worker", "engineer", "architect", "manager"};

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
