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
ADD util-*.jar .
ADD config ./config
ADD resources ./resources
ADD run-ems.sh .
ADD initialize-keystores.sh .

RUN bash -c 'touch *.jar'
RUN bash -c 'chmod +rx *.sh'
RUN mkdir logs

ENTRYPOINT ["./run-ems.sh"]
