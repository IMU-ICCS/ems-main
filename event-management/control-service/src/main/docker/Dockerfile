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

RUN mkdir bin
RUN mkdir jars
RUN mkdir jars/util
RUN mkdir logs

ADD control-service.jar ./jars
ADD esper-*.jar ./jars
ADD util-*.jar ./jars/util
ADD config ./config
ADD resources ./resources
ADD run-ems.sh ./bin
ADD initialize-keystores.sh ./bin

RUN bash -c 'touch jars/*.jar'
RUN bash -c 'chmod +rx ./bin/*.sh'

ENV MELODIC_CONFIG_DIR /opt/ems-server/config
ENV PAASAGE_CONFIG_DIR /opt/ems-server/config
ENV JAR_PATH ./jars

ENTRYPOINT ["./bin/run-ems.sh"]