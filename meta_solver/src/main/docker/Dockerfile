FROM java:8-alpine
VOLUME /tmp
ADD meta-solver.jar meta-solver.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /meta-solver.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
