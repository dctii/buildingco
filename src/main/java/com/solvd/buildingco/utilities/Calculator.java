package com.solvd.buildingco.utilities;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.finance.Order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Calculator {

    public static class BuildingCostCalculator {
        public static BigDecimal calculate(Building building, ZonedDateTime customerEndDate) {
            // get Order for materials
            Order materialOrder = building.generateMaterialOrder();
            // get total cost of Order
            BigDecimal materialCost = materialOrder.getTotalCost();
            // get labor cost of construction project
            BigDecimal laborCost = building.calculateLaborCost(customerEndDate);

            // sums up material and labor costs
            return materialCost.add(laborCost);
        }
    }

}

