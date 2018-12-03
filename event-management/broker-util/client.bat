@echo off

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

setlocal
set JAVA_OPTS= -Djavax.net.ssl.keyStore=..\config-files\broker-keystore.p12 ^
 -Djavax.net.ssl.keyStoreType=pkcs12 ^
 -Djavax.net.ssl.keyStorePassword=melodic ^
 -Djavax.net.ssl.trustStore=..\config-files\broker-truststore.p12 ^
 -Djavax.net.ssl.trustStorePassword=melodic ^
 -Djavax.net.ssl.trustStoreType=pkcs12 
rem -Djavax.net.debug=all
rem -Djavax.net.debug=ssl,handshake,record

java %JAVA_OPTS% -classpath "target\classes;target\dependency\*" eu.melodic.event.brokerutil.BrokerClient %*

endlocal
