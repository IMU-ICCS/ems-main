#!/usr/bin/env bash
#
# Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

curl -v -k -X POST https://localhost:8111/appModel \
        --header "EMS-API-KEY: ${2:-WEB_SECURITY_API_KEY_AUTHENTICATION_VALUE}" \
        --header "Content-Type: application/json" \
        --data "{ \"applicationId\": \"$1\" }"
