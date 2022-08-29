#
# Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
# If a copy of the MPL was not distributed with this file, You can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

FROM openjdk:8-alpine
VOLUME /tmp
RUN /bin/sh -c "apk add --no-cache bash"

ADD meta-solver.jar meta-solver.jar
RUN bash -c 'touch /meta-solver.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
