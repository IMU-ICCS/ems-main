FROM java:8
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN bash -c 'touch /cp-solver.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
CMD ["$PAASAGE_CONFIG_DIR/wait-for-cdo.sh && " "java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-jar", "/cp-solver.jar"]