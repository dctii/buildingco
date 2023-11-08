package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.House;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class HouseMenu extends Menu {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.interactive");
    final static int MAX_NUM_ROOMS = 8;

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

    // TODO: Store in a separate file, can prob pass in the Menu menu since HouseMenu and others
    //  are children of them
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

    private static House createHouse(Scanner scanner) {
        int numRooms = 0;
        do {
            LOGGER.info("How many rooms would you like (up to 8)? ");
            numRooms = scanner.nextInt();
            if (numRooms > MAX_NUM_ROOMS || numRooms < 1) {
                LOGGER.info("Sorry, that's not a valid number of rooms. Please try again.");
            }
        } while (numRooms > MAX_NUM_ROOMS || numRooms < 1);

        // Dynamically calculates the ceiling for bathrooms. Can not have more bathrooms than rooms.
        int numBathrooms = 0;
        do {
            LOGGER.info("How many bathrooms would you like (up to " + numRooms + ")? ");
            numBathrooms = scanner.nextInt();
            if (numBathrooms > numRooms || numBathrooms < 1) {
                LOGGER.info("Sorry, that's not a valid number of bathrooms. Please try again.");
            }
        } while (numBathrooms > numRooms || numBathrooms < 1);

        // Cannot have more than four garage spaces. Also, cannot have more amount of car
        // spaces than rooms. If 2 rooms, then no more than 2 car spaces in garage capacity.
        int maxGarageCapacity = Math.min(4, numRooms);
        int garageCapacity = 0;
        do {
            LOGGER.info("What garage capacity would you like (up to " + maxGarageCapacity + ", number of cars)? ");
            garageCapacity = scanner.nextInt();
            if (garageCapacity > maxGarageCapacity || garageCapacity < 0) {
                LOGGER.info("Sorry, that's not a valid garage capacity. Please try again.");
            }
        } while (garageCapacity > maxGarageCapacity || garageCapacity < 0);

        return com.solvd.buildingco.buildings.House.createHouse(numRooms, numBathrooms, garageCapacity);
    }
}
