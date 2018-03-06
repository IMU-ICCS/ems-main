#!/bin/bash

# Change directory to VMS client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Run VMS Server
java -classpath "target/classes:target/dependency/*:conf:jars/*" eu.melodic.upperware.discovery.server.VmsDiscoveryServer $*  >> /logs/vms-server.log 2>&1
cd $PWD
