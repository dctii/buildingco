package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.House;
import com.solvd.buildingco.buildings.IndustrialBuilding;
import com.solvd.buildingco.buildings.Skyscraper;

import java.util.Scanner;

/*
            Request the number of rooms from the user. Ceilings are set for each and, if not
            respected when user inputs, the user will be prompted to try again.
                - createHouse ask how many rooms, bathrooms, and garage capacity.
                - createIndustrialBuilding and createSkyscraper ask how much square footage and how
                many floors

 */
public class BuildingPrompt {
    public static House createHouse(Scanner scanner) {
        int numRooms = 0;
        do {
            System.out.print("How many rooms would you like (up to 8)? ");
            numRooms = scanner.nextInt();
            if (numRooms > 8 || numRooms < 1) {
                System.out.println("Sorry, that's not a valid number of rooms. Please try again.");
            }
        } while (numRooms > 8 || numRooms < 1);

        // Dynamically calculates the ceiling for bathrooms. Can not have more bathrooms than rooms.
        int numBathrooms = 0;
        do {
            System.out.print("How many bathrooms would you like (up to " + numRooms + ")? ");
            numBathrooms = scanner.nextInt();
            if (numBathrooms > numRooms || numBathrooms < 1) {
                System.out.println("Sorry, that's not a valid number of bathrooms. Please try again.");
            }
        } while (numBathrooms > numRooms || numBathrooms < 1);

        // Cannot have more than four garage spaces. Also, cannot have more amount of car
        // spaces than rooms. If 2 rooms, then no more than 2 car spaces in garage capacity.
        int maxGarageCapacity = Math.min(4, numRooms);
        int garageCapacity = 0;
        do {
            System.out.print("What garage capacity would you like (up to " + maxGarageCapacity + ", number of cars)? ");
            garageCapacity = scanner.nextInt();
            if (garageCapacity > maxGarageCapacity || garageCapacity < 0) {
                System.out.println("Sorry, that's not a valid garage capacity. Please try again.");
            }
        } while (garageCapacity > maxGarageCapacity || garageCapacity < 0);

        return House.createHouse(numRooms, numBathrooms, garageCapacity);
    }



    public static IndustrialBuilding createIndustrialBuilding(Scanner scanner) {
        // ceiling value
        final int maxSquareFootage = 100000;

        System.out.print("Enter the square footage for the Industrial Building (up to " + maxSquareFootage + "): ");
        int squareFootage = Math.min(scanner.nextInt(), maxSquareFootage);

        System.out.print("Enter the number of floors for the Industrial Building (up to 4): ");
        int numberOfFloors = Math.min(scanner.nextInt(), 4);

        return new IndustrialBuilding(squareFootage, numberOfFloors);
    }

    public static Skyscraper createSkyscraper(Scanner scanner) {
        // ceiling values
        final int maxSquareFootagePerLevel = 50000;
        final int maxLevels = 100;

        System.out.print("Enter the square footage per level for the Skyscraper (up to " + maxSquareFootagePerLevel + "): ");
        int squareFootagePerLevel = Math.min(scanner.nextInt(), maxSquareFootagePerLevel);

        System.out.print("Enter the number of levels for the Skyscraper (up to " + maxLevels + "): ");
        int levels = Math.min(scanner.nextInt(), maxLevels);

        return new Skyscraper(squareFootagePerLevel, levels);
    }
}
