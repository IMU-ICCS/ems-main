#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

FROM java:8-alpine

WORKDIR /opt/ems-server
RUN /bin/sh -c "apk add --no-cache bash"

ADD bin ./bin
ADD jars ./jars
ADD config ./config
ADD resources ./resources

RUN mkdir logs
RUN bash -c 'touch jars/*.jar'
RUN bash -c 'chmod +rx ./bin/*.sh'

ENV BASEDIR /opt/ems-server
ENV MELODIC_CONFIG_DIR ${BASEDIR}/config
ENV PAASAGE_CONFIG_DIR ${BASEDIR}/config
ENV JAR_PATH ${BASEDIR}/jars

ENTRYPOINT ["./bin/run.sh"]