#!/usr/bin/env bash
#
# Copyright (C) 2017-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

# ----- Setup -----
DELAY=5
PROCNAMES=( "${@:2}" )
if [ ${#procnames[@]} -eq 0 ]; then PROCNAMES=('netdata' 'kube' 'etcd'); fi
WHATTOKILL=('Baguette' 'netdata')

# ----- Logging -----
declare -A LOG_LEVEL
LOG_LEVEL[INFO]="1 94"
LOG_LEVEL[ERROR]="2 31"
log() {
    IFS=', ' read -r -a LEVEL_CFG <<< " ${LOG_LEVEL[$1]}"
    FD=${LEVEL_CFG[0]}
    COL=${LEVEL_CFG[1]}
    while IFS= read -r line; do
        echo -e "\e[93m$(date '+%Y-%m-%d %H:%M:%S %Z')\e[0m \e[${COL}m[$(printf '%-5s' $1)]\e[0m $line" >/dev/fd/$FD
    done
}
exec 1> >( log INFO )  2> >( log ERROR )
# Alternative: ./k8smon.sh | xargs -L 1 echo `date +'[%Y-%m-%d %H:%M:%S]'` $1

# ----- Start monitoring -----
echo "Starting K8S process monitor..."
#echo "Parent PID: $1"

# ----- Main loop -----
while true; do
  sleep $DELAY
  #echo Running check...
  found=0
  # Loop over process names
  for procname in "${PROCNAMES[@]}"
  do
    #echo Checking for $procname
    if [ `ps -ef |grep $procname |grep -v grep |grep -v $0 |wc -l` -gt 0 ]; then
      #echo Found $procname
      found=1
    fi
  done
  #echo FOUND_ANY=$found
  if [ $found -eq 1 ]; then
    # Kill processes mentioned in 'WHATTOKILL' array
    for proctokill in "${WHATTOKILL[@]}"
    do
      # Check if 'proctokill' is running
      if [ `ps -ef |grep -i $proctokill |grep -v grep |grep -v $0 |wc -l` -gt 0 ]; then
        echo "Killing $proctokill" >&2
        #pkill $proctokill
        #sleep 5
        #pkill -9 $proctokill
      fi
    done
  fi
done
