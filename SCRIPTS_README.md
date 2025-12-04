# Test Execution Scripts

This directory contains batch scripts for running tests and generating reports.

## Available Scripts

### 1. `run-tests.bat` (Recommended)
**Full test execution with cleanup**

Performs:
- ✅ Cleans previous build
- ✅ Compiles project
- ✅ Runs all test cases
- ✅ Generates Allure report
- ✅ Cleans up unwanted files
- ✅ Offers to open report

**Usage:**
```bash
run-tests.bat
```

**What gets cleaned up:**
- Temporary files (test-output.txt, compile-log.txt)
- IDE files (.idea, *.iml, .vscode/settings.json)
- Log files (*.log)
- Redis dump files (dump.rdb)
- Maven wrapper files (.mvn)

---

### 2. `quick-test.bat`
**Fast test execution without cleanup**

Performs:
- ✅ Cleans and runs tests
- ✅ Generates Allure report
- ✅ Opens report automatically

**Usage:**
```bash
quick-test.bat
```

---

### 3. `create-excel.bat`
**Creates Excel test data file**

**Usage:**
```bash
create-excel.bat
```

---

## Manual Commands

### Run All Tests
```bash
mvn clean test
```

### Generate Allure Report
```bash
mvn allure:report
```

### View Report in Browser
```bash
mvn allure:serve
```

### Run Specific Tags
```bash
mvn test -Dcucumber.filter.tags="@Login"
mvn test -Dcucumber.filter.tags="@Positive"
mvn test -Dcucumber.filter.tags="@Database"
```

---

## Report Locations

After running tests, reports are available at:

1. **Allure HTML Report**
   - `target/site/allure-maven-plugin/index.html`

2. **TestNG Report**
   - `target/surefire-reports/index.html`

3. **Allure Results (Raw Data)**
   - `target/allure-results/`

---

## Troubleshooting

### Tests Fail to Run
1. Ensure Java 11+ is installed: `java -version`
2. Ensure Maven is installed: `mvn -version`
3. Check `testdata.xlsx` exists in `src/test/resources/testdata/`

### Report Not Generated
1. Run: `mvn allure:report`
2. Check for errors in console output
3. Verify Allure plugin in `pom.xml`

### Browser Doesn't Open
1. Manually open: `target/site/allure-maven-plugin/index.html`
2. Or run: `mvn allure:serve` (starts local server)

---

## Best Practices

1. **First Run**: Use `run-tests.bat` for complete execution
2. **Quick Iterations**: Use `quick-test.bat` during development
3. **Specific Tests**: Use Maven commands with tags
4. **CI/CD**: Use `mvn clean test` in pipeline

---

## File Cleanup Details

The `run-tests.bat` script removes:

### Temporary Files
- `test-output.txt` - Temporary test output
- `compile-log.txt` - Compilation logs
- `*.log` - All log files

### IDE Files
- `.idea/` - IntelliJ IDEA settings
- `*.iml` - IntelliJ module files
- `.vscode/settings.json` - VS Code settings

### Build Artifacts
- `.mvn/` - Maven wrapper (optional)
- `dump.rdb` - Redis dump file

### What's NOT Removed
- ✅ `target/` - Contains test results and reports
- ✅ `testdata.xlsx` - Your test data
- ✅ Source code files
- ✅ Configuration files

---

## Quick Reference

| Task | Command |
|------|---------|
| Run all tests with cleanup | `run-tests.bat` |
| Quick test run | `quick-test.bat` |
| View report | `mvn allure:serve` |
| Run login tests only | `mvn test -Dcucumber.filter.tags="@Login"` |
| Clean project | `mvn clean` |
| Compile only | `mvn compile` |

---

**Tip**: For continuous integration, use `mvn clean test allure:report` in your CI/CD pipeline.
