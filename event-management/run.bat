@echo off
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\config-files
set PAASAGE_CONFIG_DIR=%curdir%\config-files
java -jar control-service\target\control-service.jar --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml

rem Uncomment next line (and comment out the previous one) to run a Broker-CEP tests scenario (BrokerCepServiceTest1..BrokerCepServiceTest5)
rem java -jar -Drun-broker-cep-test=BrokerCepServiceTest5 control-service\target\control-service.jar --logging.config=%MELODIC_CONFIG_DIR%\logback-spring.xml

rem // XXX: Sample JSON request:  { 'camel-model-id': '/camel-new', 'cp-model-id': '' }