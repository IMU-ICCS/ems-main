FROM java:8
VOLUME /tmp
ADD solver-to-deployment.jar solver-to-deployment.jar
RUN bash -c 'touch /solver-to-deployment.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/solver-to-deployment.jar"]