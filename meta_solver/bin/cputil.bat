::
:: Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
::
:: This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
:: If a copy of the MPL was not distributed with this file, You can obtain one at
:: https://www.mozilla.org/en-US/MPL/2.0/
::

:: Usage:
:: cputil.bat "file:/path/to/cp-model.xmi" "/cp-model"

@echo off
set BASEDIR=%~dp0\..
IF NOT DEFINED MELODIC_CONFIG_DIR set MELODIC_CONFIG_DIR=%BASEDIR%\src\main\resources\config
IF NOT DEFINED PAASAGE_CONFIG_DIR set PAASAGE_CONFIG_DIR=%BASEDIR%\src\main\resources

java -cp %BASEDIR%\target\meta-solver.jar -Dloader.main=eu.melodic.upperware.metasolver.util.UtilCpModelImport org.springframework.boot.loader.PropertiesLauncher 1 "file:%1" "%2"
