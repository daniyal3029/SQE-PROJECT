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
   ```bash
   java -version
   ```

2. **Maven 3.6 or higher**
   ```bash
   mvn -version
   ```

3. **Google Chrome** (or Firefox/Edge for cross-browser testing)

4. **Redis Server** (optional - tests will run without it)
   - Windows: Download from [Redis for Windows](https://github.com/microsoftarchive/redis/releases)
   - Start Redis: `redis-server`
   - Default port: 6379

5. **Eclipse IDE** (optional, for development)

---

## 🚀 Setup Instructions

### 1. Clone or Extract the Project

```bash
cd e:\F233029_SQE_Project
```

### 2. Install Dependencies

```bash
mvn clean install
```

This will download all required dependencies including:
- Selenium WebDriver
- Cucumber
- TestNG
- Allure
- Apache POI
- H2 Database
- Jedis (Redis client)

### 3. Configure Framework (Optional)

Edit `src/test/resources/config.properties` to customize:

```properties
# Browser selection
browser=chrome

# Headless mode
headless.mode=false

# Redis (set to false if Redis is not available)
redis.enabled=true
```

---

## ▶️ Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Tags

```bash
# Run only login tests
mvn clean test -Dcucumber.filter.tags="@Login"

# Run only positive tests
mvn clean test -Dcucumber.filter.tags="@Positive"

# Run database integration tests
mvn clean test -Dcucumber.filter.tags="@Database"
```

### Run in Different Browsers

Edit `config.properties` and change:
```properties
browser=firefox  # or edge
```

### Run from Eclipse

1. Import project: `File > Import > Existing Maven Projects`
2. Right-click on `TestRunner.java`
3. Select `Run As > TestNG Test`

---

## 📊 Generate Allure Reports

### Generate and View Report

```bash
mvn allure:serve
```

This will:
1. Generate the Allure report
2. Open it automatically in your default browser

### Generate Report Only

```bash
mvn allure:report
```

Report will be generated in `target/allure-report/`

### Allure Report Features

- ✅ Test execution timeline
- ✅ Test case details with steps
- ✅ Screenshots for failed tests
- ✅ Test categorization by features
- ✅ Trend graphs and statistics

---

## 📝 Test Scenarios

### Login Tests (7 scenarios)
1. ✅ Login with valid credentials (data-driven from Excel)
2. ✅ Login with invalid credentials
3. ✅ Login with empty username
4. ✅ Login with empty password
5. ✅ Login with empty fields
6. ✅ Successful logout
7. ✅ Login with locked out user

### Product Tests (8 scenarios)
8. ✅ Sort products by price
9. ✅ Sort products by name
10. ✅ View product details (database integration)
11. ✅ Add single product to cart
12. ✅ Add multiple products from database
13. ✅ Remove product from cart
14. ✅ Add product from details page
15. ✅ Navigate back to products

### Checkout Tests (7 scenarios)
16. ✅ Complete checkout with valid data (data-driven from Excel)
17. ✅ Checkout with missing first name
18. ✅ Checkout with missing last name
19. ✅ Checkout with missing zip code
20. ✅ Checkout with all empty fields
21. ✅ Verify checkout overview information
22. ✅ Cancel checkout

### Form Validation Tests (4 scenarios)
23. ✅ Login form validation - empty username
24. ✅ Login form validation - empty password
25. ✅ Checkout form validation - all fields
26. ✅ Error message persistence

### UI Validation Tests (8 scenarios)
27. ✅ Verify login page UI elements
28. ✅ Verify products page UI elements
29. ✅ Verify cart page UI elements
30. ✅ Verify checkout page titles and URLs
31. ✅ Verify product images
32. ✅ Verify product details page elements
33. ✅ Verify all products have required information
34. ✅ Verify navigation consistency

**Total: 34 automated test scenarios**

---

## 💾 Data Sources

### 1. Excel (Apache POI)

File: `src/test/resources/testdata/testdata.xlsx`

**Sheets:**
- `LoginData`: User credentials
- `ProductData`: Product information
- `CheckoutData`: Checkout form data

### 2. H2 Database (Embedded)

Database file: `testdb/saucedemo.mv.db` (auto-created)

**Tables:**
- `users`: User credentials and types
- `products`: Product details and prices
- `checkout_info`: Checkout form data

Database is automatically initialized on first run.

### 3. Redis (Optional)

**Usage:**
- Session token storage
- Test data caching
- State management

If Redis is not available, tests will continue without Redis features.

---

## 🔍 Troubleshooting

### Issue: Tests fail with "WebDriver not found"

**Solution:** WebDriverManager handles this automatically. Ensure you have internet connection for first run.

### Issue: Redis connection errors

**Solution:** 
1. Set `redis.enabled=false` in `config.properties`, OR
2. Start Redis server: `redis-server`

### Issue: Excel file not found

**Solution:** Ensure `testdata.xlsx` exists in `src/test/resources/testdata/`

### Issue: Database errors

**Solution:** Delete `testdb` folder and rerun tests. Database will be recreated.

### Issue: Tests fail in Eclipse

**Solution:**
1. Right-click project > Maven > Update Project
2. Clean and rebuild: `Project > Clean`
3. Ensure TestNG plugin is installed

---

## 🎯 Best Practices Implemented

1. **Page Object Model**: Separates page structure from test logic
2. **DRY Principle**: Reusable methods in BasePage
3. **Explicit Waits**: WaitHelper for stable test execution
4. **Configuration Management**: Centralized config.properties
5. **Logging**: Console output for debugging
6. **Screenshot on Failure**: Automatic capture and Allure attachment
7. **Data-Driven Testing**: Excel and Database integration
8. **BDD Approach**: Readable Gherkin scenarios
9. **Graceful Degradation**: Tests continue if Redis unavailable

---

## 📹 Video Demonstration

Record a video showing:
1. Running tests: `mvn clean test`
2. Test execution in console
3. Opening Allure report: `mvn allure:serve`
4. Navigating through test results
5. Viewing screenshots of failed tests (if any)

---

## 👨‍💻 Author

**Project:** UI Test Automation Framework  
**Course:** Software Quality Engineering  
**Website Under Test:** https://www.saucedemo.com  
**Framework:** Selenium + Java + Cucumber + TestNG  
**Reporting:** Allure Reports

---

## 📄 License

This project is created for educational purposes as part of the Software Quality Engineering course.
