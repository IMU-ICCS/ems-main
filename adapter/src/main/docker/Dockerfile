FROM java:8-alpine
VOLUME /tmp
ADD adapter.jar adapter.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/adapter.jar"]