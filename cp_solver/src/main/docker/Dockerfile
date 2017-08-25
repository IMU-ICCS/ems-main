FROM java:8
VOLUME /tmp
ADD cp-solver.jar cp-solver.jar
RUN bash -c 'touch /cp-solver.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cp-solver.jar", "--PAASAGE_CONFIG_DIR=/config", "--spring.config.location=classpath:/config/eu.melodic.upperware.cpSolver.properties"]