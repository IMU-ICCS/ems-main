FROM java:8-alpine
VOLUME /tmp
ADD gui-backend.jar gui-backend.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /gui-backend.jar'
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/gui-backend.jar"]