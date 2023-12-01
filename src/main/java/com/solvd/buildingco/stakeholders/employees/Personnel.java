package com.solvd.buildingco.stakeholders.employees;

public enum Personnel {
    ARCHITECT(
            "Architect",
            "Architectural Design",
            "architects@"
    ),
    CONSTRUCTION_WORKER(
            "Construction Worker",
            "Construction Work",
            "workers@"
    ),
    ENGINEER(
            "Engineer",
            "Engineering Work",
            "engineers@"
    ),
    PROJECT_MANAGER(
            "Project Manager",
            "Project Management",
            "projectmanagers@"
    );

    private final String personnelType;
    private final String defaultActivityDescription;
    private final String departmentEmailHandle;

    Personnel(String personnelType, String defaultActivityDescription,
              String departmentEmailHandle) {
        this.personnelType = personnelType;
        this.defaultActivityDescription = defaultActivityDescription;
        this.departmentEmailHandle = departmentEmailHandle;
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
}
