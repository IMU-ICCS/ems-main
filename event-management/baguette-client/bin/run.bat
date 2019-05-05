@echo off
::
:: Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
::
:: This Source Code Form is subject to the terms of the
:: Mozilla Public License, v. 2.0. If a copy of the MPL
:: was not distributed with this file, You can obtain one at
:: http://mozilla.org/MPL/2.0/.
::

CALL bin\initialize-keystores.bat

setlocal
set curdir=%~dp0
set BASEDIR=%curdir%..
set MELODIC_CONFIG_DIR=%BASEDIR%\conf
set PAASAGE_CONFIG_DIR=%BASEDIR%\conf

cd %BASEDIR%

set JAVA_OPTS= -Djavax.net.ssl.trustStore=%MELODIC_CONFIG_DIR%\client-broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12

rem set JAVA_OPTS=-Djavax.net.debug=all
java %JAVA_OPTS% -classpath "conf;jars\*" eu.melodic.event.baguette.client.BaguetteClient %*
endlocal