@echo off
echo *********************
echo run_jar.bat Begins...
echo *********************

REM Store the original directory
SET "ORIGINAL_DIR=%CD%"

REM Change to the directory containing the JAR files and set directories
CD /D "%ORIGINAL_DIR%/../../quarkus-camel-demo/quarkus-camel-sftp/target" || (
    echo Error: Failed to change directory to quarkus-camel-sftp/target
    exit /b 1
)
SET "quarkus-camel-sftp=%CD%"

REM Change to the directory containing the JAR files and set directories
CD /D "%ORIGINAL_DIR%/../../quarkus-camel-demo/quarkus-rest-service-7070/target" || (
    echo Error: Failed to change directory to quarkus-rest-service-7070/target
    exit /b 1
)
SET "quarkus_rest_service_7070=%CD%"

CD /D "%ORIGINAL_DIR%/../../quarkus-camel-demo/quarkus-rest-service-9090/target" || (
    echo Error: Failed to change directory to quarkus-rest-service-9090/target
    exit /b 1
)
SET "quarkus_rest_service_9090=%CD%"

REM Change back to the original directory before running the JAR files
CD /D "%ORIGINAL_DIR%" || (
    echo Error: Failed to return to original directory
    exit /b 1
)

REM Check if the JAR files exist before trying to run them
if not exist "%quarkus-camel-sftp%/quarkus-app/quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus-camel-sftp%/quarkus-app/quarkus-run.jar
    exit /b 1
)


REM Check if the JAR files exist before trying to run them
if not exist "%quarkus_rest_service_7070%/quarkus-app/quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_rest_service_7070%/quarkus-app/quarkus-run.jar
    exit /b 1
)

if not exist "%quarkus_rest_service_9090%/quarkus-app/quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_rest_service_9090%/quarkus-app/quarkus-run.jar
    exit /b 1
)

REM Run the JAR files
START "quarkus-camel-sftp" CMD /c java -jar "%quarkus-camel-sftp%/quarkus-app/quarkus-run.jar"
START "quarkus-rest-service-7070" CMD /c java -jar "%quarkus_rest_service_7070%/quarkus-app/quarkus-run.jar"
START "quarkus-rest-service-9090" CMD /c java -jar "%quarkus_rest_service_9090%/quarkus-app/quarkus-run.jar"

REM Check the error level of the last command
if %ERRORLEVEL% neq 0 (
    echo Error: JAR file execution failed with error code %ERRORLEVEL%.
    exit /b %ERRORLEVEL%
)

REM Return to the original directory
CD /D "%ORIGINAL_DIR%" || (
    echo Error: Failed to return to original directory
    exit /b 1
)