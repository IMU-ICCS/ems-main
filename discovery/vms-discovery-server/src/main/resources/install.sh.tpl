#!/bin/bash

#created by Stefanidis 05.02.2018

# we are at location : /home/ubuntu
# for all Virtual Machines the IP of Upperware should be inititialized at: http://{PUBLIC_MELODIC_IP}:8095 (admin:admin)

mkdir -p ~/melodic/downloads
cd ~/melodic/downloads
sleep 2

echo 'downloading Active MQ broker' 
wget -c  https://archive.apache.org/dist/activemq/5.15.0/apache-activemq-5.15.0-bin.tar.gz
sleep 2

echo 'downloading mule engine' 
wget -c https://repository-master.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/3.9.0/mule-standalone-3.9.0.zip
sleep 2

echo 'downloading mule application for 1st level VM '
#wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/RAM_CPU_case_v44_t_1st.zip?at=refs%2Fheads%2Fesper-demo -O RAM_CPU_case_v44_t_1st.zip
wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/browse/esper-demo/1stLevelEsper_app.zip?at=esper-demo -O 1stLevelEpser_app.zip
sleep 2

echo 'downloading mule application for 2nd level VM '
#wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/raw/esper-demo/RAM_CPU_case_v44_t_2nd.zip?at=refs%2Fheads%2Fesper-demo -O RAM_CPU_case_v44_t_2nd.zip
wget -c https://bitbucket.7bulls.eu/projects/TST/repos/melodic/browse/esper-demo/2ndLevelEsper_app.zip?at=esper-demo -O 2ndLevelEsper_app.zip
sleep 2

echo 'downloading VMS Discovery Client'
wget -c VMS_CLIENT_DOWNLOAD_URL/vms-discovery-client.zip
sleep 2


sudo mkdir /opt

echo 'unzip the file of active mq broker to the /opt folder' 
sudo tar xvzf apache-activemq-5.15.0-bin.tar.gz -C /opt 

echo 'making a symbolic link for AMQ broker'
sudo ln -s /opt/apache-activemq-5.15.0 /opt/activemq
#sudo ln -s /opt/apache-activemq-5.15.0 activemq

echo 'unzip the file of mule engine to the /opt folder' 
sudo unzip mule-standalone-3.9.0.zip -d /opt


echo 'putting the mule application (.zip) to the /apps folder'
sudo cp RAM_CPU_case_v44_t_1st.zip /opt/mule-standalone-3.9.0/apps


echo 'unzip VMD Discovery Client'
unzip vms-discovery-client.zip -d ../

cd ../sshc
chmod +x run.sh bin/client.sh


echo "Installed"


echo "
# VMS Discovery Server credentials
host = VMS_SERVER_HOST
port = VMS_SERVER_PORT
pubkey = VMS_SERVER_PUBKEY
fingerprint = VMS_SERVER_FINGERPRINT

username = VMS_SERVER_USERNAME
password = VMS_SERVER_PASSWORD
" > ~/melodic/vms-server.credentials


echo "Starting VMS Discovery Client..."
cd ../sshc
./run.sh

exit
