package com.solvd.buildingco.stakeholders.employees;

import java.math.BigDecimal;

public enum Personnel {
    ARCHITECT(
            "Architect",
            "Architectural Design",
            "architects@",
            new BigDecimal("35.00")

    ),
    CONSTRUCTION_WORKER(
            "Construction Worker",
            "Construction Work",
            "workers@",
            new BigDecimal("15.00")),
    ENGINEER(
            "Engineer",
            "Engineering Work",
            "engineers@",
            new BigDecimal("30.00")),
    PROJECT_MANAGER(
            "Project Manager",
            "Project Management",
            "projectmanagers@",
            new BigDecimal("40.00"));

    private final String personnelType;
    private final String defaultActivityDescription;
    private final String departmentEmailHandle;
    private final BigDecimal averageRatePerHour;

    Personnel(String personnelType, String defaultActivityDescription,
              String departmentEmailHandle, BigDecimal averageRatePerHour) {
        this.personnelType = personnelType;
        this.defaultActivityDescription = defaultActivityDescription;
        this.departmentEmailHandle = departmentEmailHandle;
        this.averageRatePerHour = averageRatePerHour;
    }

    public String getPersonnelType() {
        return personnelType;
    }

    public String getDefaultActivityDescription() {
        return defaultActivityDescription;
    }

    public String getDepartmentEmailHandle() {
        return departmentEmailHandle;
    }

    public BigDecimal getAverageRatePerHour() {
        return averageRatePerHour;
    }
}
