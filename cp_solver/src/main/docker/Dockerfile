FROM java:8
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN bash -c 'touch /cp-solver.jar'
RUN bash -c 'apt-get install -y netcat ; until nc -z $CDO_HOST $CDO_PORT ; do echo "$(date) - waiting for cdo on : $CDO_HOST $CDO_PORT"; sleep 1 ; done'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-jar", "/cp-solver.jar"]