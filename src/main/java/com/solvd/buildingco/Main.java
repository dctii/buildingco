package com.solvd.buildingco;

import com.solvd.buildingco.finance.HourlyRate;
import com.solvd.buildingco.finance.Order;
import com.solvd.buildingco.inventory.Item;
import com.solvd.buildingco.scheduling.Schedule;
import com.solvd.buildingco.scheduling.Schedule.ScheduledActivity;
import com.solvd.buildingco.stakeholders.employees.Employee;
import com.solvd.buildingco.utilities.Calculators.OrderCalculator;
import com.solvd.buildingco.utilities.Calculators.WageCalculator;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.solvd.buildingco.utilities.TimeUtils.getZonedTime;

/* TODO: Assignment 3
* Create  3 building types. Extends 'Building' class. Basics in 'Building': Labor, Structure
* (amount of rooms, how many floors),
   Material, Size, Time to finish.
*  - House
*  - Skyscraper
*  - Industrial Building
*
* EXISTING: Use 'Item' for the materials. Use Order and OrderItem to calculating how much it costs
*  for the
  materials
* EXISTING: Use 'Personnel' and their hourly rates to calculate how much it costs for people
* CREATE: The 'Structure' (Shape, Number of Rooms and Room Types, Sq. Meters, Levels) of the
   project will:
   - increase the amount of time it takes to work on it
   - increase the amount of materials required
        - each factor of Structure will do this as they increase in amount.
        - shape
* CREATE: The 'Rooms'
* Create a required amount of workers and worker types that will go with them
* */

public class Main {
    public static void main(String[] args) {
        // variables initiated at the top

        // project date range used with wage calculation
        String projectStartDate = "03/04/2024";
        String projectEndDate = "03/29/2024";
        // (temporary) used to count the amount of workers of a given Personnel type, multiply by
        // total wage for project length
        int numberOfWorkers = 20;
        int numberOfPMs = 4;
        int numberOfArchitects = 2;
        // types of staff
        Employee constructionWorker, projectManager, architect;
        // arrays for iteration
        int[] allStaffCounts;
        Employee[] allPersonnelTypes;
        // for calculating wages
        WageCalculator wageCalculator;
        BigDecimal combinedTotalWages;
        // types of items for construction and order class for quantifying items
        Item tool, vehicle, material;
        Order projectOrder;
        // for calculating the total order price
        OrderCalculator orderCalculator;
        BigDecimal totalOrderPrice;


        constructionWorker = createConstructionWorker();
        projectManager = createProjectManager();
        architect = createArchitect();

        System.out.println(constructionWorker.getFullName());

        allPersonnelTypes = new Employee[]{constructionWorker, projectManager, architect};
        allStaffCounts = new int[]{numberOfWorkers, numberOfPMs, numberOfArchitects};

        wageCalculator = new WageCalculator();
        combinedTotalWages = new BigDecimal("0.00");

        // calculate the wages of all the personnel combined
        for (int i = 0; i < allPersonnelTypes.length; i++) {
            Employee personnel = allPersonnelTypes[i];
            int staffCount = allStaffCounts[i];

            // work hours are calculated by scheduled hours per day, the hours of each day in the
            // range are taken into the count, (temporary) assumes all staff have the same hours
            long workHours = personnel.getWorkHours(projectStartDate, projectEndDate) * staffCount;
            // the personnel type is multiplied by the amount of work hours to get the wage for
            // that personnel type for this project
            BigDecimal totalStaffWage = wageCalculator.calculate(personnel.getPayRate(), workHours);

            // update the combinedTotalWages for all personnel
            combinedTotalWages = combinedTotalWages.add(totalStaffWage);
        }

        // create new Item(name, price, unitMeasurement, providerName)
        tool = new Item("Tool", new BigDecimal("12.00"), "pcs", "Zed Tools Inc.");
        vehicle = new Item("Vehicle", new BigDecimal("8000.00"), "pcs", "Zed Vehicles Inc.");
        material = new Item("Material", new BigDecimal("0.05"), "lbs", "Doe Construction Supplies Co.");

        // create new order and add `Item` and quantity of item per unit of measurement
        projectOrder = new Order();
        projectOrder
                .addOrderItem(tool, 45)
                .addOrderItem(vehicle, 15)
                .addOrderItem(material, 5000);

        orderCalculator = new OrderCalculator();
        totalOrderPrice = orderCalculator.calculate(projectOrder);

        // print out all the costs
        System.out.println("Total Wages for Personnel project: $" + combinedTotalWages);
        System.out.println("Total price for the items order: $" + totalOrderPrice);
        BigDecimal totalCosts = totalOrderPrice.add(combinedTotalWages);
        System.out.println("Total price for the project: $" + totalCosts);
    }

    public static Employee createProjectManager() {
        String[] nameParts = {
                "John", "Jacob", "Schmidt", null
        };
        String[] postNominals = {
                "PE", "PMP"
        };
        String[] organizationNames = {
                "ABC Construction"
        };
        String[] roles = {
                "Project Manager",
                "Civil Engineer"
        };
        String[] addresses = {
                "123456 Test Lane, Los Angeles, CA 90210",
                "123456 Another Lane, Torrance, CA 90277"
        };
        String[] phoneNumbers = {
                "+12135551234",
                "+12135552345"
        };
        String[] emails = {
                "jj.schmidt250@testexample12345.com",
                "jj.schmidt@abc-construction.com"
        };

        // Create HourlyRate object
        BigDecimal ratePerHour = new BigDecimal("45.00");
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);


        // Create Schedule objects
        Schedule newSchedule = createSchedule();


        // Create PersonnelType object
        String personnelType = "Manager";

        // Create and return the Personnel object
        return new Employee(nameParts, postNominals, organizationNames, roles, addresses,
                phoneNumbers, emails, hourlyRate, newSchedule, personnelType);
    }

    public static Employee createArchitect() {
        String[] nameParts = {
                "Alexa", "M", "Doe", null
        };
        String[] postNominals = {
                "M.Arch",
                "AIA"
        };
        String[] organizationNames = {
                "Architects Anonymous"
        };
        String[] roles = {
                "Architect",
        };
        String[] addresses = {
                "123456 Test Lane, Los Angeles, CA 90210",
                "123456 Another Lane, Torrance, CA 90277"
        };
        String[] phoneNumbers = {
                "+12135551234",
                "+12135552345"
        };
        String[] emails = {
                "am.doe@testexample12345.com",
                "am.doe@abc-construction.com"
        };

        // Create HourlyRate object
        BigDecimal ratePerHour = new BigDecimal("100.00");
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);


        // Create Schedule object
        Schedule newSchedule = createSchedule();


        // Create PersonnelType object
        String personnelType = "Architect";

        // Create and return the Personnel object
        return new Employee(nameParts, postNominals, organizationNames, roles, addresses,
                phoneNumbers, emails, hourlyRate, newSchedule, personnelType);
    }

    public static Employee createConstructionWorker() {
        String[] nameParts = {
                "Bill", "Bo", "Bagginess", "II"
        };
        String[] postNominals = {};
        String[] organizationNames = {
                "ABC Construction Co."
        };
        String[] roles = {
                "Construction Worker",
        };
        String[] addresses = {
                "123456 Test Lane, Los Angeles, CA 90210",
                "123456 Another Lane, Torrance, CA 90277"
        };
        String[] phoneNumbers = {
                "+12135551234",
                "+12135552345"
        };
        String[] emails = {
                "bb.baggins@testexample12345.com",
                "bb.baggins@abc-construction.com"
        };

        // Create HourlyRate object
        BigDecimal ratePerHour = new BigDecimal("35.00");
        HourlyRate hourlyRate = new HourlyRate(ratePerHour);


        // Create Schedule object
        Schedule newSchedule = createSchedule();


        // Create PersonnelType object
        String personnelType = "Construction Worker";

        // Create and return the Personnel object
        return new Employee(nameParts, postNominals, organizationNames, roles, addresses,
                phoneNumbers, emails, hourlyRate, newSchedule, personnelType);
    }

    public static Schedule createSchedule() {
        Schedule schedule = new Schedule();
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");

        // Dates for a 4-week schedule
        String[] dates = {
                "03/04/2024",
                "03/05/2024",
                "03/06/2024",
                "03/07/2024",
                "03/08/2024",

                "03/11/2024",
                "03/12/2024",
                "03/13/2024",
                "03/14/2024",
                "03/15/2024",

                "03/18/2024",
                "03/19/2024",
                "03/20/2024",
                "03/21/2024",
                "03/22/2024",

                "03/25/2024",
                "03/26/2024",
                "03/27/2024",
                "03/28/2024",
                "03/29/2024"
        };
        String startTime = "09:00:00";
        String endTime = "17:00:00";
        String description = "Work";

        for (String date : dates) {
            ZonedDateTime workStart = getZonedTime(date, startTime, zoneId);
            ZonedDateTime workEnd = getZonedTime(date, endTime, zoneId);
            ScheduledActivity work = new ScheduledActivity(
                    description,
                    workStart,
                    workEnd
            );
            schedule.addActivity(work);
        }

        return schedule;
    }


}
