# Building Company

Uses:

- IntelliJ IDEA 2023.2.3 (Community Edition)
- openjdk 11.0.21 2023-10-17
- mac os x aarch64
- Apache Maven 3.9.5

Dependencies:

- org.apache.logging.log4j:log4j-api v2.21.1
- org.apache.logging.log4j:log4j-core v2.21.1
- org.apache.commons:commons-lang3 v3.12.0
- commons-io:commons-io v2.15.0

Plugins:

- org.apache.maven.plugins:maven-compiler-plugin v3.11.0
- org.codehaus.mojo:exec-maven-plugin v3.1.0

## How to Run

### Run `Main.class`

```shell
mvn clean install exec:java
```

### Run `WordCounter.class`

```shell
# specifically for assignment 9
mvn clean install exec:java -P run-word-counter
```

### Run `MultithreadingProof.class`
```shell
# specifically for assignment 12
mvn clean install exec:java -P run-multithreading-proof
```

## Assignments

<hr />

### Assignment 12: Multithreading

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 11:

- Change _lambda expressions_ to _method references_ where possible.

##### Requirements for Assignment 12

1. Create 3 classes, `Connection`, `ConnectionPool`, and, thirdly, some client which can be used in
   multithreading.
2. The `ConnectionPool` should be a _singleton_ and should have a set maximum value, which can be
   set to `5` of connections.
3. Any `Thread` should be able to wait for an available connection. Print the connection's name, or
   use some other strategy, to identify and show explicit proof that it is waiting. See the
   screenshot in our Google Classroom.

<hr />

### Assignment 11: Reflection. Collection streaming

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 10:

- don't use default initializations
- don't hardcode data everywhere that can be put into one place, like a single and separate `class`
- may find more instances where _polymorphism_ can be applied
- reduce usage of `import static` where possible

##### Requirements for Assignment 11

1. Create a `class` `Object` using _reflection_ and put data there and use in project workflow (
   choose any `class`)
2. Replace all `for` loops in your project with `Collection` streaming

<hr />

### Assignment 10: Enums, Lambda, Functional Interface

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 9:

- make no-argument constructors in classes if possible, else make constructors that reduce the size
  of the parameter list
- do not create fields with preset values
- can use `Calendar` class for date and time
- should use `UUID.randomUUID()` for generating IDs
- enable WordCounter to take in a list of words and have the WordCounter count for the words in that
  list. Use `StringUtils.countMatches()`.
- use primitive types instead of reference types where needed, for example use `boolean` instead
  of `Boolean`

##### Requirements for Assignment 10

1. Add 3 enums to project (not simple, with fields and parameters)
2. Add 1 functional interface and integrate it to project functionality with Lambda function usage

<hr />

### Assignment 9: Apache libraries. IDE hotkeys. Debugging.

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 8:

- <span style="color: pink;"> Pending... </span>

##### Requirements for Assignment 9

1. Make top 10 of hotkeys for IntelliJ IDEA and learn them
2. Word counting algorithm on `.txt` file
    - In the `resources/` folder, create one `.txt` file which can contain any article, but do
      not use any short articles, and copy some text from a website.
    - Create one `.java` file with `main method` that should not be related to the application
      near your existing `Main` or `App` class.
    - In this file, implement an algorithm which counts the amount of given words in the
      article.
    - Use `StringUtils` and `FileUtils` for that purpose.
    - As output, you should have another `.txt` file in the resources which contains the
      information in the block below. In the example below, `I`, `day`, and `give` are three words
      that appear in a given `.txt` file and the `25`, `45`, and `99` are how many times,
      respectively, that each of these three words appear in the `.txt` file.

         ```text
         I = 25
         day = 45
         give = 99
         ```
      &ast; _The words above are just examples, but it can be for any word_.

#### Assignment 9.1: [IntelliJ IDEA's top keyboard shortcuts](https://www.jetbrains.com/help/idea/reference-keymap-mac-default.html#top_shortcuts)

| #  | shortcut               | keys                      |
|----|------------------------|---------------------------|
| 1  | Search Everywhere      | `⇧ SHIFT`, `⇧ SHIFT`      |
| 2  | Find Action            | `⌘ CMD` + `⇧ SHIFT` + `A` |
| 3  | Show Project window    | `⌘ CMD` + `1`             |
| 4  | Show Intention Actions | `⌥ OPT` + `↩ ENTER`       |
| 5  | Find Usages            | `⌥ OPT` + (`fn` +) `F7`   |
| 6  | Run Anything           | `⌃ CTRL`, `⌃ CTRL`        |
| 7  | Generate               | `⌘ CMD` + `N`             |
| 8  | Debug                  | `⌃ CTRL` + `⌥ OPT` + `D`  |
| 9  | Refactor This          | `⌃ CTRL` + `T`            |
| 10 | Reformat Code          | `⌘ CMD` + `⌥ OPT` + `L`   |

##### More useful shortcuts

| #  | shortcut                    | keys                                |
|----|-----------------------------|-------------------------------------|
| 11 | Toggle Line Breakpoint      | `⌘ CMD` + (`fn` +) `F8`             |
| 12 | View Breakpoints            | `⌘ CMD` + `⇧ SHIFT` + (`fn` +) `F8` |
| 13 | Edit Breakpoint             | `⌘ CMD` + `⇧ SHIFT` + (`fn` +) `F8` |
| 14 | Evaluate Expression         | `⌥ OPT` + (`fn` +) `F8`             |
| 14 | Show Debug window           | `⌘ CMD` + `5`                       | 
| 15 | Show Run window             | `⌘ CMD` + `4`                       |
| 16 | Show Terminal window        | `⌘ CMD` + `2`                       |
| 17 | Rename                      | `⇧ SHIFT` + (`fn` +) `F6`           |
| 18 | Change Signature            | `⌘ CMD` + (`fn` +) `F6`             |
| 19 | Inline refactor             | `⌘ CMD` + `⌥ OPT` + `N`             |
| 20 | Extract Method              | `⌘ CMD` + `⌥ OPT` + `M`             |
| 21 | Safe Delete                 | `⌘ CMD` + `⌦ DELETE`                |
| 22 | Introduce Variable          | `⌘ CMD` + `⌥ OPT` + `V`             |
| 23 | Introduce Field             | `⌘ CMD` + `⌥ OPT` + `F`             |
| 24 | Introduce Parameter         | `⌘ CMD` + `⌥ OPT` + `P`             |
| 25 | Move                        | (`fn` +) `F6`                       |
| 26 | Duplicate Selection or Line | ⌘ CMD` + `D`                        |

<hr />

### Assignment 8: Git

<hr />

#### Requirements

##### Comments and Required changes to consider from Assignment 7:

- Rewrite the method `generateMaterialOrder()`
    - make the method in another class and giving the `Building` type reference to method which will
      generate and set MaterialOrder to some class field from outside so the class will store data
      without this generating logic.

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

- For `log4j`, put the class name as the argument. For example,
  in `private static final Logger LOGGER = LogManager.getLogger("com.solvd.buildingco");`, swap
  out `"com.solvd.buildingco"` with `Main.class` in `Main.java`.
- Make sure `@Override` of `toString` in each class exists, understand why and how `HashSet`
  and `HashMap` works.
- Close the `Scanner` out with `in.close()`.
- For any use of exceptions, for now use `RuntimeException`.
- For data generators or class instantiation methods, make sure a class does not generate itself.
  The only exception for something like this is when it requires fields that are in the class
  itself. For example, the `Order generateMaterialOrder()` method in `House`, `IndustrialBuilding`,
  and `Skyscraper`.
    - Store generators in a folder called `utils` or `utilities`
    - Some methods to move or consider moving:
        - `generateEmployee()`
        - `calculateMaterialCost()`
        - `calculateConstructionDays()`

- Move fields to the top of the class.
- Remove unused generic in `HourlyRate`
- set `weeklyActivities` to `final` and assign it to a `new HashMap<>();`, then
  remove `this.weeklyActivities` from `Schedule` constructor
- Do not nest `BuildingCostCalculator` class within `Calculator`. Just call
  it `BuildingCostCalculator`. Avoid nesting classes.
- Consider how to abstract things as much as possible

##### Requirements for Assignment 7

1. Install Maven to your local env
2. Create `pom.xml` file in
   project (https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
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

1. Add 3 interfaces to your application (please keep in mind their purpose and use polymorphism with
   them as explained on the lesson)
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
3. Use polymorphism with your abstract classes(so some methods take as a parameter abstract class
   and then call some functions for it or it can be a field in another class)
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







