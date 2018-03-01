FROM java:8

# RUN apt-get update  &&  apt-get --yes --force-yes install vim

WORKDIR /tmp
ADD https://archive.apache.org/dist/activemq/5.15.0/apache-activemq-5.15.0-bin.tar.gz /tmp
ADD https://repository-master.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/3.9.0/mule-standalone-3.9.0.zip /tmp
# ADD https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/2ndLevelEsper_app.zip?at=refs%2Fheads%2Fesper-demo /tmp/2ndLevelEsper_app.zip
ADD https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/2ndLevelEsper2outs_app.zip?at=refs%2Fheads%2Fesper-demo /tmp/2ndLevelEsper2outs_app.zip
ADD https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/vms-discovery-server.zip?at=refs%2Fheads%2Fesper-demo /tmp/vms-discovery-server.zip

# COPY apache-activemq-5.15.0-bin.tar.gz /tmp
# COPY mule-standalone-3.9.0.zip /tmp
# # COPY 2ndLevelEsper_app.zip /tmp
# COPY 2ndLevelEsper2outs_app.zip /tmp
# COPY vms-discovery-server.zip /tmp

RUN mkdir -p /opt
RUN tar xvzf apache-activemq-5.15.0-bin.tar.gz -C /opt 
RUN ln -s /opt/apache-activemq-5.15.0 /opt/activemq
RUN unzip mule-standalone-3.9.0.zip -d /opt
# RUN cp 2ndLevelEsper_app.zip /opt/mule-standalone-3.9.0/apps
RUN cp 2ndLevelEsper2outs_app.zip /opt/mule-standalone-3.9.0/apps
RUN unzip vms-discovery-server.zip -d /opt

# RUN rm -f apache-activemq-5.15.0-bin.tar.gz mule-standalone-3.9.0.zip 2ndLevelEsper_app.zip vms-discovery-server.zip
RUN rm -f apache-activemq-5.15.0-bin.tar.gz mule-standalone-3.9.0.zip 2ndLevelEsper2outs_app.zip vms-discovery-server.zip


COPY ./scripts/run.sh /opt/vms-discovery-server/run.sh
COPY ./scripts/start-level-3.sh /opt/vms-discovery-server/start-level-3.sh
COPY ./scripts/stop-level-3.sh /opt/vms-discovery-server/stop-level-3.sh

WORKDIR /opt/vms-discovery-server
RUN chmod +x *.sh

# VOLUME /opt/vms-discovery-server/conf

EXPOSE 2222  8161  61616

RUN echo "3rd Level Installed"

# ENTRYPOINT ["/bin/bash"]
ENTRYPOINT ["./start-level-3.sh"]