#!/bin/sh

keytool -noprompt -storepass changeit -import -alias mule -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -file /config/mule-server.crt
/config/wait-for-cdo.sh && java -XX:ErrorFile=/logs/java_error%p.log -XX:HeapDumpPath=/logs -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar cp-solver.jar
