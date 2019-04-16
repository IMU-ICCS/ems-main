FROM java:8-alpine
VOLUME /tmp
ADD DLMSController.jar DLMSController.jar
RUN /bin/sh -c "apk add --no-cache bash"

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
