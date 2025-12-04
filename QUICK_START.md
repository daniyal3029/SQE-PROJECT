# Quick Start Guide

## 🚀 Get Started in 3 Steps

### Step 1: Create Excel Test Data

**Option A - Automatic (Recommended):**
```bash
cd e:\F233029_SQE_Project
mvn compile exec:java -Dexec.mainClass="com.saucedemo.utils.ExcelFileCreator"
```

**Option B - Manual:**
Follow instructions in `src/test/resources/testdata/HOW_TO_CREATE_EXCEL.md`

### Step 2: Run Tests

```bash
mvn clean test
```

### Step 3: View Allure Report

```bash
mvn allure:serve
```

---

## ⚡ Quick Commands

```bash
# Compile only
mvn clean compile

# Run specific tag
mvn test -Dcucumber.filter.tags="@Login"

# Run in different browser (edit config.properties first)
# Change: browser=firefox
mvn test

# Generate report without opening
mvn allure:report
```

---

## 📁 Important Files

- **Configuration**: `src/test/resources/config.properties`
- **Test Data**: `src/test/resources/testdata/testdata.xlsx`
- **Feature Files**: `src/test/resources/features/*.feature`
- **Test Runner**: `src/test/java/com/saucedemo/runners/TestRunner.java`

---

## 🔧 Troubleshooting

**Excel file not found?**
→ Run ExcelFileCreator or create manually

**Redis errors?**
→ Set `redis.enabled=false` in config.properties

**Tests fail in Eclipse?**
→ Right-click project > Maven > Update Project

---

## 📚 Full Documentation

See [README.md](README.md) for complete documentation.
