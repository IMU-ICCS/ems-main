FROM java:8
VOLUME /tmp
ADD generator.jar generator.jar

ENTRYPOINT ["java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/generator.jar"]