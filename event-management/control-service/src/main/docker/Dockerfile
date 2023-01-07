#
# Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless 
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at 
# https://www.mozilla.org/en-US/MPL/2.0/
#

FROM adoptopenjdk:8-jre

ENV BASEDIR /opt/ems-server
ENV MELODIC_CONFIG_DIR ${BASEDIR}/config
ENV PAASAGE_CONFIG_DIR ${BASEDIR}/config

ENV BIN_DIR ${BASEDIR}/bin
ENV CONFIG_DIR ${BASEDIR}/config
ENV JARS_DIR ${BASEDIR}/jars
ENV LOGS_DIR ${BASEDIR}/logs
ENV PUBLIC_DIR ${BASEDIR}/public_resources

WORKDIR ${BASEDIR}
RUN apt-get update && apt-get install -y \
    dumb-init \
    netcat \
    vim \
    iputils-ping \
    && rm -rf /var/lib/apt/lists/*

# Download a JRE suitable for running EMS clients, and
# offer it for download during EMS client installations
ENV JRE_LINUX_PACKAGE zulu8.52.0.23-ca-jre8.0.282-linux_x64.tar.gz
RUN mkdir -p ${PUBLIC_DIR}/resources
RUN curl https://cdn.azul.com/zulu/bin/${JRE_LINUX_PACKAGE} --output ${PUBLIC_DIR}/resources/${JRE_LINUX_PACKAGE}

ADD bin ${BIN_DIR}
ADD jars ${JARS_DIR}
ADD config ${CONFIG_DIR}
ADD public_resources ${PUBLIC_DIR}

RUN mkdir ${LOGS_DIR}
RUN bash -c 'chmod +rx ${BIN_DIR}/*.sh'

# RUN ln -s ${JARS_DIR}/broker-client-jar-with-dependencies.jar ${PUBLIC_DIR}/broker-client.jar
# RUN ln -s ${CONFIG_DIR}/eu.melodic.event.brokerclient.properties ${PUBLIC_DIR}/eu.melodic.event.brokerclient.properties
# RUN ln -s ${BIN_DIR}/client.sh ${PUBLIC_DIR}/client.sh
# RUN ln -s ${LOGS_DIR} ${PUBLIC_DIR}/logs

# RUN adduser --system --home ${BASEDIR} --no-create-home --shell /bin/false --group emsuser
# RUN chown -R emsuser:emsuser ${BASEDIR}
# USER emsuser

EXPOSE 2222
EXPOSE 8111
EXPOSE 61610
EXPOSE 61616
EXPOSE 61617

ENTRYPOINT ["dumb-init", "./bin/run.sh"]