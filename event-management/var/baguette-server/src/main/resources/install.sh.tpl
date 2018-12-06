#!/bin/bash

if [ $( dpkg -s unzip |grep "ok installed" |wc -l ) == 0 ]; then
	sudo apt-get update ;
	sudo apt-get --yes --force-yes install unzip ;
	sleep 2
fi

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

echo 'Downloading Baguette Client'
wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/baguette-client.zip?at=refs%2Fheads%2Fesper-demo -O baguette-client.zip
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
sudo unzip baguette-client.zip -d /opt

cd /opt/baguette-client
sudo chmod +x run.sh bin/client.sh


echo "1st Level Installed on VM"


echo "
# Baguette Server credentials
host = BAGUETTE_SERVER_HOST
port = BAGUETTE_SERVER_PORT
pubkey = BAGUETTE_SERVER_PUBKEY
fingerprint = BAGUETTE_SERVER_FINGERPRINT

username = BAGUETTE_SERVER_USERNAME
password = BAGUETTE_SERVER_PASSWORD
" | sudo tee -a BAGUETTE_SERVER_CREDENTIALS_FILE > /dev/null

sudo chmod 700 BAGUETTE_SERVER_CREDENTIALS_FILE


exit
