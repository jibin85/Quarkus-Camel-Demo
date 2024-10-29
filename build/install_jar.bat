@echo off
SET work_dir=%CD%

REM Change to the project directory
CD /D "../../quarkus-camel-demo" || exit /b %ERRORLEVEL%

REM Clean and package the application using Maven, skipping tests
CALL mvn clean package -DskipTests || exit /b %ERRORLEVEL%

REM Return to the original directory
CD /D "%work_dir%"

run_jar.bat
