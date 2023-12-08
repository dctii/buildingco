package com.solvd.buildingco.interactive;

import com.solvd.buildingco.utilities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

public class TaxMenu extends Menu {
    private static final Logger LOGGER = LogManager.getLogger(TaxMenu.class);

    @Override
    public void display() {
        LOGGER.info("Select which Californian county the home will be built in: ");

        Arrays.stream(TaxRate.values())
                .forEach(taxRate -> {
                    String optionNumber = "[" + (taxRate.ordinal() + 1) + "] ";
                    String countyOption = optionNumber + taxRate.getCountyName();

                    LOGGER.info(countyOption);
                });
    }

    @Override
    public ITax handleChoice(int choice, Scanner scanner) {
        TaxRate[] rates = TaxRate.values();
        boolean isValidChoice = choice < 1 || choice > rates.length;
        if (isValidChoice) {
            return null;
        }

        TaxRate selectedRate = rates[choice - 1]; // handle
        return amount -> BigDecimalUtils
                .multiply(amount, selectedRate.getTaxRate())
                .setScale(2, RoundingMode.UP);
    }

    public static ITax runMenu(Scanner scanner) {
        TaxMenu menu = new TaxMenu();
        ITax tax = null;

        while (tax == null) {
            menu.display();
            int choice = menu.getChoice(scanner);
            tax = menu.handleChoice(choice, scanner);
            if (tax == null) {
                LOGGER.error(
                        "{}{}Invalid option. Please select a valid number{}{}",
                        StringConstants.NEWLINE,
                        AnsiCodes.YELLOW,
                        AnsiCodes.RESET_ALL,
                        StringConstants.NEWLINE
                );
            }
        }
        return tax;
    }
}
