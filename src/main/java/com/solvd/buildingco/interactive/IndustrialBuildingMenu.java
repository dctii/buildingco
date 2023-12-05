package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.utilities.BuildingUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.INDUSTRIAL_BUILDING;

public class IndustrialBuildingMenu extends BuildingMenu {
    private static final Logger LOGGER = LogManager.getLogger(IndustrialBuildingMenu.class);
    // ceiling values


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

    // executes menu for IndustrialBuilding
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

    // create IndustrialBuilding object with user input

    private static IndustrialBuilding createIndustrialBuilding(Scanner scanner) {
        int squareFootage = 0;

        do {
            LOGGER.info(
                    "Enter the square footage for the Industrial Building ({}-{}): ",
                    INDUSTRIAL_BUILDING.getMinSquareFootage(),
                    INDUSTRIAL_BUILDING.getMaxSquareFootage()
            );

            squareFootage = scanner.nextInt();
            if (BuildingUtils.hasInvalidSquareFootage(squareFootage)) {
                LOGGER.warn(
                        "Square footage must be between {} and {}. Please try again.",
                        INDUSTRIAL_BUILDING.getMinSquareFootage(),
                        INDUSTRIAL_BUILDING.getMaxSquareFootage()
                );
            }
        } while (BuildingUtils.hasInvalidSquareFootage(squareFootage));

        int numberOfFloors = 0;

        do {
            LOGGER.info(
                    "Enter the number of floors for the Industrial Building ({}-{}): ",
                    INDUSTRIAL_BUILDING.getMinLevels(),
                    INDUSTRIAL_BUILDING.getMaxLevels()
            );

            numberOfFloors = scanner.nextInt();

            if (BuildingUtils.hasInvalidNumberOfFloors(numberOfFloors)) {
                LOGGER.warn(
                        "Number of floors must be between {} and {}. Please try again.",
                        INDUSTRIAL_BUILDING.getMinLevels(),
                        INDUSTRIAL_BUILDING.getMaxLevels()
                );
            }
        } while (BuildingUtils.hasInvalidNumberOfFloors(numberOfFloors));

        return new IndustrialBuilding(squareFootage, numberOfFloors);
    }
}
