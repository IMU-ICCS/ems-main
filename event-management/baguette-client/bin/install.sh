#!/bin/bash

# Create installation directories
BIN_DIRECTORY=/opt/baguette-client/bin
LOGS_DIRECTORY=/opt/baguette-client/logs
APIKEY=$1

mkdir -p $BIN_DIRECTORY/
mkdir -p $LOGS_DIRECTORY/

# Create and change permissions of 'install-log.sh' script
touch $BIN_DIRECTORY/install-log.sh
chmod u=rwx,og-rwx $BIN_DIRECTORY/install-log.sh

# Write contents of 'install-log.sh' script
cat |sed -E 's/~/`/g' > $BIN_DIRECTORY/install-log.sh <<- EOM
#!/bin/bash

echo ""
echo "** Baguette Client - MELODIC PROJECT FP7 EU ...... **"
echo "** Copyright ICCS-NTUA (C) 2016-2019, http://imu.iccs.gr **"
date -Iseconds

# Common variables
APIKEY=?ems-api-key=\$1
DOWNLOAD_URL=http://192.168.48.1/resources/baguette-client.zip
PACKAGE_MD5=ecd502efe0292697721e99194a84dcef
INSTALL_PACKAGE=/opt/baguette-client/baguette-client.zip
INSTALL_DIR=/opt/
STARTUP_SCRIPT=/opt/baguette-client/bin/baguette-client
SERVICE_NAME=baguette-client
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

# Check MD5 checksum
ZIP_CHECKSUM=~md5sum \$INSTALL_PACKAGE |cut -d " " -f 1~
echo ""
echo "Checksum: \$ZIP_CHECKSUM"
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

# Add Id and Credentials configuration files
echo "Add Id and Credentials configuration files"
date -Iseconds
touch \$CLIENT_ID_FILE \$CREDENTIALS_FILE
if [ \$? != 0 ]; then
  echo "Failed to 'touch' configuration files (\$?)"
  echo "Aborting installation..."
  date -Iseconds
  exit 1
fi

chmod u=rw,og-rwx \$CLIENT_ID_FILE \$CREDENTIALS_FILE
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
$BIN_DIRECTORY/install-log.sh $APIKEY &> $LOGS_DIRECTORY/install.log

