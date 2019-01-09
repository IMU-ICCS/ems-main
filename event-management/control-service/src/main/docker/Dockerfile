FROM java:8
VOLUME /tmp
ADD control-service.jar control-service.jar
RUN bash -c 'touch /control-service.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'

ENV MELODIC_CONFIG_DIR=/config
ENV PAASAGE_CONFIG_DIR=/config

ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/control-service.jar"]