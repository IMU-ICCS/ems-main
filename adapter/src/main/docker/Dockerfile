FROM java:8-alpine
VOLUME /tmp
ADD adapter.jar adapter.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /adapter.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
