FROM openjdk:8-alpine
ADD utility-generator-standalone.jar utility-generator-standalone.jar

ENV LD_LIBRARY_PATH /usr/local/lib/
# setting ENV variable for the config dir, assuming it is mounted in the Docker RUN command
ENV MELODIC_CONFIG_DIR config/

# Morphemic-way of starting - does not work locally on Mac
#COPY ./run.sh .
#RUN bash -c 'chmod +rx run.sh'
#ENTRYPOINT ["./run.sh"]

# alternative way of starting
ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/config/common/melodic-truststore.p12", "-Djavax.net.ssl.trustStorePassword=melodic", "-Djavax.net.ssl.trustStoreType=pkcs12", "-Dbroker_properties_configuration_file_location=/config/eu.melodic.event.brokerclient.properties", "-XX:ErrorFile=/logs/java_error%p.log", "-XX:HeapDumpPath=/logs", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "utility-generator-standalone.jar"]
