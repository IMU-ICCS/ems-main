@echo off
::
:: Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
::
:: This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
:: If a copy of the MPL was not distributed with this file, You can obtain one at
:: https://www.mozilla.org/en-US/MPL/2.0/
::

set curdir=%~dp0
set MELODIC_CONFIG_DIR=%curdir%\src\main\resources\config
set PAASAGE_CONFIG_DIR=%curdir%\src\main\resources

rem Run UtilCpModelImport
java -classpath "target\classes;target\lib\*;target\dependency\*" eu.melodic.upperware.metasolver.util.UtilCpModelImport %*