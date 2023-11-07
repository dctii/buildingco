package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.Building;

import java.util.Scanner;

public interface IChoose {
    void display();
    int getChoice(Scanner scanner);
    Building handleChoice(int choice, Scanner scanner);


}
