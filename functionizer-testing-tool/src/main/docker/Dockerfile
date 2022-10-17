FROM openjdk:8-alpine
VOLUME /tmp
ADD functionizer-testing-tool.jar functionizer-testing-tool.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /functionizer-testing-tool.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
