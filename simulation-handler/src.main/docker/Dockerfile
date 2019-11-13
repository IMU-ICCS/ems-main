#
# Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
# If a copy of the MPL was not distributed with this file, You can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

FROM java:8-alpine
VOLUME /tmp
ADD simulation-handler.jar simulation-handler.jar
RUN /bin/sh -c "apk add --no-cache bash"
RUN bash -c 'touch /simulation-handler.jar'

COPY ./run.sh .
RUN bash -c 'chmod +rx run.sh'
ENTRYPOINT ["./run.sh"]
