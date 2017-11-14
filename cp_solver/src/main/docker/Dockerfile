FROM java:8
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN bash -c 'touch /cp-solver.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/cp-solver.jar"]