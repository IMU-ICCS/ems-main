#!/bin/sh

/config/wait-for-cdo.sh && java -Djavax.net.ssl.trustStore=/config/common/melodic-truststore.p12 -Djavax.net.ssl.trustStorePassword=melodic -Djavax.net.ssl.trustStoreType=pkcs12 -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dbroker_properties_configuration_file_location=/config/eu.melodic.event.brokerclient.properties -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar pt-solver.jar
