FROM java:8
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN bash -c 'touch /cp-solver.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-jar", "/cp-solver.jar"]