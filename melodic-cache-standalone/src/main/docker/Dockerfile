FROM openjdk:8-alpine
VOLUME /tmp
ADD melodic-cache-standalone.jar melodic-cache-standalone.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /melodic-cache-standalone.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
