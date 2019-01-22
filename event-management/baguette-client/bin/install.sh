#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

# Create installation directories
BIN_DIRECTORY=/opt/baguette-client/bin
LOGS_DIRECTORY=/opt/baguette-client/logs
BASE_URL=$1
APIKEY=$2

mkdir -p $BIN_DIRECTORY/
mkdir -p $LOGS_DIRECTORY/

# Create and change permissions of 'install-log.sh' script
touch $BIN_DIRECTORY/install-log.sh
chmod u=rwx,og-rwx $BIN_DIRECTORY/install-log.sh

# Write contents of 'install-log.sh' script
cat |sed -E 's/~/`/g' > $BIN_DIRECTORY/install-log.sh <<- EOM
#!/usr/bin/env bash

echo ""
echo "** Baguette Client - MELODIC PROJECT FP7 EU ...... **"
echo "** Copyright ICCS-NTUA (C) 2016-2019, http://imu.iccs.gr **"
date -Iseconds

# Common variables
BASE_URL=\$1
APIKEY=?ems-api-key=\$2
DOWNLOAD_URL=\$BASE_URL/baguette-client.zip
DOWNLOAD_URL_MD5=\$BASE_URL/baguette-client.zip.md5
INSTALL_PACKAGE=/opt/baguette-client/baguette-client.zip
INSTALL_PACKAGE_MD5=/opt/baguette-client/baguette-client.zip.md5
INSTALL_DIR=/opt/
STARTUP_SCRIPT=/opt/baguette-client/bin/baguette-client
SERVICE_NAME=baguette-client
CLIENT_CONF_FILE=/opt/baguette-client/conf/baguette-client.properties
CLIENT_ID_FILE=/opt/baguette-client/conf/id.txt
CREDENTIALS_FILE=/opt/baguette-client/conf/baguette-server.credentials

# Create installation directory
echo ""
echo "Create installation directory..."
date -Iseconds
mkdir -p \$INSTALL_DIR/baguette-client
if [ \$? != 0 ]; then
  echo "Failed to create installation directory (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Download installation package
echo ""
echo "Download installation package..."
date -Iseconds
wget \$DOWNLOAD_URL -O \$INSTALL_PACKAGE
if [ \$? != 0 ]; then
  echo "Failed to download installation package (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi
date -Iseconds
echo "Download installation package...ok"

# Download installation package MD5 checksum
echo ""
echo "Download installation package MD5 checksum..."
date -Iseconds
wget \$DOWNLOAD_URL_MD5 -O \$INSTALL_PACKAGE_MD5
if [ \$? != 0 ]; then
  echo "Failed to download installation package (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi
date -Iseconds
echo "Download installation package MD5 checksum...ok"

# Check MD5 checksum
PACKAGE_MD5=~cat \$INSTALL_PACKAGE_MD5~
ZIP_CHECKSUM=~md5sum \$INSTALL_PACKAGE |cut -d " " -f 1~
echo ""
echo "Checksum MD5:  \$PACKAGE_MD5"
echo "Checksum calc: \$ZIP_CHECKSUM"
if [ \$ZIP_CHECKSUM == \$PACKAGE_MD5 ]; then
  echo "Checksum: ok"
else
  echo "Checksum: wrong"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Unzip installation package
echo ""
echo "Unzip installation package..."
date -Iseconds
unzip -o \$INSTALL_PACKAGE -d \$INSTALL_DIR
if [ \$? != 0 ]; then
  echo "Failed to unzip installation package (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi
date -Iseconds

# Make scripts executable
echo ""
echo "Make scripts executable..."
date -Iseconds
chmod u=rx,og-rwx \$INSTALL_DIR/baguette-client/bin/*
if [ \$? != 0 ]; then
  echo "Failed to copy service script to /etc/init.d/ directory (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Register as a service
echo ""
echo "Register as a service..."
date -Iseconds
cp -f \$STARTUP_SCRIPT /etc/init.d/
if [ \$? != 0 ]; then
  echo "Failed to copy service script to /etc/init.d/ directory (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

update-rc.d \$SERVICE_NAME defaults
if [ \$? != 0 ]; then
  echo "Failed to register service script to /etc/init.d/ directory (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Add Id, Credentials and Client configuration files
echo "Add Id, Credentials and Client configuration files"
date -Iseconds
touch \$CLIENT_ID_FILE \$CREDENTIALS_FILE \$CLIENT_CONF_FILE
if [ \$? != 0 ]; then
  echo "Failed to 'touch' configuration files (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

chmod u=rw,og-rwx \$CLIENT_ID_FILE \$CREDENTIALS_FILE \$CLIENT_CONF_FILE
touch \$CLIENT_ID_FILE \$CREDENTIALS_FILE
if [ \$? != 0 ]; then
  echo "Failed to change permissions of configuration files (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Success
echo ""
echo "Success - Baguette client successfully installed on system"
date -Iseconds
echo ""
exit 0
EOM

# Run 'install-log.sh' script
$BIN_DIRECTORY/install-log.sh $BASE_URL $APIKEY &> $LOGS_DIRECTORY/install.log
