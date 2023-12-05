package com.solvd.buildingco.interactive;

import com.solvd.buildingco.buildings.Building;

import java.util.Scanner;

public abstract class BuildingMenu extends Menu {
    @Override
    public abstract Building handleChoice(int choice, Scanner scanner);
}
