#!/usr/bin/env bash
#
# Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

# Change directory to EMS home
PREVWORKDIR=`pwd`
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
cd ${BASEDIR}
if [[ -z $MELODIC_CONFIG_DIR ]]; then MELODIC_CONFIG_DIR=$BASEDIR/config-files; export MELODIC_CONFIG_DIR; fi
if [[ -z $PAASAGE_CONFIG_DIR ]]; then PAASAGE_CONFIG_DIR=$BASEDIR/config-files; export PAASAGE_CONFIG_DIR; fi
if [[ -z $JARS_DIR ]]; then JARS_DIR=$BASEDIR/control-service/target; export JARS_DIR; fi

java -Djasypt.encryptor.password=$JASYPT_PASSWORD  -cp ${JARS_DIR}/control-service.jar -Dloader.main=eu.melodic.event.control.util.JwtTokenUtil -Dlogging.level.ROOT=WARN -Dlogging.level.eu.melodic.event.control.util.JwtTokenUtil=INFO org.springframework.boot.loader.PropertiesLauncher $*

cd $PREVWORKDIR