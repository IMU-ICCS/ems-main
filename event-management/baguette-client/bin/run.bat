@echo off

setlocal
cd ..
set curdir=%~dp0
set BASEDIR=%curdir%
set MELODIC_CONFIG_DIR=%BASEDIR%\conf
set PAASAGE_CONFIG_DIR=%BASEDIR%\conf

rem set JAVA_OPTS=-Djavax.net.debug=all
java %JAVA_OPTS% -classpath "conf;jars\*" eu.melodic.event.baguette.client.BaguetteClient %*
endlocal