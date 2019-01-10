@echo off

setlocal
set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\conf
set PAASAGE_CONFIG_DIR=%curdir%\conf

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

rem set JAVA_OPTS=-Djavax.net.debug=all
java %JAVA_OPTS% -classpath "%MELODIC_CONFIG_DIR%;target\classes;target\dependency\*" eu.melodic.event.baguette.client.BaguetteClient %*
endlocal