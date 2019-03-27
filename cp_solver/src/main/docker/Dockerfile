FROM java:8-alpine
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN /bin/sh -c "apk add --no-cache bash"
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-XX:ErrorFile=/logs/java_error%p.log", "-XX:HeapDumpPath=/logs", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/cp-solver.jar"]

