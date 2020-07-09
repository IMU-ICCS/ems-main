#!/bin/sh

/config/wait-for-cdo.sh && java \
-Duser.timezone=Europe/Warsaw \
-Djava.security.egd=file:/dev/./urandom \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
-jar functionizer-testing-tool.jar
