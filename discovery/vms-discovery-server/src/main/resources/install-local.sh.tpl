#!/bin/bash

#created by Stefanidis 05.02.2018

# we are at location : /home/ubuntu
# for all Virtual Machines the IP of Upperware should be inititialized at: http://{PUBLIC_MELODIC_IP}:8095 (admin:admin)

mkdir -p ~/melodic/downloads
cd ~/melodic/downloads

#DOWNLOADURL=http://192.168.2.2:8080/melodic
DOWNLOADURL=VMS_CLIENT_DOWNLOAD_URL

echo 'downloading Active MQ broker' 
wget -c  $DOWNLOADURL/apache-activemq-5.15.0-bin.tar.gz

echo 'downloading mule engine' 
wget -c $DOWNLOADURL/mule-standalone-3.9.0.zip

echo 'downloading mule application for 1st level VM '
wget -c $DOWNLOADURL/RAM_CPU_case_v44_t_1st.zip -O RAM_CPU_case_v44_t_1st.zip

echo 'downloading mule application for 2nd level VM '
wget -c $DOWNLOADURL/RAM_CPU_case_v44_t_2nd.zip -O RAM_CPU_case_v44_t_2nd.zip

echo 'downloading VMS Discovery Client'
wget -c $DOWNLOADURL/vms-discovery-client.zip


sudo mkdir /opt

echo 'unzip the file of active mq broker to the /opt folder' 
sudo tar xvzf apache-activemq-5.15.0-bin.tar.gz -C /opt 

echo 'making a symbolic link for AMQ broker'
sudo ln -s /opt/apache-activemq-5.15.0 /opt/activemq


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
