FROM java:8
VOLUME /tmp
ADD adapter.jar adapter.jar
RUN bash -c 'touch /app.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/adapter.jar"]