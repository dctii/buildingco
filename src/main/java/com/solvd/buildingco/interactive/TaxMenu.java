package com.solvd.buildingco.interactive;

import com.solvd.buildingco.utilities.BigDecimalUtils;
import com.solvd.buildingco.utilities.ITax;
import com.solvd.buildingco.utilities.TaxRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.util.Scanner;

public class TaxMenu extends Menu {
    private static final Logger LOGGER = LogManager.getLogger(TaxMenu.class);

    @Override
    public void display() {
        LOGGER.info("Select which Californian county the home will be built in: ");

        for (TaxRate taxRate : TaxRate.values()) {
            String optionNumber = "[" + (taxRate.ordinal() + 1) + "] ";
            String countyOption = optionNumber + taxRate.getCountyName();

            LOGGER.info(countyOption);
        }
    }

    @Override
    public ITax handleChoice(int choice, Scanner scanner) {
        TaxRate selectedRate = TaxRate.values()[choice - 1]; // handle
        return amount -> BigDecimalUtils
                .multiply(amount, selectedRate.getTaxRate())
                .setScale(2, RoundingMode.UP);
    }

    public static ITax runMenu(Scanner scanner) {
        TaxMenu menu = new TaxMenu();
        menu.display();
        int choice = menu.getChoice(scanner);
        return menu.handleChoice(choice, scanner);
    }
}
