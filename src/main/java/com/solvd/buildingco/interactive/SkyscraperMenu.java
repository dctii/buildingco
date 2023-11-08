package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.Skyscraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class SkyscraperMenu extends Menu {
    private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco.interactive");
    // ceiling values
    final static int MAX_SQUARE_FOOTAGE_PER_LEVEL = 35000;
    final static int MAX_LEVELS = 100;

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

    // TODO: Store in a separate file, can prob pass in the Menu menu since HouseMenu and others
    //  are children of them
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

    private static Skyscraper createSkyscraper(Scanner scanner) {


        int squareFootagePerLevel = 0;
        do {
            LOGGER.info("Enter the square footage per level for the Skyscraper (up to " + MAX_SQUARE_FOOTAGE_PER_LEVEL + "): ");
            squareFootagePerLevel = scanner.nextInt();
            if (squareFootagePerLevel > MAX_SQUARE_FOOTAGE_PER_LEVEL || squareFootagePerLevel < 1) {
                LOGGER.info("Sorry, that's above the ceiling value for square footage. " +
                        "Please try again.");
            }
        } while (squareFootagePerLevel > MAX_SQUARE_FOOTAGE_PER_LEVEL || squareFootagePerLevel < 1);

        int levels = 0;
        do {
            LOGGER.info("Enter the number of levels for the Skyscraper (up to " + MAX_LEVELS + "): ");
            levels = scanner.nextInt();
            if (levels > MAX_LEVELS || levels < 1) {
                LOGGER.info("Sorry, that's above the ceiling value for levels.Please try again.");
            }
        } while (levels > MAX_LEVELS || levels < 1);

        return new Skyscraper(squareFootagePerLevel, levels);
    }
}
