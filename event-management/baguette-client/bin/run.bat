@echo off
::
:: Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
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
IF NOT DEFINED MELODIC_CONFIG_DIR set MELODIC_CONFIG_DIR=%BASEDIR%\conf
IF NOT DEFINED PAASAGE_CONFIG_DIR set PAASAGE_CONFIG_DIR=%BASEDIR%\conf
IF NOT DEFINED JASYPT_PASSWORD set JASYPT_PASSWORD=melodic

:: Copy dependencies if missing
if exist pom.xml (
    if not exist %BASEDIR%\target\dependency cmd /C "mvn dependency:copy-dependencies"
)

:: Run Baguette Client
set JAVA_OPTS= -Djavax.net.ssl.trustStore=%MELODIC_CONFIG_DIR%\client-broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12 ^
 -Djasypt.encryptor.password=%JASYPT_PASSWORD%
::set JAVA_OPTS=-Djavax.net.debug=all %JAVA_OPTS%

echo MELODIC_CONFIG_DIR=%MELODIC_CONFIG_DIR%
echo Starting baguette client...
java %JAVA_OPTS% -classpath "%MELODIC_CONFIG_DIR%;%BASEDIR%\jars\*;%BASEDIR%\target\classes;%BASEDIR%\target\dependency\*" eu.melodic.event.baguette.client.BaguetteClient %*

cd %PWD%
endlocal