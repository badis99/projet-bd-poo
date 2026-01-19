@echo off
if not exist out mkdir out
echo Finding source files...
dir /s /B src\*.java > sources.txt
echo Compiling...
javac -d out -cp "lib\*" @sources.txt
if %errorlevel% neq 0 (
    echo Compilation FAILED.
    exit /b %errorlevel%
)
del sources.txt
echo Copying resources...
xcopy /s /y src\*.properties out\
xcopy /s /y src\*.fxml out\
echo Compilation SUCCESSFUL.
