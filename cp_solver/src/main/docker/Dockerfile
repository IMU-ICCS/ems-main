FROM java:8
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN bash -c 'touch /cp-solver.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-XX:ErrorFile=/logs/java_error%p.log", "-XX:HeapDumpPath=/logs", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/cp-solver.jar"]