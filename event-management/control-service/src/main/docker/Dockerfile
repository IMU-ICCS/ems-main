#
# Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless 
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at 
# https://www.mozilla.org/en-US/MPL/2.0/
#

FROM java:8-alpine

ENV BASEDIR /opt/ems-server
ENV MELODIC_CONFIG_DIR ${BASEDIR}/config
ENV PAASAGE_CONFIG_DIR ${BASEDIR}/config
ENV JAR_PATH ${BASEDIR}/jars

WORKDIR ${BASEDIR}
RUN /bin/sh -c "apk add --no-cache bash"

ADD bin ./bin
ADD jars ./jars
ADD config ./config
ADD resources ./resources

RUN mkdir logs
RUN bash -c 'touch jars/*.jar'
RUN bash -c 'chmod +rx ./bin/*.sh'

ENTRYPOINT ["./bin/run.sh"]