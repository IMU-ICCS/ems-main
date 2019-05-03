@echo off
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\src\main\resources\config
set PAASAGE_CONFIG_DIR=%curdir%\src\main\resources

rem Run UtilCpModelImport
java -classpath "target\classes;target\lib\*;target\dependency\*" eu.melodic.upperware.metasolver.util.UtilCpModelImport %*