FROM java:8
VOLUME /tmp
ADD generator.jar generator.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Deu.paasage.configdir=/config","-jar","/generator.jar", "--MELODIC_CONFIG_DIR=/config", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5007"]