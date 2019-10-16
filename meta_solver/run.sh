#!/usr/bin/env bash
#
# Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
# If a copy of the MPL was not distributed with this file, You can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
MELODIC_CONFIG_DIR=$BASEDIR/src/main/resources/config
PAASAGE_CONFIG_DIR=$BASEDIR/src/main/resources

export BASEDIR MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

#mvn spring-boot:run

#LOGGING=--logging.config=file:%MELODIC_CONFIG_DIR%\logback-spring.xml
JAVA_OPTS= -Djavax.net.ssl.trustStore=${MELODIC_CONFIG_DIR}/broker-truststore.p12
JAVA_OPTS=${JAVA_OPTS} -Djavax.net.ssl.trustStorePassword=melodic -Djavax.net.ssl.trustStoreType=pkcs12

#java ${JAVA_OPTS} -Deu.paasage.configdir=. -jar target/meta-solver.jar ${LOGGING}
java ${JAVA_OPTS} -jar target\meta-solver.jar ${LOGGING}
