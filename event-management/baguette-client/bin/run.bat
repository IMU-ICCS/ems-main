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
set PWD=%~dp0
cd %PWD%..
set BASEDIR=%cd%
set MELODIC_CONFIG_DIR=%BASEDIR%\conf
set PAASAGE_CONFIG_DIR=%BASEDIR%\conf

:: Copy dependencies if missing
if exist pom.xml (
    if not exist %BASEDIR%\target\dependency cmd /C "mvn dependency:copy-dependencies"
)

:: Initialize keystores and certificate
CALL bin\initialize-keystores.bat

:: Run Baguette Client
set JAVA_OPTS= -Djavax.net.ssl.trustStore=%MELODIC_CONFIG_DIR%\client-broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12
::set JAVA_OPTS=-Djavax.net.debug=all %JAVA_OPTS%

echo MELODIC_CONFIG_DIR=%MELODIC_CONFIG_DIR%
echo Starting baguette client...
java %JAVA_OPTS% -classpath "%MELODIC_CONFIG_DIR%;%BASEDIR%\jars\*;%BASEDIR%\target\classes;%BASEDIR%\target\dependency\*" eu.melodic.event.baguette.client.BaguetteClient %*

cd %PWD%
endlocal