FROM java:8
VOLUME /tmp
ADD adapter.jar adapter.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/adapter.jar", "--MELODIC_CONFIG_DIR=/config", "--PAASAGE_CONFIG_DIR=/config"]