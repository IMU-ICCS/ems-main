@echo off

setlocal
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\config-files
set PAASAGE_CONFIG_DIR=%curdir%\config-files

rem set JAVA_OPTS=-Djavax.net.debug=all 
java %JAVA_OPTS% -jar control-service\target\control-service.jar --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml
rem e.g. --spring.config.location=%MELODIC_CONFIG_DIR%\
rem e.g. --spring.config.name=application.properties

rem Uncomment next line (and comment out the previous one) to run a Broker-CEP tests scenario (BrokerCepServiceTest1..BrokerCepServiceTest5)
rem java -jar -Drun-broker-cep-test=BrokerCepServiceTest5 control-service\target\control-service.jar --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml
endlocal
