@echo off
::
:: Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
::
:: This Source Code Form is subject to the terms of the
:: Mozilla Public License, v. 2.0. If a copy of the MPL
:: was not distributed with this file, You can obtain one at
:: http://mozilla.org/MPL/2.0/.
::

setlocal
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\conf
set PAASAGE_CONFIG_DIR=%curdir%\conf

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

rem set JAVA_OPTS=-Djavax.net.debug=all
java %JAVA_OPTS% -classpath "%MELODIC_CONFIG_DIR%;target\classes;target\dependency\*" eu.melodic.event.baguette.client.BaguetteClient %*
endlocal