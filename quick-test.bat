@echo off
REM ============================================================================
REM Purpose: Quick Test Run (No Cleanup)
REM Runs tests and opens report immediately without cleanup
REM ============================================================================

echo Running tests and generating report...
cd /d "%~dp0"

call mvn clean test allure:report
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Tests complete! Opening report...
    start target\site\allure-maven-plugin\index.html
) else (
    echo.
    echo Tests completed with some failures. Opening report...
    start target\site\allure-maven-plugin\index.html
)

pause
