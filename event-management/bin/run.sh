#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

# Change directory to EMS home
PREVWORKDIR=`pwd`
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
cd ${BASEDIR}
if [[ -z $MELODIC_CONFIG_DIR ]]; then MELODIC_CONFIG_DIR=$BASEDIR/config-files; export MELODIC_CONFIG_DIR; fi
if [[ -z $PAASAGE_CONFIG_DIR ]]; then PAASAGE_CONFIG_DIR=$BASEDIR/config-files; export PAASAGE_CONFIG_DIR; fi
if [[ -z $JAR_PATH ]]; then JAR_PATH=$BASEDIR/control-service/target; export JAR_PATH; fi

# Import MULE certificate
MULE_CERT=$MELODIC_CONFIG_DIR/mule-server.crt
if [[ -f ${MULE_CERT} ]]; then
    echo "importing mule certificate"
    keytool -noprompt -storepass changeit -import -alias mule -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -file ${MULE_CERT}
    echo "importing mule certificate completed"
else
    echo "mule certificate not found: ${MULE_CERT}"
fi

# Initialize keystores and certificate
# Uncomment next line to generate BrokerCEP keystore, truststore and certificate before EMS server launch
# Modifying 'initialize-keystores.bat' script you can customize the certificate generation
#./bin/initialize-keystores.sh

# Read JASYPT password (decrypts encrypted configuration settings)
JASYPT_PASSWORD=password
if [[ -z "$JASYPT_PASSWORD" ]]; then
    printf "Configuration Password: "
    read -s JASYPT_PASSWORD
fi
# Use this online service to encrypt/decrypt passwords:
# https://www.devglan.com/online-tools/jasypt-online-encryption-decryption

export JASYPT_PASSWORD

# check logger configuration
if [[ -z "$LOG_CONFIG_FILE" ]]; then
    LOG_CONFIG_FILE=$MELODIC_CONFIG_DIR/logback-spring.xml
fi

# Waiting CDO to come up...
if [[ -f $MELODIC_CONFIG_DIR/wait-for-cdo.sh ]]; then
    echo "Waiting CDO server to start..."
    $MELODIC_CONFIG_DIR/wait-for-cdo.sh
fi

# Run EMS server
# Uncomment next line to set JAVA runtime options
# JAVA_OPTS=-Djavax.net.debug=all
export JAVA_OPTS

echo "MELODIC_CONFIG_DIR=${MELODIC_CONFIG_DIR}"
echo "Starting EMS server..."
# Use when Esper is packaged in control-service.jar
# java $JAVA_OPTS -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/urandom -jar $JAR_PATH/control-service/target/control-service.jar --logging.config=file:$LOG_CONFIG_FILE

# Use when Esper is NOT packaged in control-service.jar
java $JAVA_OPTS -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/urandom -cp ${JAR_PATH}/control-service.jar -Dloader.path=${JAR_PATH}/esper-7.1.0.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=file:$LOG_CONFIG_FILE 

# Extra parameters
# e.g. --spring.config.location=$MELODIC_CONFIG_DIR
# e.g. --spring.config.name=application.properties

cd $PREVWORKDIR