# Building Company

Uses:

- IntelliJ IDEA 2023.2.3 (Community Edition)
- openjdk 11.0.21 2023-10-17


## Assignments

<hr />

### Assignment 8: Git

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 7:
- <span style="color: pink;"> Pending... </span>

##### Requirements for Assignment 8
1. Push your code to GitHub
2. In the GitHub repository, the evaluator should see:
   - `src/` folder
   - `pom.xml` file
   - `.gitignore` file



<hr />

### Assignment 7: Maven

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 6:
- For `log4j`, put the class name as the argument. For example, in `private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco");`, swap out `"com.solvd.buildingco"` with `Main.class` in `Main.java`.
- Make sure `@Override` of `toString` in each class exists, understand why and how `HashSet` and `HashMap` works.
- Close the `Scanner` out with `in.close()`.
- For any use of exceptions, for now use `RuntimeException`.
- For data generators or class instantiation methods, make sure a class does not generate itself. The only exception for something like this is when it requires fields that are in the class itself. For example, the `Order generateMaterialOrder()` method in `House`, `IndustrialBuilding`, and `Skyscraper`.
  - Store generators in a folder called `utils` or `utilities`
  - Some methods to move or consider moving:
    - `generateEmployee()`
    - `calculateMaterialCost()`
    - `calculateConstructionDays()`
    
- Move fields to the top of the class.
- Remove unused generic in `HourlyRate`
- set `weeklyActivities` to `final` and assign it to a `new HashMap<>();`, then remove `this.weeklyActivities` from `Schedule` constructor
- Do not nest `BuildingCostCalculator` class within `Calculator`. Just call it `BuildingCostCalculator`. Avoid nesting classes.
- Consider how to abstract things as much as possible

##### Requirements for Assignment 7
1. Install Maven to your local env
2. Create `pom.xml` file in project (https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
3. Add Log4j as a dependency and remove `lib/` directory.
4. You should be able to run `maven` commands without _IntelliJ IDEA CE_
5. Add plugins (https://maven.apache.org/guides/mini/guide-configuring-plugins.html)



<hr />

### Assignment 6: Collections. Generics.

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 6:
- <span style="color: pink;"> Pending... </span>

##### Requirements for Assignment 6
1. Add usage of any type of `Map`, `List` and `Set` collections.
2. Create 1 generic and include their usage in the project.



<hr />

### Assignment 5: Exceptions. Try-catch-finally block. Try-catch with resources. Throw. Throws. Logger.

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 4:
- Find values that can be stored as constant and put them at the beginning of its class.
- Pay attention to fields that are initialized only once and make them final

##### Requirements for Assignment 5
1. Add 5 custom exceptions for your application. Store them in a dir called `exception`.
2. Configure logger and replace all `System.out.print` with the logger
   - Put the `.jar` files in a dir called `lib`.
   - LOGGER.info(); for general info
   - LOGGER.debug(); self-explanatory
   - LOGGER.error(); for unexpected behavior


<hr />

### Assignment 4: Interfaces. Final(class, method, variable). Static. Polymorphism.

<hr />

#### Requirements

##### Comments and Required changes on Assignment 3:
- <span style="color: pink;"> Pending... </span>

##### Requirements for Assignment 4
1. Add 3 interfaces to your application (please keep in mind their purpose and use polymorphism with them as explained on the lesson)
   - 1a. Whoever has menus in main classes, please use interfaces with them
2. Add 5 `final static` constants.


<hr />

### Assignment 3: Inheratance. Abstraction. Method Overriding. Class Object. Encapsulation(protected)

<hr />

#### Requirements

##### Comments and Required changes on Assignment 2:
1. Remove enums and interfaces
2. Simplify building process
3. Make app more various

##### Requirements for Assignment 3
1. Add 4 - 5 `abstract` classes to your project
2. Each `abstract` class should have at least 1 `abstract` method
3. Use polymorphism with your abstract classes(so some methods take as a parameter abstract class and then call some functions for it or it can be a field in another class)
4. `@Override` `toString()` method for your classes


<hr />

### Assignment 2: OOP, Classes, Objects

<hr />
Organization Type: Building Company
Specifications:
  - 2023/10/27
    - count price
    - timing utility for civil or industrial building production

1. Create a new project with folders structured as they are in the 
instructor's screenshot.
2. Create 10 Java _classes_ with field _getters_, _setters_, and 
_constructors_.
3. Create a `com.solvd.buildingco.Main` class (can be named `App` or `EntryPoint`) where
all the core logic wil be placed.







