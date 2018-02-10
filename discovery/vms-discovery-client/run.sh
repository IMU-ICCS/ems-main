#!/bin/bash

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

if [ ! -d "target/dependency" ]; then
  mvn dependency:copy-dependencies
fi

java -classpath "target/classes:target/dependency/*" eu.melodic.upperware.discovery.client.VmsDiscoveryClient $*
cd $PWD
