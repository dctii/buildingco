package com.solvd.buildingco;

import com.solvd.buildingco.buildings.Building;
import com.solvd.buildingco.interactive.HouseMenu;
import com.solvd.buildingco.interactive.IndustrialBuildingMenu;
import com.solvd.buildingco.interactive.SkyscraperMenu;
import com.solvd.buildingco.utilities.Calculator.BuildingCostCalculator;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // instantiate scanner
        Scanner scanner = new Scanner(System.in);
        // initialize for prompt selection
        int choice;

        do {
            System.out.println("Choose a building type: ");
            System.out.println("[1] House");
            System.out.println("[2] Industrial Building");
            System.out.println("[3] Skyscraper");
            System.out.println("[0] Exit");
            System.out.print("Your choice: ");
            choice = scanner.nextInt();

            // Building instance will be used to calculate costs at the end
            Building building;

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
                    System.out.println("Goodbye");
                    return;
                default:
                    System.out.println("You cannot choose that");
                    building = null;
                    break;
            }

            // TODO: have customer input a desired completion date, then it will see if the
            //  building can be completed by that time, if not, prompt for a further date
            // hardcoded future completion date
            ZonedDateTime completionDate = ZonedDateTime.now().plusMonths(32);




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
                System.out.println("Material Cost: " + building.calculateMaterialCost());
                System.out.println("Labor Cost: " + building.calculateLaborCost(completionDate));
                System.out.println("Total Building Cost: " + BuildingCostCalculator.calculateBuildingCost(building, completionDate));
            }


            // ask to restart the prompt sequence after finishing a BuildingPrompt sequence
            System.out.println("Do you want to calculate for another building? (y/n)");
        } while (scanner.next().equalsIgnoreCase("y"));
    }

}
