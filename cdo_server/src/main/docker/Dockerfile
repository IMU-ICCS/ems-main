# Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; version 2 of the License.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
FROM oraclelinux:7-slim

USER root

ARG PACKAGE_URL=https://repo.mysql.com/yum/mysql-5.5-community/docker/x86_64/mysql-community-server-minimal-5.5.58-2.el7.x86_64.rpm
ARG PACKAGE_URL_SHELL=""

# Install server
RUN rpmkeys --import https://repo.mysql.com/RPM-GPG-KEY-mysql \
  && yum install -y $PACKAGE_URL $PACKAGE_URL_SHELL libpwquality \
  && yum clean all \
  && mkdir /docker-entrypoint-initdb.d

ADD server.jar server.jar

RUN YUM_CMD="yum install -y java-1.8.0-openjdk" && \
    (${YUM_CMD} || ${YUM_CMD}) && \
    yum clean all
ENV JAVA_HOME=/usr/lib/jvm/jre-openjdk


VOLUME /var/lib/mysql

COPY ./scripts/docker-entrypoint.sh /entrypoint.sh
COPY ./scripts/healthcheck.sh /healthcheck.sh

RUN chmod +x ./healthcheck.sh
RUN chmod +x ./entrypoint.sh


ENTRYPOINT ["/entrypoint.sh"]
HEALTHCHECK CMD /healthcheck.sh
EXPOSE 3306
CMD ["mysqld"]