FROM openjdk:8-alpine
ADD cp-solver.jar cp-solver.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN apk update && apk add ca-certificates wget && update-ca-certificates
RUN wget https://github.com/ibex-team/ibex-lib/archive/ibex-2.8.0.tar.gz -O ibex280.tar.gz
RUN tar xvzf ibex280.tar.gz
RUN apk add --no-cache python && \
   python -m ensurepip && \
   rm -r /usr/lib/python*/ensurepip && \
   pip install --upgrade pip setuptools && \
   rm -r /root/.cache
RUN python --version
RUN apk add build-base
RUN apk add flex bison

RUN cd ibex-lib-ibex-2.8.0 && ./waf configure --enable-shared --with-solver --with-jni --java-package-name=org.chocosolver.solver.constraints.real
RUN cd ibex-lib-ibex-2.8.0 && ./waf install

ENV LD_LIBRARY_PATH /usr/local/lib/

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
