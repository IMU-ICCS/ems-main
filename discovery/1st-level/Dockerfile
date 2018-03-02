FROM java:8

# RUN apt-get update  &&  apt-get --yes --force-yes install vim

WORKDIR /tmp
ADD https://archive.apache.org/dist/activemq/5.15.0/apache-activemq-5.15.0-bin.tar.gz /tmp
ADD https://repository-master.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/3.9.0/mule-standalone-3.9.0.zip /tmp
ADD https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/1stLevelEsper_app.zip?at=refs%2Fheads%2Fesper-demo /tmp/1stLevelEsper_app.zip
ADD https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/vms-discovery-client.zip?at=refs%2Fheads%2Fesper-demo /tmp/vms-discovery-client.zip

# COPY apache-activemq-5.15.0-bin.tar.gz /tmp
# COPY mule-standalone-3.9.0.zip /tmp
# COPY 1stLevelEsper_app.zip /tmp
# COPY vms-discovery-client.zip /tmp

RUN mkdir -p /opt
RUN tar xvzf apache-activemq-5.15.0-bin.tar.gz -C /opt 
RUN ln -s /opt/apache-activemq-5.15.0 /opt/activemq
RUN unzip mule-standalone-3.9.0.zip -d /opt
RUN cp 1stLevelEsper_app.zip /opt/mule-standalone-3.9.0/apps
RUN unzip vms-discovery-client.zip -d /opt

RUN rm -f apache-activemq-5.15.0-bin.tar.gz mule-standalone-3.9.0.zip 1stLevelEsper_app.zip vms-discovery-client.zip

WORKDIR /opt/vms-discovery-client
RUN chmod +x run.sh bin/*.sh

EXPOSE  1099  8161  61616

RUN echo "1st Level Installed on VM"

# ENTRYPOINT ["/bin/bash"]
ENTRYPOINT ["./run.sh"]