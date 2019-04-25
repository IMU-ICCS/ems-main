FROM java:8-alpine
VOLUME /tmp
ADD gui-backend.jar gui-backend.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /gui-backend.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
