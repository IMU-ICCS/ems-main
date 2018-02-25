#!/bin/bash

# Change directory to VMS client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Download dependencies if needed
if [ ! -d "target/dependency" ]; then
  mvn dependency:copy-dependencies
fi

# Run VMS Server
java -classpath "target/classes:target/dependency/*" eu.melodic.upperware.discovery.server.VmsDiscoveryServer $*
cd $PWD
