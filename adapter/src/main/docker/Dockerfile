FROM java:8
VOLUME /tmp
ADD adapter.jar adapter.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "/adapter.jar", "-Djava.security.egd=file:/dev/./urandom"]