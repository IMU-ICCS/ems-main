#!/bin/bash

# Change directory to VMS client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Run VMS Server
java -classpath "target/classes:target/dependency/*:conf:jars/*" eu.melodic.upperware.discovery.server.VmsDiscoveryServer $*
cd $PWD
