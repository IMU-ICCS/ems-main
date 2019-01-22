#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

FROM java:8

WORKDIR /opt/ems-server

ADD control-service.jar .
ADD esper-*.jar .
ADD run-ems.sh .

RUN bash -c 'touch control-service.jar'
RUN bash -c 'touch esper-*.jar'
RUN bash -c 'chmod +rx run-ems.sh'
RUN bash -c 'apt-get update'
RUN bash -c 'apt-get install -y netcat'
RUN bash -c 'apt-get install -y net-tools'

#RUN bash -c 'apt-get update'
#RUN bash -c 'apt-get install -y netcat'

ENTRYPOINT ["./run-ems.sh"]

#ENV BASEDIR=/opt/ems-server
#ENV MELODIC_CONFIG_DIR=$BASEDIR/config
#ENV PAASAGE_CONFIG_DIR=$BASEDIR/config

#ENTRYPOINT ["/config/wait-for-cdo.sh", "java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/control-service.jar", " --logging.config=/config/logback-spring.xml"]
#ENTRYPOINT ["java", "-Duser.timezone=Europe/Warsaw", "-Djava.security.egd=file:/dev/./urandom", "-Dloader.path=$BASEDIR/esper-7.1.0.jar", "-cp", "$BASEDIR/control-service.jar", "org.springframework.boot.loader.PropertiesLauncher", " --logging.config=$MELODIC_CONFIG_DIR/logback-spring.xml"]
