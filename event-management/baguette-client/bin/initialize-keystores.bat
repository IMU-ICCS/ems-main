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
set MELODIC_CONFIG_DIR=%curdir%\config-files
set PAASAGE_CONFIG_DIR=%curdir%\config-files

:: Get IP addresses
echo Resolving Public and Default IP addresses...
for /f %%i in ('java -jar jars\util\util-2.1.0-SNAPSHOT-jar-with-dependencies.jar -nolog public')  do set {PUBLIC_IP}=%%i
for /f %%i in ('java -jar jars\util\util-2.1.0-SNAPSHOT-jar-with-dependencies.jar -nolog default') do set {DEFAULT_IP}=%%i

IF "%{PUBLIC_IP}%" == "" set {PUBLIC_IP}=127.0.0.1
IF "%{PUBLIC_IP}%" == "null" set {PUBLIC_IP}=127.0.0.1
IF "%{DEFAULT_IP}%" == "" set {DEFAULT_IP}=127.0.0.1
IF "%{DEFAULT_IP}%" == "null" set {DEFAULT_IP}=127.0.0.1

echo PUBLIC_IP=%{PUBLIC_IP}%
echo DEFAULT_IP=%{DEFAULT_IP}%

:: Keystore initialization settings
set KEY_GEN_ALG=RSA
set KEY_SIZE=2048
set KEY_ALIAS=%{DEFAULT_IP}%
set START_DATE=-1d
set VALIDITY=3650
set DN=CN=%{DEFAULT_IP}%,OU=Information Management Unit (IMU),O=Institute of Communication and Computer Systems (ICCS),L=Athens,ST=Attika,C=GR
set EXT_SAN=SAN=dns:localhost,ip:127.0.0.1,ip:%{DEFAULT_IP}%,ip:%{PUBLIC_IP}%
set KEYSTORE=%MELODIC_CONFIG_DIR%\client-broker-keystore.p12
set TRUSTSTORE=%MELODIC_CONFIG_DIR%\client-broker-truststore.p12
set CERTIFICATE=%MELODIC_CONFIG_DIR%\client-broker.crt
set KEYSTORE_TYPE=PKCS12
set KEYSTORE_PASS=melodic

:: Keystores initialization
echo Generating key pair and certificate...
keytool -delete -alias %KEY_ALIAS% -keystore %KEYSTORE% -storetype %KEYSTORE_TYPE% -storepass %KEYSTORE_PASS%
keytool -genkey -keyalg %KEY_GEN_ALG% -keysize %KEY_SIZE% -alias %KEY_ALIAS% -startdate %START_DATE% -validity %VALIDITY% -dname "%DN%" -ext "%EXT_SAN%" -keystore %KEYSTORE% -storetype %KEYSTORE_TYPE% -storepass %KEYSTORE_PASS%

echo Exporting certificate to file...
del /Q %CERTIFICATE%
keytool -export -alias %KEY_ALIAS% -file %CERTIFICATE% -keystore %KEYSTORE% -storetype %KEYSTORE_TYPE% -storepass %KEYSTORE_PASS%

echo Importing certificate to trust store...
keytool -delete -alias %KEY_ALIAS% -keystore %TRUSTSTORE% -storetype %KEYSTORE_TYPE% -storepass %KEYSTORE_PASS%
keytool -import -noprompt -file %CERTIFICATE% -alias %KEY_ALIAS% -keystore %TRUSTSTORE% -storetype %KEYSTORE_TYPE% -storepass %KEYSTORE_PASS%

echo Key store, trust stores and certificate are ready.
endlocal
