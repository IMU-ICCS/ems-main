FROM java:8
VOLUME /tmp
ADD vms-discovery-server.zip vms-discovery-server.zip
RUN bash -c 'touch /vms-discovery-server.zip'
RUN bash -c 'unzip /vms-discovery-server.zip'
ENTRYPOINT ["java", "-classpath", "vmd-discovery-server/lib/*", "eu.melodic.upperware.discovery.server.VmsManagementServer"]