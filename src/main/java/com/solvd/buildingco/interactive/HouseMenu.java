package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.utilities.BuildingUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static com.solvd.buildingco.buildings.ResidentialBuildingSpecifications.HOUSE;

public class HouseMenu extends BuildingMenu {
    private static final Logger LOGGER = LogManager.getLogger(HouseMenu.class);

    @Override
    public void display() {
        LOGGER.info("House Menu: ");
        LOGGER.info("[1] Create House");
        LOGGER.info("[0] Back");
        LOGGER.info("Your choice: ");
    }

    @Override
    public House handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                return createHouse(scanner);
            case 0:
                return null;
            default:
                LOGGER.info("Invalid choice. Please try again.");
                return null;
        }
    }

    // executes menu for House
    public static House runMenu(Scanner scanner) {
        HouseMenu menu = new HouseMenu();
        House house = null;
        int choice;

        do {
            menu.display();
            choice = menu.getChoice(scanner);
            house = menu.handleChoice(choice, scanner);
            if (house == null && choice != 0) {
                LOGGER.info("Invalid choice. Please try again or choose [0] to go back.");
            }
        } while (house == null && choice != 0);

        return house;
    }

    // create House object with user input
    private static House createHouse(Scanner scanner) {
        int numRooms = 0;

        do {
            LOGGER.info(
                    "How many rooms would you like (up to {})? ",
                    HOUSE.getMaxNumRooms()
            );

            numRooms = scanner.nextInt();

            if (BuildingUtils.hasInvalidNumberOfRooms(numRooms)) {
                LOGGER.info("Sorry, that's not a valid number of rooms. Please try again.");
            }
        } while (BuildingUtils.hasInvalidNumberOfRooms(numRooms));

        // Dynamically calculates the ceiling for bathrooms. Can not have more bathrooms than rooms.
        int numBathrooms = 0;

        do {
            LOGGER.info(
                    "How many bathrooms would you like (up to {})? ",
                    numRooms
            );

            numBathrooms = scanner.nextInt();

            if (BuildingUtils.hasInvalidNumberOfBathrooms(numBathrooms, numRooms)) {
                LOGGER.info("Sorry, that's not a valid number of bathrooms. Please try again.");
            }
        } while (BuildingUtils.hasInvalidNumberOfBathrooms(numBathrooms, numRooms));

        // Cannot have more than four garage spaces. Also, cannot have more amount of car
        // spaces than rooms. If 2 rooms, then no more than 2 car spaces in garage capacity.
        int maxGarageCapacity = Math.min(HOUSE.getMaxGarageCapacity(), numRooms);
        int garageCapacity = 0;

        do {
            LOGGER.info(
                    "What garage capacity would you like (up to {}, number of cars)? ",
                    maxGarageCapacity
            );

            garageCapacity = scanner.nextInt();

            if (BuildingUtils.hasInvalidGarageCapacity(garageCapacity, maxGarageCapacity)) {
                LOGGER.info("Sorry, that's not a valid garage capacity. Please try again.");
            }
        } while (BuildingUtils.hasInvalidGarageCapacity(garageCapacity, maxGarageCapacity));

        return House.createHouse(numRooms, numBathrooms, garageCapacity);
    }
}
