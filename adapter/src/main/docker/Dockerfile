FROM java:8
VOLUME /tmp
ADD adapter.jar adapter.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Warsaw", "-jar", "/adapter.jar"]