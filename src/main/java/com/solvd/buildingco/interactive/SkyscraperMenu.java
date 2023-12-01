package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.Skyscraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static com.solvd.buildingco.buildings.CommercialBuildingSpecifications.SKYSCRAPER;
import static com.solvd.buildingco.utilities.BuildingUtils.hasInvalidNumberOfLevels;
import static com.solvd.buildingco.utilities.BuildingUtils.hasInvalidSquareFootagePerLevel;

public class SkyscraperMenu extends BuildingMenu {
    private static final Logger LOGGER = LogManager.getLogger(SkyscraperMenu.class);
    // ceiling values

    @Override
    public void display() {
        LOGGER.info("Skyscraper Menu: ");
        LOGGER.info("[1] Create Skyscraper");
        LOGGER.info("[0] Back");
        LOGGER.info("Your choice: ");
    }


    @Override
    public Skyscraper handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                return createSkyscraper(scanner);
            case 0:
                return null;
            default:
                LOGGER.info("You cannot choose that");
                return null;
        }
    }

    // executes menu for Skyscraper
    public static Skyscraper runMenu(Scanner scanner) {
        SkyscraperMenu menu = new SkyscraperMenu();
        Skyscraper skyscraper = null;
        int choice;

        do {
            menu.display();
            choice = menu.getChoice(scanner);
            skyscraper = menu.handleChoice(choice, scanner);
            if (skyscraper == null && choice != 0) {
                LOGGER.info("Invalid choice. Please try again or choose [0] to go back.");
            }

        } while (skyscraper == null && choice != 0);

        return skyscraper;
    }

    // create Skyscraper object with user input
    private static Skyscraper createSkyscraper(Scanner scanner) {
        int squareFootagePerLevel = 0;

        do {
            LOGGER.info(
                    "Enter the square footage per level for the Skyscraper ({}-{}): ",
                    SKYSCRAPER.getMinSquareFootage(),
                    SKYSCRAPER.getMaxSquareFootage()
            );

            squareFootagePerLevel = scanner.nextInt();

            if (hasInvalidSquareFootagePerLevel(squareFootagePerLevel)) {
                LOGGER.warn("Square footage per level must be between {} and {}. Please try again.",
                        SKYSCRAPER.getMinSquareFootage(),
                        SKYSCRAPER.getMaxSquareFootage()
                );
            }
        } while (hasInvalidSquareFootagePerLevel(squareFootagePerLevel));

        int numberOfLevels = 0;

        do {
            LOGGER.info(
                    "Enter the number of levels for the Skyscraper ({}-{}): ",
                    SKYSCRAPER.getMinLevels(),
                    SKYSCRAPER.getMaxLevels()
            );

            numberOfLevels = scanner.nextInt();

            if (hasInvalidNumberOfLevels(numberOfLevels)) {
                LOGGER.warn(
                        "The number of levels must be between {} and {}. Please try again.",
                        SKYSCRAPER.getMinLevels(),
                        SKYSCRAPER.getMaxLevels()
                );
            }
        } while (hasInvalidNumberOfLevels(numberOfLevels));

        return new Skyscraper(squareFootagePerLevel, numberOfLevels);
    }

}
