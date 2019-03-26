FROM java:8-alpine
VOLUME /tmp
ADD DLMSController.jar DLMSController.jar
RUN /bin/sh -c "apk add --no-cache bash"
ENTRYPOINT ["java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/DLMSController.jar"]