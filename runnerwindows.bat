@echo off
setlocal EnableDelayedExpansion

REM Create required directories
if not exist out mkdir out

REM Colors for output
set "RED=[91m"
set "GREEN=[92m"
set "BLUE=[94m"
set "NC=[0m"

REM Function to compile the project
:compile
echo !BLUE!Compiling project...!NC!
if not exist out mkdir out
dir /s /B src\*.java > sources.txt
javac -d out @sources.txt
del sources.txt

if !ERRORLEVEL! EQU 0 (
    echo !GREEN!Compilation successful!!NC!
    exit /b 0
) else (
    echo !RED!Compilation failed!!NC!
    exit /b 1
)

REM Function to run the project
:run
if not exist out (
    echo !RED!Project is not compiled. Compiling first...!NC!
    call :compile
)

echo !BLUE!Running DaisukeClinic...!NC!
java -cp out daisukeclinic.Main
exit /b

REM Function to clean compiled files
:clean
echo !BLUE!Cleaning compiled files...!NC!
if exist out rmdir /s /q out
echo !GREEN!Clean complete!!NC!
exit /b

REM Function to run all steps
:run_all
call :clean
call :compile && call :run
exit /b

REM Help message
:show_help
echo DaisukeClinic Runner Script
echo Usage: runnerwindows.bat [command]
echo.
echo Commands:
echo   compile    Compile the project
echo   run        Run the project (will compile if needed)
echo   clean      Clean compiled files
echo   help       Show help message
echo.
echo No arguments will clean, compile and run the project
exit /b

REM Main script logic
if "%~1"=="" (
    call :run_all
) else if "%~1"=="compile" (
    call :compile
) else if "%~1"=="run" (
    call :run
) else if "%~1"=="clean" (
    call :clean
) else if "%~1"=="help" (
    call :show_help
) else (
    echo !RED!Unknown command: %~1!NC!
    call :show_help
    exit /b 1
)