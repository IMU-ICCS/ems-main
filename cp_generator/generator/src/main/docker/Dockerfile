FROM java:8
VOLUME /tmp
ADD generator.jar generator.jar
ENTRYPOINT ["java", "-jar", "/generator.jar", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw"]