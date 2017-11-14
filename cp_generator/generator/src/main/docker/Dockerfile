FROM java:8
VOLUME /tmp
ADD generator.jar generator.jar

ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/generator.jar"]