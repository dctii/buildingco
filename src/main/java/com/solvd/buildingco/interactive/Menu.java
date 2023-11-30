package com.solvd.buildingco.interactive;

import java.util.Scanner;

public abstract class Menu implements IChoose {
    public abstract void display();

    public int getChoice(Scanner scanner) {
        return scanner.nextInt();
    }


    public abstract Object handleChoice(int choice, Scanner scanner);


}
