package com.solvd.buildingco.buildings;

public enum CommercialBuildingSpecifications {
    INDUSTRIAL_BUILDING(
            "industrial building",
            5000,
            75000,
            1,
            4,
            18.0,
            1000,
            6.0 / 12.0,
            1.5 / 1000,
            3.0 / 1000,
            2.0 / 1000,
            15.0 / 1000
    ),
    SKYSCRAPER(
            "skyscraper",
            10000,
            50000,
            15,
            100,
            9.0,
            40,
            1.0 / 12.0,
            1.5 / 1000,
            3.0 / 1000,
            10.0 / 1000,
            25.0 / 1000
    );

    private final String buildingType;
    private final int minSquareFootage;
    private final int maxSquareFootage;
    // refers to how many floors, stories, or levels
    private final int minLevels;
    private final int maxLevels;
    // height in feet for each level
    private final double heightPerLevel;
    // how frequently a steel beam is needed by a certain amount of square feet
    private final int squareFeetPerSteelBeam;
    // thickness of commercial building insulation in feet
    private final double insulationThickness;
    private final double architectsPerSquareFoot;
    private final double projectManagersPerSquareFoot;
    private final double engineersPerSquareFoot;
    private final double workersPerSquareFoot;

    CommercialBuildingSpecifications(String buildingType, int minSquareFootage,
                                     int maxSquareFootage, int minLevels, int maxLevels,
                                     double heightPerLevel, int squareFeetPerSteelBeam,
                                     double insulationThickness, double architectsPerSquareFoot,
                                     double projectManagersPerSquareFoot,
                                     double engineersPerSquareFoot,
                                     double workersPerSquareFoot
    ) {
        this.buildingType = buildingType;
        this.minSquareFootage = minSquareFootage;
        this.maxSquareFootage = maxSquareFootage;
        this.minLevels = minLevels;
        this.maxLevels = maxLevels;
        this.heightPerLevel = heightPerLevel;
        this.squareFeetPerSteelBeam = squareFeetPerSteelBeam;
        this.insulationThickness = insulationThickness;
        this.architectsPerSquareFoot = architectsPerSquareFoot;
        this.projectManagersPerSquareFoot = projectManagersPerSquareFoot;
        this.engineersPerSquareFoot = engineersPerSquareFoot;
        this.workersPerSquareFoot = workersPerSquareFoot;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public int getMinSquareFootage() {
        return minSquareFootage;
    }

    public int getMaxSquareFootage() {
        return maxSquareFootage;
    }

    public int getMinLevels() {
        return minLevels;
    }

    public int getMaxLevels() {
        return maxLevels;
    }

    public double getHeightPerLevel() {
        return heightPerLevel;
    }

    public int getSquareFeetPerSteelBeam() {
        return squareFeetPerSteelBeam;
    }

    public double getInsulationThickness() {
        return insulationThickness;
    }

    public double getArchitectsPerSquareFoot() {
        return architectsPerSquareFoot;
    }

    public double getProjectManagersPerSquareFoot() {
        return projectManagersPerSquareFoot;
    }

    public double getEngineersPerSquareFoot() {
        return engineersPerSquareFoot;
    }

    public double getWorkersPerSquareFoot() {
        return workersPerSquareFoot;
    }
}
