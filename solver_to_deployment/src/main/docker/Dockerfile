FROM java:8-alpine
VOLUME /tmp
ADD solver-to-deployment.jar solver-to-deployment.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /solver-to-deployment.jar'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/solver-to-deployment.jar"]