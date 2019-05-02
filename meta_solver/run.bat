::
:: Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
::
:: This Source Code Form is subject to the terms of the
:: Mozilla Public License, v. 2.0. If a copy of the MPL
:: was not distributed with this file, You can obtain one at
:: http://mozilla.org/MPL/2.0/.
::

@echo off
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\src\main\resources\config
set PAASAGE_CONFIG_DIR=%curdir%\src\main\resources

rem mvn spring-boot:run

rem set LOGGING=--logging.config=file:%MELODIC_CONFIG_DIR%\logback-spring.xml
set JAVA_OPTS= -Djavax.net.ssl.trustStore=%MELODIC_CONFIG_DIR%\broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12

rem java %JAVA_OPTS% -Deu.paasage.configdir=. -jar target\meta-solver.jar %LOGGING%
java %JAVA_OPTS% -jar target\meta-solver.jar %LOGGING%

rem Run UtilCpModelImport
rem java -classpath "target\classes;target\lib\*;target\dependency\*" eu.melodic.upperware.metasolver.util.UtilCpModelImport %*