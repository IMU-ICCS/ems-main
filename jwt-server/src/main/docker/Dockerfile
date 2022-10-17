FROM openjdk:8-alpine
VOLUME /tmp
ADD jwt-server.jar jwt-server.jar
RUN /bin/sh -c "apk add --no-cache bash"
ENTRYPOINT ["java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/jwt-server.jar"]