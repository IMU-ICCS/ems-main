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
set PWD=%~dp0
cd %PWD%..
set BASEDIR=%cd%
IF NOT DEFINED MELODIC_CONFIG_DIR set MELODIC_CONFIG_DIR=%BASEDIR%\config-files
IF NOT DEFINED PAASAGE_CONFIG_DIR set PAASAGE_CONFIG_DIR=%BASEDIR%\config-files
IF NOT DEFINED JARS_DIR set JARS_DIR=%BASEDIR%\control-service\target

java -Djasypt.encryptor.password=%JASYPT_PASSWORD% -cp %JARS_DIR%\control-service.jar -Dloader.main=eu.melodic.event.control.util.JwtTokenUtil -Dlogging.level.ROOT=WARN -Dlogging.level.eu.melodic.event.control.util.JwtTokenUtil=INFO org.springframework.boot.loader.PropertiesLauncher %*

cd %PWD%
endlocal