package com.solvd.buildingco.buildings;


import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.scheduling.Schedule;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface IEstimate {
    Order generateMaterialOrder();
    BigDecimal calculateMaterialCost();
    Schedule generateEmployeeSchedule(ZonedDateTime customerEndDate);
    BigDecimal calculateLaborCost(ZonedDateTime customerEndDate);
}
