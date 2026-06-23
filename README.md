# SauceDemo UI Test Automation Framework
## Project Overview
This is a comprehensive **Web UI Test Automation Framework** built using **Selenium WebDriver**, **Java**, **Cucumber BDD**, and **TestNG**. The framework tests the [SauceDemo](https://www.saucedemo.com) e-commerce website with **30+ automated test scenarios** covering login, product browsing, shopping cart, checkout, form validation, and UI verification.
### Key Features
✅ **Page Object Model (POM)** design pattern for maintainability
✅ **Cucumber BDD** with Gherkin syntax for readable test scenarios
✅ **Data-Driven Testing** using multiple data sources: 
   - **Excel** (Apache POI)
   - **H2 Database** (embedded)
   - **Redis** (optional, with graceful degradation if unavailable)
✅ **Allure Reports** with screenshots on test failures
✅ **TestNG** test runner with parallel execution support
✅ **WebDriverManager** for automatic browser driver management
✅ **Cross-browser support** (Chrome, Firefox, Edge)
---
## Project Structure
```
F233029_SQE_Project/
├── src/
│   ├── test/
│   │   ├── java/com/saucedemo/
│   │   │   ├── config/
│   │   │   │   └── ConfigReader.java          # Configuration management
│   │   │   ├── pages/                          # Page Object Model classes
│   │   │   │   └── BasePage.java
│   │   │   │   └── LoginPage.java
│   │   │   │   └── ProductsPage.java
│   │   │   │   └── ProductDetailsPage.java
│   │   │   │   └── CartPage.java
│   │   │   │   └── CheckoutPage.java
│   │   │   ├── steps/                          # Cucumber step definitions
│   │   │   │   └── LoginSteps.java
│   │   │   │   └── ProductSteps.java
│   │   │   │   └── CheckoutSteps.java
│   │   │   │   └── UIValidationSteps.java
│   │   │   │   └── Hooks.java
│   │   │   ├── utils/                          # Utility classes
│   │   │   │   └── DriverManager.java
│   │   │   │   └── WaitHelper.java
│   │   │   │   └── ScreenshotUtil.java
│   │   │   │   └── ExcelReader.java
│   │   │   │   └── DatabaseUtil.java
│   │   │   │   └── RedisUtil.java
│   │   │   └── runners/
│   │   │       └── TestRunner.java             # TestNG Cucumber runner
│   │   └── resources/
│   │       ├── features/                       # Cucumber feature files
│   │       │   └── login.feature
│   │       │   └── product.feature
│   │       │   └── checkout.feature
│   │       │   └── form.feature
│   │       │   └── ui-validation.feature
│   │       ├── testdata/
│   │       │   └── testdata.xlsx               # Excel test data
│   │       └── config.properties               # Framework configuration
│   │       └── allure.properties               # Allure configuration
│   ├── main/
│   └── target/
├── pom.xml                                     # Maven dependencies
└── testng.xml                                  # TestNG suite configuration
```
---
## Prerequisites
Before running the tests, ensure you have the following installed:
1. **Java JDK 11 or higher**
2. **Maven**
3. **Git**
---
## Installation
To get started, follow these steps:
1. Clone the repository using `git clone https://github.com/your-repo/SQE-PROJECT.git`
2. Navigate to the project directory using `cd SQE-PROJECT`
3. Install the dependencies using `mvn clean install`
---
## Usage
To run the tests, use the following command: `mvn test`
---