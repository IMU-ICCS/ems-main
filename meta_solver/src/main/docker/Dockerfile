FROM java:8
VOLUME /tmp
ADD meta-solver.jar meta-solver.jar
RUN bash -c 'touch /meta-solver.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/meta-solver.jar"]