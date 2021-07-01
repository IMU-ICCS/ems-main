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
IF NOT DEFINED LOGS_DIR set LOGS_DIR=%BASEDIR%\logs
IF NOT DEFINED PUBLIC_DIR set PUBLIC_DIR=%BASEDIR%\public

:: Import MULE certificate
set MULE_CERT=%MELODIC_CONFIG_DIR%\mule-server.crt
if exist %MULE_CERT% (
    echo importing mule certificate
    keytool -noprompt -storepass changeit -import -alias mule -keystore "%JAVA_HOME%\jre\lib\security\cacerts" -file %MULE_CERT%
    echo importing mule certificate completed
) else (
    echo mule certificate not found: %MULE_CERT%
)

:: Initialize keystores and certificate
:: Uncomment next line to generate BrokerCEP keystore, truststore and certificate before EMS server launch
:: Modifying 'initialize-keystores.bat' script you can customize the certificate generation
::CALL bin\initialize-keystores.bat

:: Read JASYPT password (decrypts encrypted configuration settings)
set JASYPT_PASSWORD=password
if "%JASYPT_PASSWORD%"=="" (
    set /p JASYPT_PASSWORD="Configuration Password: "
)
:: Use this online service to encrypt/decrypt passwords:
:: https://www.devglan.com/online-tools/jasypt-online-encryption-decryption

:: check logger configuration
if "%LOG_CONFIG_FILE%"=="" (
    set LOG_CONFIG_FILE=%MELODIC_CONFIG_DIR%\logback-conf\logback-spring.xml
)
echo Using logback config.: %LOG_CONFIG_FILE%
if "%LOG_FILE%"=="" (
    set LOG_FILE=%LOGS_DIR%\ems.log
)

:: create symbolic link to ems.log
mkdir %PUBLIC_DIR%\logs
::ln -s $LOG_FILE $PUBLIC_DIR/logs/ems.log

:: Waiting CDO to come up...
if exist %MELODIC_CONFIG_DIR%\wait-for-cdo.bat (
    echo "Waiting CDO server to start..."
    %MELODIC_CONFIG_DIR%\wait-for-cdo.bat
)

:: Run EMS server
rem Uncomment next line to set JAVA runtime options
rem set JAVA_OPTS=-Djavax.net.debug=all

echo MELODIC_CONFIG_DIR=%MELODIC_CONFIG_DIR%
echo Starting EMS server...
rem Use when Esper is packaged in control-service.jar
rem java %JAVA_OPTS% -Djasypt.encryptor.password=%JASYPT_PASSWORD% -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/urandom -jar %JARS_DIR%\control-service.jar --logging.config=file:%LOG_CONFIG_FILE%

rem Use when Esper is NOT packaged in control-service.jar
java %JAVA_OPTS% -Djasypt.encryptor.password=%JASYPT_PASSWORD% -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/urandom -cp %JARS_DIR%\control-service.jar -Dloader.path=%JARS_DIR%\esper-7.1.0.jar org.springframework.boot.loader.PropertiesLauncher -nolog --logging.config=file:%LOG_CONFIG_FILE%

rem e.g. --spring.config.location=%MELODIC_CONFIG_DIR%\
rem e.g. --spring.config.name=application.properties

cd %PWD%
endlocal