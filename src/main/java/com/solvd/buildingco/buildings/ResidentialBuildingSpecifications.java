package com.solvd.buildingco.buildings;

public enum ResidentialBuildingSpecifications {
    HOUSE(
            "House",
            1,
            8,
            1,
            8,
            0,
            4,
            0,
            1,
            550,
            30,
            100,
            3,
            9,
            15,
            12,
            0.5,
            3.0 / 12.0,
            375
    );


    private final String buildingType;
    private final int minNumRooms;
    private final int maxNumRooms;
    private final int minNumBathrooms;
    private final int maxNumBathrooms;
    /*
         min size of garage capacity, 0 will have to mean no garage, more than 1 will need logic
         elsewhere to say there is 1 garage

        for an example, see:
            com.solvd.buildingco.utilities.MaterialOrderGenerator.calculateElectricSuppliesQuantity

        max size of garage capacity, 4 means it can fit 4 cars
    */
    private final int minGarageCapacity;
    private final int maxGarageCapacity;
    private final int minNumGarages;
    private final int minNumKitchens;
    /*
            base square footage, e.g. if one room then assume a House has a base of 550 sq ft
            excluding the garage
        */
    private final int baseSquareFootage;
    private final int baseConstructionDays;
    // additional sq ft and days for each garage cap increment
    private final int extraSquareFootagePerCar;
    private final int extraConstructionDaysPerCar;
    // height of the rooms of the residential building
    private final int roomHeight;
    private final int averageRoomLength;
    private final int averageRoomWidth;
    // amount of wood used for each foot of a wall length
    private final double woodUsageFactorPerFoot;
    // thickness of insulation in feet
    private final double insulationThickness;
    // how much square footage is covered by one gallon of paint
    private final int paintCoverageBySquareFeet;


    ResidentialBuildingSpecifications(String buildingType, int minNumRooms, int maxNumRooms,
                                      int minNumBathrooms, int maxNumBathrooms,
                                      int minGarageCapacity, int maxGarageCapacity,
                                      int minNumGarages, int minNumKitchens,
                                      int baseSquareFootage, int baseConstructionDays,
                                      int extraSquareFootagePerCar,
                                      int extraConstructionDaysPerCar, int roomHeight,
                                      int averageRoomLength, int averageRoomWidth,
                                      double woodUsageFactorPerFoot, double insulationThickness,
                                      int paintCoverageBySquareFeet) {
        this.buildingType = buildingType;
        this.minNumRooms = minNumRooms;
        this.maxNumRooms = maxNumRooms;
        this.minNumKitchens = minNumKitchens;
        this.minNumBathrooms = minNumBathrooms;
        this.maxNumBathrooms = maxNumBathrooms;
        this.minNumGarages = minNumGarages;
        this.minGarageCapacity = minGarageCapacity;
        this.maxGarageCapacity = maxGarageCapacity;
        this.baseSquareFootage = baseSquareFootage;
        this.baseConstructionDays = baseConstructionDays;
        this.extraSquareFootagePerCar = extraSquareFootagePerCar;
        this.extraConstructionDaysPerCar = extraConstructionDaysPerCar;
        this.roomHeight = roomHeight;
        this.averageRoomLength = averageRoomLength;
        this.averageRoomWidth = averageRoomWidth;
        this.woodUsageFactorPerFoot = woodUsageFactorPerFoot;
        this.insulationThickness = insulationThickness;
        this.paintCoverageBySquareFeet = paintCoverageBySquareFeet;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public int getMinNumRooms() {
        return minNumRooms;
    }

    public int getMaxNumRooms() {
        return maxNumRooms;
    }

    public int getMinNumBathrooms() {
        return minNumBathrooms;
    }

    public int getMaxNumBathrooms() {
        return maxNumBathrooms;
    }


    public int getMinGarageCapacity() {
        return minGarageCapacity;
    }

    public int getMaxGarageCapacity() {
        return maxGarageCapacity;
    }

    public int getMinNumGarages() {
        return minNumGarages;
    }

    // min number of kitchens
    public int getMinNumKitchens() {
        return minNumKitchens;
    }


    public int getBaseSquareFootage() {
        return baseSquareFootage;
    }

    public int getBaseConstructionDays() {
        return baseConstructionDays;
    }

    // additional sq ft for each additional car, garage cap increment
    public int getExtraSquareFootagePerCar() {
        return extraSquareFootagePerCar;
    }

    public int getExtraConstructionDaysPerCar() {
        return extraConstructionDaysPerCar;
    }

    public int getRoomHeight() {
        return roomHeight;
    }

    public int getAverageRoomLength() {
        return averageRoomLength;
    }

    public int getAverageRoomWidth() {
        return averageRoomWidth;
    }

    public double getWoodUsageFactorPerFoot() {
        return woodUsageFactorPerFoot;
    }


    public double getInsulationThickness() {
        return insulationThickness;
    }


    public int getPaintCoverageBySquareFeet() {
        return paintCoverageBySquareFeet;
    }
}
