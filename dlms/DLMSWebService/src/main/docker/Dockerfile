FROM java:8-alpine
VOLUME /tmp
ADD DLMSWebService.jar DLMSWebService.jar
RUN /bin/sh -c "apk add --no-cache bash"

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
