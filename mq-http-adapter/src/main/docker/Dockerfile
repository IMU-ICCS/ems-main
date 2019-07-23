FROM java:8-alpine
VOLUME /tmp
ADD mq-http-adapter.jar mq-http-adapter.jar
ADD eu.melodic.event.brokerclient.properties eu.melodic.event.brokerclient.properties
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /mq-http-adapter.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]