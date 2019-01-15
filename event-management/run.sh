#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

# Get EMS home directory
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
MELODIC_CONFIG_DIR=$BASEDIR/config-files
PAASAGE_CONFIG_DIR=$BASEDIR/config-files

export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

# Uncomment next line to set JAVA runtime options
# JAVA_OPTS=-Djavax.net.debug=all

# Read JASYPT password (decrypts encrypted configuration settings)
JASYPT_PASSWORD=password
if [[ -z "$JASYPT_PASSWORD" ]]; then
    printf "Configuration Password: "
    read -s JASYPT_PASSWORD
fi

# Uncomment next line to run a Broker-CEP test scenario (BrokerCepServiceTest1..BrokerCepServiceTest5)
# BROKER_CEP_TEST=-Drun-broker-cep-test=BrokerCepServiceTest5

export JAVA_OPTS BROKER_CEP_TEST

# Use when Esper is packaged in control-service.jar
# java $JAVA_OPTS -jar $BROKER_CEP_TEST $BASEDIR/control-service/target/control-service.jar --logging.config=$MELODIC_CONFIG_DIR/logback-spring.xml

# Use when Esper is NOT packaged in control-service.jar
java $JAVA_OPTS -cp $BASEDIR/control-service/target/control-service.jar -Dloader.path=$BASEDIR/control-service/target/esper-7.1.0.jar $BROKER_CEP_TEST org.springframework.boot.loader.PropertiesLauncher --logging.config=$MELODIC_CONFIG_DIR/logback-spring.xml

# e.g. --spring.config.location=$MELODIC_CONFIG_DIR
# e.g. --spring.config.name=application.properties
