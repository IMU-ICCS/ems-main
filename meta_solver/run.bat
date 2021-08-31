::
:: Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
::
:: This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
:: If a copy of the MPL was not distributed with this file, You can obtain one at
:: https://www.mozilla.org/en-US/MPL/2.0/
::

@echo off
set curdir=%~dp0
IF NOT DEFINED MELODIC_CONFIG_DIR set MELODIC_CONFIG_DIR=%curdir%\src\main\resources\config
IF NOT DEFINED PAASAGE_CONFIG_DIR set PAASAGE_CONFIG_DIR=%curdir%\src\main\resources
IF NOT DEFINED EXTRA_TS_DIR set EXTRA_DIR=%MELODIC_CONFIG_DIR% fi

rem mvn spring-boot:run

rem set LOGGING=--logging.config=file:%MELODIC_CONFIG_DIR%\logback-spring.xml
set JAVA_OPTS= -Djavax.net.ssl.trustStore=%EXTRA_TS_DIR%\broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12

rem java %JAVA_OPTS% -Deu.paasage.configdir=. -jar target\meta-solver.jar %LOGGING%
java %JAVA_OPTS% -jar target\meta-solver.jar %LOGGING%
