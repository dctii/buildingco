package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.IndustrialBuilding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class IndustrialBuildingMenu extends Menu {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.interactive");
    // ceiling values
    final static int MAX_SQUARE_FOOTAGE = 75000;
    final static int MAX_FLOORS = 4;

    @Override
    public void display() {
        LOGGER.info("Industrial Building Menu: ");
        LOGGER.info("[1] Create Industrial Building");
        LOGGER.info("[0] Back");
        LOGGER.info("Your choice: ");
    }


    @Override
    public IndustrialBuilding handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                return createIndustrialBuilding(scanner);
            case 0:
                return null;
            default:
                LOGGER.info("You cannot choose that");
                return null;
        }
    }

    // TODO: Store in a separate file, can prob pass in the Menu menu since HouseMenu and others
    //  are children of them
    public static IndustrialBuilding runMenu(Scanner scanner) {
        IndustrialBuildingMenu menu = new IndustrialBuildingMenu();
        IndustrialBuilding industrialBuilding = null;
        int choice;

        do {
            menu.display();
            choice = menu.getChoice(scanner);
            industrialBuilding = menu.handleChoice(choice, scanner);
            if (industrialBuilding == null && choice != 0) {
                LOGGER.info("Invalid choice. Please try again or choose [0] to go back.");
            }

        } while (industrialBuilding == null && choice != 0);

        return industrialBuilding;
    }

    private static IndustrialBuilding createIndustrialBuilding(Scanner scanner) {


        int squareFootage = 0;
        do {
            LOGGER.info("Enter the square footage for the Industrial Building (up to " + MAX_SQUARE_FOOTAGE + "): ");
            squareFootage = scanner.nextInt();
            if (squareFootage > MAX_SQUARE_FOOTAGE || squareFootage < 1) {
                LOGGER.info("Sorry, that's the ceiling value for square footage. Please " +
                        "try again.");
            }
        } while (squareFootage > MAX_SQUARE_FOOTAGE || squareFootage < 1);

        int numberOfFloors = 0;
        do {
            LOGGER.info("Enter the number of floors for the Industrial Building (up to " + MAX_FLOORS + "): ");
            numberOfFloors = scanner.nextInt();
            if (numberOfFloors > MAX_FLOORS || numberOfFloors < 1) {
                LOGGER.info("Sorry, that's above the ceiling value for number of floors. " +
                        "Please try again.");
            }
        } while (numberOfFloors > MAX_FLOORS || numberOfFloors < 1);

        return new IndustrialBuilding(squareFootage, numberOfFloors);
    }
}
