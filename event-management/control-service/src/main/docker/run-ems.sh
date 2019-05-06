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
#MELODIC_CONFIG_DIR=$BASEDIR/config-files
#PAASAGE_CONFIG_DIR=$BASEDIR/config-files
#export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

echo "importing mule certificate"
keytool -noprompt -storepass changeit -import -alias mule -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -file /config/mule-server.crt
echo "importing mule certificate completed"

# Initialize keystores and certificate
./bin/initialize-keystores.sh

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

# Run EMS server
# Uncomment next line to set JAVA runtime options
# JAVA_OPTS=-Djavax.net.debug=all
export JAVA_OPTS

echo "MELODIC_CONFIG_DIR=${MELODIC_CONFIG_DIR}"
echo "Starting EMS server..."

$MELODIC_CONFIG_DIR/wait-for-cdo.sh && java -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=esper-7.1.0.jar -cp control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=file:$LOG_CONFIG_FILE

cd $PREVWORKDIR