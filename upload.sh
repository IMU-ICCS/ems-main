#!/bin/bash
SSH_HOST="ceticadmin@192.168.65.100"
JAR=adaptationmanager-2015.9.1-SNAPSHOT-jar-with-dependencies.jar
SERVICE=adaptationmanager
SRC_DIR=./target
DEST_DIR=adaptationmanager/app

function stop_service()
{
  ssh $SSH_HOST  "sudo service $SERVICE stop"
}

function start_service()
{
  ssh $SSH_HOST  "sudo service $SERVICE start"
}

function copy_jar() {
  scp $SRC_DIR/$JAR $SSH_HOST:$DEST_DIR/$JAR
}

echo "Building"
mvn clean package  -Dmaven.test.skip=true

echo "Stopping $SERVICE"
stop_service
echo "Copying $JAR"
copy_jar
echo "Starting $SERVICE"
start_service

