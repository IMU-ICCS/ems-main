FROM java:8-alpine
ADD nc-solver.jar nc-solver.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN apk update && apk add ca-certificates wget && update-ca-certificates

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
