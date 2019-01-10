@echo off
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\..\config-files
set PAASAGE_CONFIG_DIR=%curdir%\..\config-files
java -jar target\control-service.jar --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml
