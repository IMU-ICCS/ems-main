@echo off
::
:: Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
::
:: This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
:: Esper library is used, in which case it is subject to the terms of General Public License v2.0.
:: If a copy of the MPL was not distributed with this file, you can obtain one at
:: https://www.mozilla.org/en-US/MPL/2.0/
::

setlocal
set PWD=%cd%
cd %~dp0..
set BASEDIR=%cd%
IF NOT DEFINED MELODIC_CONFIG_DIR set MELODIC_CONFIG_DIR=%BASEDIR%\config-files
IF NOT DEFINED PAASAGE_CONFIG_DIR set PAASAGE_CONFIG_DIR=%BASEDIR%\config-files

:: Copy dependencies if missing
if exist pom.xml (
    if not exist %BASEDIR%\control-service\target\dependency cmd /C "cd control-service && mvn dependency:copy-dependencies"
)

java -classpath %BASEDIR%/control-service/target/classes;%BASEDIR%/control-service/target/dependency/* eu.melodic.event.control.util.CpModelHelper %*
rem Usage: cp2cdo <file> <cdo-resource>

cd %PWD%
endlocal