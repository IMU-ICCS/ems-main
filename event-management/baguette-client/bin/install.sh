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
CONF_DIRECTORY=/opt/baguette-client/conf
LOGS_DIRECTORY=/opt/baguette-client/logs
BASE_URL=$1
APIKEY=$2

mkdir -p $BIN_DIRECTORY/
mkdir -p $CONF_DIRECTORY/
mkdir -p $LOGS_DIRECTORY/

echo ""
echo "** EMS Baguette Client - MELODIC PROJECT FP7 EU ...... **"
echo "** Copyright ICCS-NTUA (C) 2016-2019, http://imu.iccs.gr **"
echo ""
date -Iseconds

# Common variables
DOWNLOAD_URL=$BASE_URL/baguette-client.tgz
DOWNLOAD_URL_MD5=$BASE_URL/baguette-client.tgz.md5
INSTALL_PACKAGE=/opt/baguette-client/baguette-client.tgz
INSTALL_PACKAGE_MD5=/opt/baguette-client/baguette-client.tgz.md5
INSTALL_DIR=/opt/
STARTUP_SCRIPT=$BIN_DIRECTORY/baguette-client
SERVICE_NAME=baguette-client
CLIENT_CONF_FILE=$CONF_DIRECTORY/baguette-client.properties
CLIENT_ID_FILE=$CONF_DIRECTORY/id.txt
CREDENTIALS_FILE=$CONF_DIRECTORY/baguette-server.credentials

# Check if already installed
if [ -f /opt/baguette-client/conf/ok.txt ]; then
  echo "Already installed. Exiting..."
  date -Iseconds
  exit 0
fi

# Create installation directory
echo ""
echo "Create installation directory..."
date -Iseconds
mkdir -p $INSTALL_DIR/baguette-client
if [ $? != 0 ]; then
  echo "Failed to create installation directory ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Download installation package
echo ""
echo "Download installation package..."
date -Iseconds
wget --no-check-certificate $DOWNLOAD_URL -O $INSTALL_PACKAGE
if [ $? != 0 ]; then
  echo "Failed to download installation package ($?)"
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
wget --no-check-certificate $DOWNLOAD_URL_MD5 -O $INSTALL_PACKAGE_MD5
if [ $? != 0 ]; then
  echo "Failed to download installation package ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi
date -Iseconds
echo "Download installation package MD5 checksum...ok"

# Check MD5 checksum
PACKAGE_MD5=`cat $INSTALL_PACKAGE_MD5`
PACKAGE_CHECKSUM=`md5sum $INSTALL_PACKAGE |cut -d " " -f 1`
echo ""
echo "Checksum MD5:  $PACKAGE_MD5"
echo "Checksum calc: $PACKAGE_CHECKSUM"
if [ $PACKAGE_CHECKSUM == $PACKAGE_MD5 ]; then
  echo "Checksum: ok"
else
  echo "Checksum: wrong"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Extract installation package
echo ""
echo "Extracting installation package..."
date -Iseconds
#unzip -o $INSTALL_PACKAGE -d $INSTALL_DIR
tar -xvzf $INSTALL_PACKAGE -C $INSTALL_DIR
if [ $? != 0 ]; then
  echo "Failed to extract installation package contents ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi
date -Iseconds

# Make scripts executable
echo ""
echo "Make scripts executable..."
date -Iseconds
chmod u=rx,og-rwx $INSTALL_DIR/baguette-client/bin/*
if [ $? != 0 ]; then
  echo "Failed to copy service script to /etc/init.d/ directory ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Register as a service
echo ""
echo "Register as a service..."
date -Iseconds
cp -f $STARTUP_SCRIPT /etc/init.d/
if [ $? != 0 ]; then
  echo "Failed to copy service script to /etc/init.d/ directory ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

update-rc.d $SERVICE_NAME defaults
if [ $? != 0 ]; then
  echo "Failed to register service script to /etc/init.d/ directory ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

# Add Id, Credentials and Client configuration files
echo "Add Id, Credentials and Client configuration files"
date -Iseconds
touch $CLIENT_ID_FILE $CREDENTIALS_FILE $CLIENT_CONF_FILE
if [ $? != 0 ]; then
  echo "Failed to 'touch' configuration files ($?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

chmod u=rw,og-rwx $CLIENT_ID_FILE $CREDENTIALS_FILE $CLIENT_CONF_FILE
touch $CLIENT_ID_FILE $CREDENTIALS_FILE
if [ $? != 0 ]; then
  echo "Failed to change permissions of configuration files ($?)"
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
