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
if [[ -z $LOGS_DIR ]]; then LOGS_DIR=$BASEDIR/logs; export LOGS_DIR; fi
if [[ -z $PUBLIC_DIR ]]; then PUBLIC_DIR=$BASEDIR/public_resources; export PUBLIC_DIR; fi

# Import MULE certificate
#MULE_CERT=$MELODIC_CONFIG_DIR/mule-server.crt
#if [[ -f ${MULE_CERT} ]]; then
#    echo "importing mule certificate"
#    keytool -noprompt -storepass changeit -import -alias mule -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -file ${MULE_CERT}
#    echo "importing mule certificate completed"
#else
#    echo "mule certificate not found: ${MULE_CERT}"
#fi

# Initialize keystores and certificate
# Uncomment next line to generate BrokerCEP keystore, truststore and certificate before EMS server launch
# Modifying 'initialize-keystores.sh' script you can customize the certificate generation
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

# Check EMS configuration
if [[ -z "$EMS_SECRETS_FILE" ]]; then
  EMS_SECRETS_FILE=$MELODIC_CONFIG_DIR/secrets.properties
fi
if [[ -z "$EMS_CONFIG_LOCATION" ]]; then
  EMS_CONFIG_LOCATION=classpath:rule-templates.yml,optional:file:$MELODIC_CONFIG_DIR/ems-server.yml,optional:file:$MELODIC_CONFIG_DIR/ems-server.properties,optional:file:$MELODIC_CONFIG_DIR/ems.yml,optional:file:$MELODIC_CONFIG_DIR/ems.properties,optional:file:$EMS_SECRETS_FILE
fi

# Check logger configuration
if [[ -z "$LOG_CONFIG_FILE" ]]; then
    LOG_CONFIG_FILE=$MELODIC_CONFIG_DIR/logback-conf/logback-spring.xml
fi
if [[ -z "$LOG_FILE" ]]; then
    LOG_FILE=$LOGS_DIR/ems.log
    export LOG_FILE
fi

# Waiting CDO to come up...
if [[ -z ${EMS_SKIP_WAIT_CDO+x} ]] && [[ -f $MELODIC_CONFIG_DIR/wait-for-cdo.sh ]]; then
    echo "Waiting CDO server to start..."
    $MELODIC_CONFIG_DIR/wait-for-cdo.sh
fi

# Run EMS server
# Uncomment next line to set JAVA runtime options
#JAVA_OPTS=-Djavax.net.debug=all
#export JAVA_OPTS

echo "MELODIC_CONFIG_DIR=${MELODIC_CONFIG_DIR}"
echo "EMS_CONFIG_LOCATION=${EMS_CONFIG_LOCATION}"
echo "IP address=`hostname -I`"
echo "Starting EMS server..."
if [[ -z $RESTART_EXIT_CODE ]]; then RESTART_EXIT_CODE=99; export RESTART_EXIT_CODE; fi
retCode=$RESTART_EXIT_CODE
while :; do
  # Use when Esper is packaged in control-service.jar
  # java $JAVA_OPTS -Djasypt.encryptor.password=$JASYPT_PASSWORD -Duser.timezone=Europe/Athens -Djava.security.egd=file:/dev/urandom -jar $JARS_DIR/control-service/target/control-service.jar "--spring.config.location=${EMS_CONFIG_LOCATION}" "--logging.config=file:$LOG_CONFIG_FILE"

  # Use when Esper is NOT packaged in control-service.jar
  java $EMS_DEBUG_OPTS $JAVA_OPTS -Djasypt.encryptor.password=$JASYPT_PASSWORD -Djava.security.egd=file:/dev/urandom -cp ${JARS_DIR}/control-service.jar -Dloader.path=${JARS_DIR}/esper-7.1.0.jar org.springframework.boot.loader.PropertiesLauncher "--spring.config.location=${EMS_CONFIG_LOCATION}" "--logging.config=file:$LOG_CONFIG_FILE" $*

  retCode=$?
  if [[ $retCode -eq $RESTART_EXIT_CODE ]]; then echo "Restarting EMS server..."; else break; fi
done
echo "EMS server exited"

# Extra parameters
# e.g. --spring.config.location=$MELODIC_CONFIG_DIR
# e.g. --spring.config.name=application.properties

cd $PREVWORKDIR