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

# Read JASYPT password (decrypts encrypted configuration settings)
JASYPT_PASSWORD=password
if [[ -z "$JASYPT_PASSWORD" ]]; then
    printf "Configuration Password: "
    read -s JASYPT_PASSWORD
fi

export BASEDIR MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR JASYPT_PASSWORD

#$MELODIC_CONFIG_DIR/wait-for-cdo.sh && java -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=$BASEDIR/esper-7.1.0.jar -cp $BASEDIR/control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=$MELODIC_CONFIG_DIR/logback-spring.xml
$MELODIC_CONFIG_DIR/wait-for-cdo.sh && java -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=$BASEDIR/esper-7.1.0.jar -cp $BASEDIR/control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=$MELODIC_CONFIG_DIR/logback-conf/logback-ems.xml
