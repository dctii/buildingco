package com.solvd.buildingco.interactive;

import java.util.Scanner;

public interface IChoose {
    void display();

    int getChoice(Scanner scanner);


    <T> T handleChoice(int choice, Scanner scanner);


}
