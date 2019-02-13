#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

# Read JASYPT password (decrypts encrypted configuration settings)
JASYPT_PASSWORD=password
if [[ -z "$JASYPT_PASSWORD" ]]; then
    printf "Configuration Password: "
    read -s JASYPT_PASSWORD
fi

export JASYPT_PASSWORD

# check logger configuration
if [ -z "$LOG_CONFIG_FILE" ]
    then LOG_CONFIG_FILE=$MELODIC_CONFIG_DIR/logback-spring.xml
fi

$MELODIC_CONFIG_DIR/wait-for-cdo.sh && java -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=esper-7.1.0.jar -cp control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=file:$LOG_CONFIG_FILE
