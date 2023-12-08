package com.solvd.buildingco;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;
import com.solvd.buildingco.interactive.HouseMenu;
import com.solvd.buildingco.interactive.IndustrialBuildingMenu;
import com.solvd.buildingco.interactive.SkyscraperMenu;
import com.solvd.buildingco.interactive.TaxMenu;
import com.solvd.buildingco.utilities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Scanner;

import static com.solvd.buildingco.utilities.StringFormatters.toUSD;

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

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    break;
                } else {
                    LOGGER.info(
                            "Invalid choice. Please enter a number from 0-3 as accords with the options above."
                    );
                    scanner.next();
                }
            }


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


            /*
                Fixed future completion date, expected completion time will count backward from 20
                years in the future to set the expected start time.
            */
            ZonedDateTime completionDate = ZonedDateTime.now().plusMonths(12 * 20);


            /*
                The material and labor cost calculations are based on arbitrary values, so they
                are not accurate, but attempt to provide the appearance of scaling costs as the
                size of the construction grows.

                An interactive menu for tax rates will appear and ask the user which California
                county to choose from. They will get the rate for that county.

                Material costs are a calculation of things like wood, steel, electrical supplies,
                 and other general things required in any construction project.

                 The cost of taxes will be calculated for the material costs.

                 Labor costs are a calculation of worker hours and their respective, differing
                 hourly rates. Employee types include Project Manager, Architect, Engineer, and
                 Construction Worker.

                 The last is the sum of the material and labor costs.
            */
            if (building != null) {
                ITax taxCalculator = TaxMenu.runMenu(scanner);

                BigDecimal calculatedMaterialCost
                        = building.calculateMaterialCost();
                BigDecimal materialsTaxAmount = taxCalculator.calculateTax(calculatedMaterialCost);
                BigDecimal calculatedLaborCost =
                        building.calculateLaborCost(completionDate);
                BigDecimal calculatedBuildingCost =
                        BuildingCostCalculator.calculateBuildingCost(
                                building,
                                completionDate
                        );
                BigDecimal calculatedBuildingCostWithTax =
                        calculatedBuildingCost.add(materialsTaxAmount);

                // display specs of building
                if (building instanceof House) {
                    BuildingUtils.displayBuildingProfile((House) building);
                } else if (building instanceof IndustrialBuilding) {
                    BuildingUtils.displayBuildingProfile((IndustrialBuilding) building);
                } else {
                    BuildingUtils.displayBuildingProfile((Skyscraper) building);
                }

                int totalConstructionDays = building.getConstructionDays();

                LOGGER.info("Material Cost: " + toUSD(calculatedMaterialCost));
                LOGGER.info("Material Cost Sales Tax: " + toUSD(materialsTaxAmount));
                LOGGER.info("Labor Cost: " + toUSD(calculatedLaborCost));
                LOGGER.info("Total Building Cost: " + toUSD(calculatedBuildingCostWithTax));
                LOGGER.info("Estimated Construction Days: " + totalConstructionDays);
                LOGGER.info("Expected to Build Between: "
                        + ScheduleUtils.toReadableDateString(completionDate.minusDays(totalConstructionDays))
                        + " to "
                        + ScheduleUtils.toReadableDateString(completionDate)
                );
                LOGGER.info(StringConstants.NEWLINE);
            }


            // ask to restart the prompt sequence after finishing a BuildingPrompt sequence
            LOGGER.info(
                    "Do you want to calculate for another building? "
                            + StringConstants.NEWLINE
                            + "Enter 'y' to start over or any other key to exit. "
            );
        } while (scanner.next().equalsIgnoreCase("y"));

        scanner.close();
    }

}
