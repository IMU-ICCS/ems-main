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
ENV JAR_PATH ${BASEDIR}/jars

WORKDIR ${BASEDIR}
RUN apt-get update && apt-get install -y \
    dumb-init \
    netcat \
    vim \
    iputils-ping \
    && rm -rf /var/lib/apt/lists/*

ADD bin ./bin
ADD jars ./jars
ADD config ./config
#ADD resources ./resources

RUN ln -s ${BASEDIR}/jars/broker-client-with-dependencies.jar ${MELODIC_CONFIG_DIR}/resources/broker-client.jar

RUN mkdir logs
RUN bash -c 'chmod +rx ./bin/*.sh'

# RUN adduser --system --home ${BASEDIR} --no-create-home --shell /bin/false --group emsuser
# RUN chown -R emsuser:emsuser ${BASEDIR}
# USER emsuser

ENTRYPOINT ["dumb-init", "./bin/run.sh"]