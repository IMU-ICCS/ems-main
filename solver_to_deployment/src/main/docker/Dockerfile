FROM java:8
VOLUME /tmp
ADD solver-to-deployment.jar solver-to-deployment.jar
RUN bash -c 'touch /solver-to-deployment.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/solver-to-deployment.jar", "--PAASAGE_CONFIG_DIR=/config", "--spring.config.location=classpath:/config/eu.melodic.upperware.solverToDeployment.properties"]