@echo off
echo ===============================================
echo       Banking Application - Quick Run
echo ===============================================

REM Check if build directory exists
if not exist "build" (
    echo ❌ Build directory not found!
    echo Please run compile_and_run.bat first.
    pause
    exit /b 1
)

REM Run the application
echo Starting Banking Application...
echo.
cd build
java BankingApp

REM Return to original directory
cd ..

echo.
echo Application closed.
pause
