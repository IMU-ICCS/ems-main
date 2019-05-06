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

ADD control-service.jar .
ADD esper-*.jar .
ADD config ./config
ADD resources ./resources
ADD *.sh .

RUN bash -c 'touch control-service.jar'
RUN bash -c 'touch esper-*.jar'
RUN bash -c 'chmod +rx run-ems.sh'
RUN mkdir logs

ENTRYPOINT ["./bin/run-ems.sh"]
