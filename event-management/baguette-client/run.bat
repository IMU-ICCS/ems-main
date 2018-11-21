@echo off
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\src\main\resources
set PAASAGE_CONFIG_DIR=%curdir%\src\main\resources

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

java -classpath "target\classes;target\dependency\*" eu.melodic.event.baguette.client.BaguetteClient %*