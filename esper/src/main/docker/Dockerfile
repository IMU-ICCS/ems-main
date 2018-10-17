FROM java:openjdk-8-jdk

MAINTAINER mprusinski@7bulls.com

RUN cd ~ && wget https://repository-master.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/3.9.0/mule-standalone-3.9.0.tar.gz && echo "39b773bf20702f614faf30b2ffca4716 mule-standalone-3.9.0.tar.gz" | md5sum -c
RUN cd /opt && tar xvzf ~/mule-standalone-3.9.0.tar.gz && rm ~/mule-standalone-3.9.0.tar.gz && ln -s /opt/mule-standalone-3.9.0 /opt/mule

RUN cd ~ && wget https://archive.apache.org/dist/activemq/5.15.0/apache-activemq-5.15.0-bin.tar.gz
RUN cd /opt && tar xvzf ~/apache-activemq-5.15.0-bin.tar.gz && rm ~/apache-activemq-5.15.0-bin.tar.gz && ln -s /opt/apache-activemq-5.15.0 /opt/activemq

# Define environment variables.
ENV MULE_HOME /opt/mule

# Define mount points.
VOLUME ["/opt/mule/logs", "/opt/mule/conf", "/opt/mule/apps", "/opt/mule/domains"]

# Define working directory.
WORKDIR /opt/mule

# copy mule app
ADD esper.zip /opt/mule/apps

ADD ./scripts/entrypoint.sh /opt/mule
RUN chmod +x /opt/mule/entrypoint.sh
ENTRYPOINT ["/opt/mule/entrypoint.sh"]
#ENTRYPOINT ["/opt/mule/bin/mule" , "-M-Duser.timezone=Europe/Warsaw"]
#CMD [ "cd /opt/activemq/bin/linux-x86-64 && sh activemq start && ", "/opt/mule/bin/mule", "-M-Duser.timezone=Europe/Warsaw" ]

