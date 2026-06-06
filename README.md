# SauceDemo UI Test Automation Framework
## 📌 Project Overview
This is a comprehensive **Web UI Test Automation Framework** built using **Selenium WebDriver**, **Java**, **Cucumber BDD**, and **TestNG**. The framework tests the [SauceDemo](https://www.saucedemo.com) e-commerce website with **30+ automated test scenarios** covering login, product browsing, shopping cart, checkout, form validation, and UI verification.
### Key Features
✅ **Page Object Model (POM)** design pattern for maintainability  
✅ **Cucumber BDD** with Gherkin syntax for readable test scenarios  
✅ **Data-Driven Testing** using multiple data sources: 
   - **Excel** (Apache POI)
   - **H2 Database** (embedded)
   - **Redis** (optional, graceful degradation if unavailable)  
✅ **Allure Reports** with screenshots on test failures  
✅ **TestNG** test runner with parallel execution support  
✅ **WebDriverManager** for automatic browser driver management  
✅ **Cross-browser support** (Chrome, Firefox, Edge)
---
## 🏗️ Project Structure
```
F233029_SQE_Project/
├── src/
│   ├── test/
│   │   ├── java/com/saucedemo/
│   │   │   ├── config/
│   │   │   │   └── ConfigReader.java          # Configuration management
│   │   │   ├── pages/                          # Page Object Model classes
│   │   │   │   ├── BasePage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ProductsPage.java
│   │   │   │   ├── ProductDetailsPage.java
│   │   │   │   ├── CartPage.java
│   │   │   │   └── CheckoutPage.java
│   │   │   ├── steps/                          # Cucumber step definitions
│   │   │   │   ├── LoginSteps.java
│   │   │   │   ├── ProductSteps.java
│   │   │   │   ├── CheckoutSteps.java
│   │   │   │   ├── UIValidationSteps.java
│   │   │   │   └── Hooks.java
│   │   │   ├── utils/                          # Utility classes
│   │   │   │   ├── DriverManager.java
│   │   │   │   ├── WaitHelper.java
│   │   │   │   ├── ScreenshotUtil.java
│   │   │   │   ├── ExcelReader.java
│   │   │   │   ├── DatabaseUtil.java
│   │   │   │   └── RedisUtil.java
│   │   │   └── runners/
│   │   │       └── TestRunner.java             # TestNG Cucumber runner
│   │   └── resources/
│   │       ├── features/                       # Cucumber feature files
│   │       │   ├── login.feature
│   │       │   ├── product.feature
│   │       │   ├── checkout.feature
│   │       │   ├── form.feature
│   │       │   └── ui-validation.feature
│   │       ├── testdata/
│   │       │   └── testdata.xlsx               # Excel test data
│   │       ├── config.properties               # Framework configuration
│   │       └── allure.properties               # Allure configuration
├── pom.xml                                     # Maven dependencies
├── testng.xml                                  # TestNG suite configuration
└── README.md                                   # This file
```
---
## 🔧 Prerequisites
Before running the tests, ensure you have the following installed:
1. **Java JDK 11 or higher**
2. **Maven** (for building and managing dependencies)
3. **Git** (for version control)
4. **Allure** (for reporting)
5. **TestNG** (for test execution)
6. **Selenium WebDriver** (for browser automation)
7. **Cucumber** (for BDD testing)

## 🚀 Getting Started
To get started with the project, follow these steps:
1. Clone the repository using Git.
2. Build the project using Maven.
3. Configure the test environment by setting up the required dependencies.
4. Run the tests using TestNG.

## 📊 Contributing
Contributions are welcome! If you'd like to contribute to the project, please follow these steps:
1. Fork the repository.
2. Make your changes and commit them.
3. Open a pull request.

## 📚 License
This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.