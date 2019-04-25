@echo off
::
:: Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
::
:: This Source Code Form is subject to the terms of the
:: Mozilla Public License, v. 2.0. If a copy of the MPL
:: was not distributed with this file, You can obtain one at
:: http://mozilla.org/MPL/2.0/.
::

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

set MELODIC_CONFIG_DIR=.

setlocal
set JAVA_OPTS= -Djavax.net.ssl.trustStore=..\config-files\broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12 
rem -Djavax.net.debug=all
rem -Djavax.net.debug=ssl,handshake,record

java %JAVA_OPTS% -classpath "target\classes;target\dependency\*" eu.melodic.event.brokerclient.BrokerClientApp %*

endlocal
