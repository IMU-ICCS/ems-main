FROM java:8-alpine
VOLUME /tmp
ADD meta-solver.jar meta-solver.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /meta-solver.jar'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/meta-solver.jar"]