@echo off
::
:: Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
::
:: This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless 
:: Esper library is used, in which case it is subject to the terms of General Public License v2.0.
:: If a copy of the MPL was not distributed with this file, you can obtain one at 
:: https://www.mozilla.org/en-US/MPL/2.0/
::

set MELODIC_CONFIG_DIR=.

setlocal
set JAVA_OPTS= -Djavax.net.ssl.trustStore=..\config-files\broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12 
rem -Djavax.net.debug=all
rem -Djavax.net.debug=ssl,handshake,record

java %JAVA_OPTS% -cp . -jar target\broker-client-jar-with-dependencies.jar %*

endlocal
