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

java -classpath "target\classes;target\dependency\*;conf;jars\*" eu.melodic.event.baguette.server.BaguetteServer %*