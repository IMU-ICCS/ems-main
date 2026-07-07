#
# Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless 
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at 
# https://www.mozilla.org/en-US/MPL/2.0/
#

ARG BUILDER_IMAGE=docker.io/library/maven:3.9.15-eclipse-temurin-21-noble
ARG SERVER_BASE_IMAGE=eclipse-temurin:21.0.10_7-jre-noble
ARG CLIENT_BASE_IMAGE=eclipse-temurin:21.0.10_7-jre-noble

# ----------------- EMS Builder image -----------------
FROM $BUILDER_IMAGE AS ems-server-builder

# Build directories
ARG BUILD_DIR=/build
ARG SOURCE_DIR=/build/ems-core
ARG TARGET_DIR=/build/dist

# Accept optional metadata (CI can pass these, local builds ignore them)
ARG GIT_COMMIT=unknown
ARG GIT_BRANCH=unknown
ARG GIT_URL=unknown
ARG DOCKER_IMAGE=unknown
ARG BUILD_DESCR=''

ENV BUILD_DIR=${BUILD_DIR} \
    TARGET_DIR=${TARGET_DIR} \
    GIT_COMMIT=$GIT_COMMIT \
    GIT_BRANCH=$GIT_BRANCH \
    GIT_URL=$GIT_URL \
    DOCKER_IMAGE=$DOCKER_IMAGE \
    BUILD_DESCR="$BUILD_DESCR"

WORKDIR ${BUILD_DIR}

# Download dependencies
COPY ./ems-core/pom.xml                             ${SOURCE_DIR}/pom.xml
COPY ./ems-core/api/pom.xml                         ${SOURCE_DIR}/api/pom.xml
COPY ./ems-core/baguette-client/pom.xml             ${SOURCE_DIR}/baguette-client/pom.xml
COPY ./ems-core/baguette-client-install/pom.xml     ${SOURCE_DIR}/baguette-client-install/pom.xml
COPY ./ems-core/baguette-server/pom.xml             ${SOURCE_DIR}/baguette-server/pom.xml
COPY ./ems-core/broker-cep/pom.xml                  ${SOURCE_DIR}/broker-cep/pom.xml
COPY ./ems-core/broker-client/pom.xml               ${SOURCE_DIR}/broker-client/pom.xml
COPY ./ems-core/common/pom.xml                      ${SOURCE_DIR}/common/pom.xml
COPY ./ems-core/control-service/pom.xml             ${SOURCE_DIR}/control-service/pom.xml
COPY ./ems-core/translator/pom.xml                  ${SOURCE_DIR}/translator/pom.xml
COPY ./ems-core/util/pom.xml                        ${SOURCE_DIR}/util/pom.xml
COPY ./ems-core/web-admin/pom.xml                   ${SOURCE_DIR}/web-admin/pom.xml

#RUN --mount=type=cache,target=/root/.m2,id=maven-cache \
RUN \
    mvn -q -B -ntp -f ${BUILD_DIR}/ems-core/pom.xml dependency:go-offline

# Copy source and .git
COPY ./.git     ${BUILD_DIR}/.git
COPY ./ems-core ${SOURCE_DIR}

# Build and arrange code
#RUN --mount=type=cache,target=/root/.m2,id=maven-cache  \
RUN \
    set -eux; \
    mvn -B -ntp -f ${BUILD_DIR}/ems-core/pom.xml -DskipTests \
        -Ddocker.image=${DOCKER_IMAGE} \
        -Dbuild.description="${BUILD_DESCR}" \
        clean install -P '!build-docker-image' -P '!build-web-admin'; \
    java -Djarmode=tools -jar ${SOURCE_DIR}/control-service/target/control-service.jar extract --layers --launcher; \
    mv ${BUILD_DIR}/control-service ${TARGET_DIR}; \
    cp ${SOURCE_DIR}/control-service/target/esper*.jar ${TARGET_DIR}/application/BOOT-INF/lib/; \
    rm -rf ${BUILD_DIR}/.git


# -----------------   EMS-Core Run image   -----------------
FROM $SERVER_BASE_IMAGE AS ems-server

ARG BUILD_DIR=/build
ARG SOURCE_DIR=/build/ems-core
ARG TARGET_DIR=/build/dist

ARG EMS_USER=emsuser
ARG EMS_HOME=/opt/ems-server

# Install dumb-init
RUN wget --progress=dot:giga -O /usr/local/bin/dumb-init \
          https://github.com/Yelp/dumb-init/releases/download/v1.2.5/dumb-init_1.2.5_x86_64 && \
    chmod +x /usr/local/bin/dumb-init
#    echo "e874b55f3279ca41415d290c512a7ba9d08f98041b28ae7c2acb19a545f1c4df  dumb-init" | sha256sum -c - && \

# Install optional packages
#RUN apt-get update \
#    && apt-get install -y netcat-openbsd vim iputils-ping \
#    && rm -rf /var/lib/apt/lists/*

# Add EMS user
RUN set -eux; \
    groupadd -r ${EMS_USER}; \
    useradd -r -g ${EMS_USER} -d ${EMS_HOME} -s /sbin/nologin ${EMS_USER}; \
    mkdir -p ${EMS_HOME} ${EMS_HOME}/models; \
    chown -R ${EMS_USER}:${EMS_USER} ${EMS_HOME}

# Setup environment
ENV BASEDIR=${EMS_HOME} \
    EMS_CONFIG_DIR=${EMS_HOME}/config \
    BIN_DIR=${EMS_HOME}/bin \
    CONFIG_DIR=${EMS_HOME}/config \
    LOGS_DIR=${EMS_HOME}/logs \
    MODELS_DIR=${EMS_HOME}/models \
    PUBLIC_DIR=${EMS_HOME}/public_resources

# Download a JRE suitable for running EMS clients to offer it for download
#ENV JRE_LINUX_PACKAGE=zulu21.34.19-ca-jre21.0.3-linux_x64.tar.gz
#RUN mkdir -p ${PUBLIC_DIR}/resources && \
#    wget --progress=dot:giga -O ${PUBLIC_DIR}/resources/${JRE_LINUX_PACKAGE} https://cdn.azul.com/zulu/bin/${JRE_LINUX_PACKAGE}

# Copy resource files to image
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${SOURCE_DIR}/bin                    ${BIN_DIR}
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${SOURCE_DIR}/config-files           ${CONFIG_DIR}
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${SOURCE_DIR}/public_resources       ${PUBLIC_DIR}

# Copy files from builder container
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${TARGET_DIR}/dependencies           ${BASEDIR}
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${TARGET_DIR}/spring-boot-loader     ${BASEDIR}
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${TARGET_DIR}/snapshot-dependencies  ${BASEDIR}
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder ${TARGET_DIR}/application            ${BASEDIR}

# Create 'logs', and 'models' directories. Make bin/*.sh scripts executable
RUN set -eux; \
    mkdir -p ${LOGS_DIR} ${MODELS_DIR} ; \
    chmod +rx ${BIN_DIR}/*.sh ; \
    chown -R ${EMS_USER}:${EMS_USER} ${BASEDIR}

# Set user and work dir.
USER ${EMS_USER}
WORKDIR ${EMS_HOME}

EXPOSE 2222 8111 61610 61616 61617

ENTRYPOINT ["dumb-init", "./bin/run.sh"]


# -----------------   EMS-Client Runtime image   -----------------
FROM $CLIENT_BASE_IMAGE AS ems-client

ARG BUILD_DIR=/build
ARG SOURCE_DIR=/build/ems-core
ARG TARGET_DIR=/build/dist

ARG EMS_USER=emsuser
ARG EMS_HOME=/opt/baguette-client
ARG INSTALLATION_PACKAGE=baguette-client-installation-package.tgz

# Install optional packages
#RUN apt-get update \
#    && apt-get install -y vim iputils-ping \
#    && rm -rf /var/lib/apt/lists/*

# Add EMS user
RUN set -eux; \
    groupadd -r ${EMS_USER}; \
    useradd -r -g ${EMS_USER} -d ${EMS_HOME} -s /sbin/nologin ${EMS_USER}; \
    mkdir -p ${EMS_HOME}; \
    chown -R ${EMS_USER}:${EMS_USER} ${EMS_HOME}

# Setup environment
ENV EMS_CONFIG_DIR=${EMS_HOME}/conf \
    JAVA_HOME=/opt/java/openjdk \
    PATH=$JAVA_HOME/bin:$PATH

# Copy Baguette Client files
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder  ${SOURCE_DIR}/baguette-client/target/$INSTALLATION_PACKAGE  /tmp
COPY --chown=${EMS_USER}:${EMS_USER} --from=ems-server-builder  ${SOURCE_DIR}/baguette-client/conf/  ${EMS_HOME}/conf/
RUN set -eux; \
    tar zxvf /tmp/${INSTALLATION_PACKAGE} -C /opt; \
    rm -f /tmp/${INSTALLATION_PACKAGE}; \
    chown -R ${EMS_USER}:${EMS_USER} ${EMS_HOME}

# Set user and work dir.
USER ${EMS_USER}
WORKDIR ${EMS_HOME}

EXPOSE 61610 61616 61617

ENTRYPOINT ["/bin/sh", "-c", "/opt/baguette-client/bin/run.sh  &&  tail -f /opt/baguette-client/logs/output.txt"]