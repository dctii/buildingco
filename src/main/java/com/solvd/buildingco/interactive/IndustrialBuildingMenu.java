package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.IndustrialBuilding;

import java.util.Scanner;

public class IndustrialBuildingMenu extends Menu {
    // ceiling values
    final static int MAX_SQUARE_FOOTAGE = 75000;
    final static int MAX_FLOORS = 4;

    @Override
    public void display() {
        System.out.println("Industrial Building Menu: ");
        System.out.println("[1] Create Industrial Building");
        System.out.println("[0] Back");
        System.out.print("Your choice: ");
    }


    @Override
    public IndustrialBuilding handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                return createIndustrialBuilding(scanner);
            case 0:
                return null;
            default:
                System.out.println("You cannot choose that");
                return null;
        }
    }


    public static IndustrialBuilding runMenu(Scanner scanner) {
        IndustrialBuildingMenu menu = new IndustrialBuildingMenu();
        IndustrialBuilding industrialBuilding = null;
        int choice;

        do {
            menu.display();
            choice = menu.getChoice(scanner);
            industrialBuilding = menu.handleChoice(choice, scanner);
            if (industrialBuilding == null && choice != 0) {
                System.out.println("Invalid choice. Please try again or choose [0] to go back.");
            }

        } while (industrialBuilding == null && choice != 0);

        return industrialBuilding;
    }

    private static IndustrialBuilding createIndustrialBuilding(Scanner scanner) {


        int squareFootage = 0;
        do {
            System.out.print("Enter the square footage for the Industrial Building (up to " + MAX_SQUARE_FOOTAGE + "): ");
            squareFootage = scanner.nextInt();
            if (squareFootage > MAX_SQUARE_FOOTAGE || squareFootage < 1) {
                System.out.println("Sorry, that's the ceiling value for square footage. Please " +
                        "try again.");
            }
        } while (squareFootage > MAX_SQUARE_FOOTAGE || squareFootage < 1);

        int numberOfFloors = 0;
        do {
            System.out.print("Enter the number of floors for the Industrial Building (up to " + MAX_FLOORS + "): ");
            numberOfFloors = scanner.nextInt();
            if (numberOfFloors > MAX_FLOORS || numberOfFloors < 1) {
                System.out.println("Sorry, that's above the ceiling value for number of floors. " +
                        "Please try again.");
            }
        } while (numberOfFloors > MAX_FLOORS || numberOfFloors < 1);

        return new IndustrialBuilding(squareFootage, numberOfFloors);
    }
}
