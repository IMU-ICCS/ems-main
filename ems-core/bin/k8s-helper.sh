#!/usr/bin/env bash
#
# Copyright (C) 2017-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

K8S_OUTPUT_DIR=$EMS_CONFIG_DIR/k8s
mkdir -p $K8S_OUTPUT_DIR/

# Check if  baguette server connection info file exists
[ ! -f $EMS_CONFIG_DIR/baguette-server-connection-info.json ] && exit 1

# Read baguette server connection info file into a variable
BSCI=$( tr -d "\r\n" < $EMS_CONFIG_DIR/baguette-server-connection-info.json )
#echo $BSCI

# Write baguette server connection info into ems-client-configmap
echo "/* Date: $(date) */" > $K8S_OUTPUT_DIR/cfgmap_output.json
sec=/var/run/secrets/kubernetes.io/serviceaccount
curl -sS \
     -H "Authorization: Bearer $(cat $sec/token)" \
     -H "Content-Type: application/json-patch+json" \
     --cacert $sec/ca.crt \
     --request PATCH \
     --data "[{\"op\": \"replace\", \"path\": \"/data\", \"value\": $BSCI}]" \
     https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/$(cat $sec/namespace)/configmaps/ems-client-configmap \
  &>> $K8S_OUTPUT_DIR/cfgmap_output.json
