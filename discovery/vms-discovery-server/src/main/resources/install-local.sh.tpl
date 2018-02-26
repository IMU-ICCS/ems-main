#!/bin/bash

mkdir -p /tmp/metrics-downloads
cd /tmp/metrics-downloads
sleep 2

echo 'Downloading Active MQ broker' 
wget -c  https://archive.apache.org/dist/activemq/5.15.0/apache-activemq-5.15.0-bin.tar.gz
sleep 2

echo 'Downloading Mule engine' 
wget -c https://repository-master.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/3.9.0/mule-standalone-3.9.0.zip
sleep 2

echo 'Downloading Mule application for 1st level VM '
wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/1stLevelEsper_app.zip?at=refs%2Fheads%2Fesper-demo -O 1stLevelEsper_app.zip
sleep 2

#echo 'Downloading mule application for 2nd level VM '
#wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/2ndLevelEsper_app.zip?at=refs%2Fheads%2Fesper-demo -O 2ndLevelEsper_app.zip
#sleep 2

echo 'Downloading VMS Discovery Client'
wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/vms-discovery-client.zip?at=refs%2Fheads%2Fesper-demo -O vms-discovery-client.zip
sleep 2


sudo mkdir /opt

echo 'Unzip Active MQ broker into /opt' 
sudo tar xvzf apache-activemq-5.15.0-bin.tar.gz -C /opt 

echo 'Creating a symbolic link for Active MQ broker'
sudo ln -s /opt/apache-activemq-5.15.0 /opt/activemq

echo 'Unzip Mule engine into /opt' 
sudo unzip mule-standalone-3.9.0.zip -d /opt

echo 'Copying the 1st level application (.zip) into MULE_HOME/apps folder'
sudo cp 1stLevelEsper_app.zip /opt/mule-standalone-3.9.0/apps


echo 'Unzip VMD Discovery Client'
sudo unzip vms-discovery-client.zip -d /opt

cd /opt/vms-discovery-client
sudo chmod +x run.sh bin/client.sh


echo "1st Level Installed on VM"


sudo echo "
# VMS Discovery Server credentials
host = VMS_SERVER_HOST
port = VMS_SERVER_PORT
pubkey = VMS_SERVER_PUBKEY
fingerprint = VMS_SERVER_FINGERPRINT

username = VMS_SERVER_USERNAME
password = VMS_SERVER_PASSWORD
" > ./vms-server.credentials
sudo cp vms-server.credentials /opt
chmod 700 /opt/vms-server.credentials
rm vms-server.credentials


exit
