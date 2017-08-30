FROM java:8
VOLUME /tmp
ADD solver-to-deployment.jar solver-to-deployment.jar
RUN bash -c 'touch /solver-to-deployment.jar'
ENTRYPOINT ["java", "-jar", "/solver-to-deployment.jar", "-Djava.security.egd=file:/dev/./urandom", "--spring.config.location=classpath:/config/eu.melodic.upperware.solverToDeployment.properties", "--spring.config.location=/config/eu.melodic.upperware.solverToDeployment.properties"]