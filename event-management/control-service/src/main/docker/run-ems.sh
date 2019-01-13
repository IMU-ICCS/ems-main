#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
MELODIC_CONFIG_DIR=$BASEDIR/config
PAASAGE_CONFIG_DIR=$BASEDIR/config

export BASEDIR MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

java -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=$BASEDIR/esper-7.1.0.jar -cp $BASEDIR/control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=$MELODIC_CONFIG_DIR/logback-spring.xml
