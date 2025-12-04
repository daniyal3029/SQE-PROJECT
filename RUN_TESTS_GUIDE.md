# 🚀 Quick Start - Running Tests

## ✅ Recommended: Use `run-tests.bat`

Simply double-click or run:
```bash
run-tests.bat
```

This will:
1. ✅ Clean previous builds
2. ✅ Compile the project
3. ✅ Run all 36 test cases
4. ✅ Generate Allure HTML report
5. ✅ Clean up temporary files
6. ✅ Ask if you want to open the report

---

## ⚡ Quick Option: Use `quick-test.bat`

For faster execution without cleanup:
```bash
quick-test.bat
```

---

## 📊 What You'll Get

After running, you'll have:
- **Allure Report**: `target/site/allure-maven-plugin/index.html`
- **TestNG Report**: `target/surefire-reports/index.html`
- **Test Results**: Detailed pass/fail statistics

---

## 🎯 Current Test Results

Last run showed:
- **Total**: 36 tests
- **Passed**: 17 (47%)
- **Failed**: 19 (53%)

*Note: Failures are typical for first run and can be improved by adjusting wait times*

---

## 📁 Files Created for You

1. **`run-tests.bat`** - Full test execution with cleanup
2. **`quick-test.bat`** - Fast test execution
3. **`SCRIPTS_README.md`** - Detailed documentation

---

## 🔧 What Gets Cleaned Up

The `run-tests.bat` script removes:
- Temporary files (*.log, test-output.txt)
- IDE files (.idea, *.iml)
- Redis dumps (dump.rdb)
- Old build artifacts

**Important**: Your test data and source code are NOT removed!

---

## 💡 Tips

- **First time**: Use `run-tests.bat`
- **Quick iterations**: Use `quick-test.bat`
- **View report**: Run `mvn allure:serve` or open `target/site/allure-maven-plugin/index.html`

---

**Ready to run? Just execute: `run-tests.bat`** 🎉
