FROM java:8
VOLUME /tmp
ADD control-service.jar control-service.jar
ADD esper-7.1.0.jar esper-7.1.0.jar
RUN bash -c 'touch /control-service.jar'
RUN bash -c 'touch /esper-7.1.0.jar'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
RUN bash -c 'ls -ltr'
ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-Dloader.path=/esper-7.1.0.jar", "-cp", "/control-service.jar", "$BROKER_CEP_TEST", "org.springframework.boot.loader.PropertiesLauncher", " --logging.config=/config/logback-spring.xml"]
