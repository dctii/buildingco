package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.InvalidDimensionException;
import com.solvd.buildingco.exception.InvalidFloorNumberException;
import com.solvd.buildingco.exception.InvalidNumRoomsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.solvd.buildingco.buildings.BuildingConstants.*;

public class BuildingUtils {
    private static final Logger LOGGER = LogManager.getLogger(BuildingUtils.class);

    public static boolean hasInvalidNumberOfRooms(int numRooms) {
        return numRooms > HOUSE_MAX_NUM_ROOMS
                || numRooms < HOUSE_MIN_NUM_ROOMS;
    }

    public static void validateNumberOfRooms(int numRooms) {
        final String INVALID_NUM_ROOMS_MESSAGE =
                "Invalid number of rooms";
        if (hasInvalidNumberOfRooms(numRooms)) {
            LOGGER.warn(INVALID_NUM_ROOMS_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_ROOMS_MESSAGE);
        }
    }

    public static boolean hasInvalidNumberOfBathrooms(int numBathrooms, int numRooms) {
        return numBathrooms > numRooms
                || numBathrooms < HOUSE_MIN_NUM_BATHROOMS
                || numBathrooms > HOUSE_MAX_NUM_BATHROOMS;
    }

    public static void validateNumberOfBathrooms(int numBathrooms, int numRooms) {
        final String INVALID_NUM_BATHROOMS_MESSAGE =
                "Invalid number of bathrooms";
        if (hasInvalidNumberOfBathrooms(numBathrooms, numRooms)) {
            LOGGER.warn(INVALID_NUM_BATHROOMS_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_BATHROOMS_MESSAGE);
        }
    }

    public static boolean hasInvalidGarageCapacity(int garageCapacity, int numRooms) {
        return garageCapacity > numRooms
                || garageCapacity < HOUSE_MIN_NUM_GARAGE_CAP
                || garageCapacity > HOUSE_MAX_NUM_GARAGE_CAP;
    }

    public static void validateGarageCapacity(int garageCapacity, int numRooms) {
        final String INVALID_NUM_GARAGE_CAP_MESSAGE =
                "Invalid number for garage capacity";

        if (hasInvalidGarageCapacity(garageCapacity, numRooms)) {
            LOGGER.warn(INVALID_NUM_GARAGE_CAP_MESSAGE);
            throw new InvalidNumRoomsException(INVALID_NUM_GARAGE_CAP_MESSAGE);
        }
    }

    public static boolean hasInvalidSquareFootage(int squareFootage) {
        return squareFootage < INDUSTRIAL_MIN_SQUARE_FOOTAGE
                || squareFootage > INDUSTRIAL_MAX_SQUARE_FOOTAGE;
    }

    public static void validateSquareFootage(int squareFootage) {
        final String INVALID_DIMENSIONS_MESSAGE =
                "Invalid dimensions for IndustrialBuilding.";

        if (hasInvalidSquareFootage(squareFootage)) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
    }

    public static boolean hasInvalidNumberOfFloors(int numberOfFloors) {
        return numberOfFloors < INDUSTRIAL_MIN_FLOORS
                || numberOfFloors > INDUSTRIAL_MAX_FLOORS;
    }

    public static void validateNumberOfFloors(int numberOfFloors) {
        final String INVALID_NUM_FLOORS_MESSAGE =
                "Invalid number of floors for Industrial Building.";

        if (hasInvalidNumberOfFloors(numberOfFloors)) {
            LOGGER.warn(INVALID_NUM_FLOORS_MESSAGE);
            throw new InvalidFloorNumberException(INVALID_NUM_FLOORS_MESSAGE);
        }
    }


    public static boolean hasInvalidSquareFootagePerLevel(int squareFootagePerLevel) {
        return squareFootagePerLevel < SKYSCRAPER_MIN_SQUARE_FOOTAGE_PER_LEVEL
                || squareFootagePerLevel > SKYSCRAPER_MAX_SQUARE_FOOTAGE_PER_LEVEL;
    }

    public static void validateSquareFootagePerLevel(int squareFootagePerLevel) {
        final String INVALID_DIMENSIONS_MESSAGE =
                "Invalid dimensions for Skyscraper.";

        if (hasInvalidSquareFootagePerLevel(squareFootagePerLevel)) {
            LOGGER.warn(INVALID_DIMENSIONS_MESSAGE);
            throw new InvalidDimensionException(INVALID_DIMENSIONS_MESSAGE);
        }
    }

    public static boolean hasInvalidNumberOfLevels(int numberOfLevels) {
        return numberOfLevels < SKYSCRAPER_MIN_LEVELS
                || numberOfLevels > SKYSCRAPER_MAX_LEVELS;
    }

    public static void validateNumberOfLevels(int numberOfLevels) {
        final String INVALID_NUM_LEVELS_MESSAGE =
                "Invalid number of levels for Skyscraper.";

        if (hasInvalidNumberOfLevels(numberOfLevels)) {
            LOGGER.warn(INVALID_NUM_LEVELS_MESSAGE);
            throw new InvalidFloorNumberException(INVALID_NUM_LEVELS_MESSAGE);
        }
    }

    private BuildingUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }


}
