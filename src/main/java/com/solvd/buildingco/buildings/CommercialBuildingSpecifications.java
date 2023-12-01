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
            6.0 / 12.0
    ),
    SKYSCRAPER(
            "skyscraper",
            10000,
            50000,
            15,
            100,
            9.0,
            40,
            1.0 / 12.0
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

    CommercialBuildingSpecifications(String buildingType, int minSquareFootage, int maxSquareFootage,
                                     int minLevels, int maxLevels, double heightPerLevel,
                                     int squareFeetPerSteelBeam, double insulationThickness) {
        this.buildingType = buildingType;
        this.minSquareFootage = minSquareFootage;
        this.maxSquareFootage = maxSquareFootage;
        this.minLevels = minLevels;
        this.maxLevels = maxLevels;
        this.heightPerLevel = heightPerLevel;
        this.squareFeetPerSteelBeam = squareFeetPerSteelBeam;
        this.insulationThickness = insulationThickness;
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
}
