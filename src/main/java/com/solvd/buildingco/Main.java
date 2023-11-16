package com.solvd.buildingco;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.interactive.HouseMenu;
import com.solvd.buildingco.interactive.IndustrialBuildingMenu;
import com.solvd.buildingco.interactive.SkyscraperMenu;
import com.solvd.buildingco.utilities.BuildingCostCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);


    public static void main(String[] args) {


        // instantiate scanner
        Scanner scanner = new Scanner(System.in);
        // initialize for prompt selection
        int choice;

        do {
            LOGGER.info("Choose a building type: ");
            LOGGER.info("[1] House");
            LOGGER.info("[2] Industrial Building");
            LOGGER.info("[3] Skyscraper");
            LOGGER.info("[0] Exit");
            LOGGER.info("Your choice: ");
            choice = scanner.nextInt();

            // Building instance will be used to calculate costs at the end
            Building<BigDecimal> building;

            /*
                runMenu will activate a prompt sequence for the respective
                building type. For example, "How many rooms would you like?" for `House`
                or `Enter the square footage for the ... (up to 100000)`. The user enters
                a number but should not exceed the ceiling number given.
            */
            switch (choice) {
                case 1:
                    building = HouseMenu.runMenu(scanner);
                    break;
                case 2:
                    building = IndustrialBuildingMenu.runMenu(scanner);
                    break;
                case 3:
                    building = SkyscraperMenu.runMenu(scanner);
                    break;
                case 0:
                    LOGGER.info("Goodbye");
                    return;
                default:
                    LOGGER.info("You cannot choose that");
                    building = null;
                    break;
            }

            // TODO: have customer input a desired completion date, then it will see if the
            //  building can be completed by that time, if not, prompt for a further date
            // hardcoded future completion date
            ZonedDateTime completionDate = ZonedDateTime.now().plusMonths(64);


            /*
                The material and labor cost calculations are based on arbitrary values, so they
                are not accurate, but attempt to provide the appearance of scaling costs as the
                size of the construction grows.

                Material costs are a calculation of things like wood, steel, electrical supplies,
                 and other general things required in any construction project.

                 Labor costs are a calculation of worker hours and their respective, differing
                 hourly rates. Employee types include Project Manager, Architect, Engineer, and
                 Construction Worker.

                 The last is the sum of the material and labor costs.
            */

            if (building != null) {
                BigDecimal calculatedMaterialCost
                        = building.calculateMaterialCost();
                BigDecimal calculatedLaborCost =
                        building.calculateLaborCost(completionDate);
                BigDecimal calculatedBuildingCost =
                        BuildingCostCalculator.calculateBuildingCost(
                                building,
                                completionDate
                        );

                LOGGER.info("Material Cost: " + calculatedMaterialCost);
                LOGGER.info("Labor Cost: " + calculatedLaborCost);
                LOGGER.info("Total Building Cost: " + calculatedBuildingCost);
            }


            // ask to restart the prompt sequence after finishing a BuildingPrompt sequence
            LOGGER.info("Do you want to calculate for another building? (y/n)");
        } while (scanner.next().equalsIgnoreCase("y"));

        scanner.close();
    }

}
