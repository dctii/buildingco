package com.solvd.buildingco.buildings;


import com.solvd.buildingco.finance.Order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface IEstimate {
    Order generateMaterialOrder();

    BigDecimal calculateMaterialCost();


    BigDecimal calculateLaborCost(ZonedDateTime customerEndDate);
}
