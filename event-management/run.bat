@echo off

setlocal
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\config-files
set PAASAGE_CONFIG_DIR=%curdir%\config-files

rem Uncomment next line to set JAVA runtime options
rem set JAVA_OPTS=-Djavax.net.debug=all

rem Uncomment next line to run a Broker-CEP test scenario (BrokerCepServiceTest1..BrokerCepServiceTest5)
rem set BROKER_CEP_TEST=-Drun-broker-cep-test=BrokerCepServiceTest5

rem Use when Esper is packaged in control-service.jar
rem java %JAVA_OPTS% -jar %BROKER_CEP_TEST% control-service\target\control-service.jar --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml
rem Use when Esper is NOT packaged in control-service.jar
java %JAVA_OPTS% -cp control-service\target\control-service.jar -Dloader.path=control-service\target\esper-7.1.0.jar %BROKER_CEP_TEST% org.springframework.boot.loader.PropertiesLauncher --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml
rem e.g. --spring.config.location=%MELODIC_CONFIG_DIR%\
rem e.g. --spring.config.name=application.properties

endlocal
