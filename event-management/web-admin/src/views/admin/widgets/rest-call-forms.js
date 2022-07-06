/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

export const FORM_TYPE_OPTIONS = [
                { 'id': 'new-camel', 'text': 'Send CAMEL model request', 'url': '/camelModel', 'method': 'POST', 'form': 'camel-form' },
                { 'id': 'new-cp', 'text': 'Send CP model request', 'url': '/cpModelJson', 'method': 'POST', 'form': 'cp-form' },
                { 'id': 'const', 'text': 'Set constants (add name-value pairs in Payload)', 'url': '/cpConstants', 'method': 'POST', 'form': '' },
                { 'id': 'new-vm', 'text': 'Register Node', 'url': '/baguette/registerNode', 'method': 'POST', 'form': 'vm-form' },
                { 'id': 'get-mons', 'text': 'Get Monitors/Sensors', 'url': '/monitors', 'method': 'POST', 'form': 'camel-form' },
                { 'id': 'get-camel', 'text': 'Current CAMEL model', 'url': '/translator/currentCamelModel', 'method': 'GET', 'form': '' },
                { 'id': 'get-cp', 'text': 'Current CP model', 'url': '/translator/currentCpModel', 'method': 'GET', 'form': '' },

                { 'id': 'get-cons', 'text': 'Constraint Thresholds', 'url': '/translator/constraintThresholds/{appId}', 'method': 'GET', 'form': 'app-id-form' },
                { 'id': 'get-tl-mc', 'text': 'Top-Level Metric Contexts', 'url': '/translator/getTopLevelNodesMetricContexts/{appId}', 'method': 'GET', 'form': 'app-id-form' },

                { 'id': 'get-cred', 'text': 'EMS server Broker credentials', 'url': '/broker/credentials', 'method': 'GET', 'form': '' },
                { 'id': 'get-ref', 'text': 'VM credentials by Ref', 'url': '/baguette/ref/{ref}', 'method': 'GET', 'form': 'ref-form' },

                { 'id': 'client-list', 'text': 'Client list', 'url': '/client/list', 'method': 'GET', 'form': '' },
                { 'id': 'client-map', 'text': 'Client map', 'url': '/client/list/map', 'method': 'GET', 'form': '' },
                { 'id': 'node-info', 'text': 'Node Info by IP address', 'url': '/baguette/getNodeInfoByAddress/{ip-address}', 'method': 'GET', 'form': 'ip-form' },
                { 'id': 'node-name', 'text': 'Node Name by IP address', 'url': '/baguette/getNodeNameByAddress/{ip-address}', 'method': 'GET', 'form': 'ip-form' },

                { 'id': 'new-otp', 'text': 'New OTP', 'url': '/ems/otp/new', 'method': 'GET', 'form': '' },
                { 'id': 'del-otp', 'text': 'Delete OTP', 'url': '/ems/otp/remove/{otp}', 'method': 'GET', 'form': 'otp-form' },

                { 'id': 'd-stop-baguette', 'text': 'DEBUG - Stop Baguette Server', 'url': '/baguette/stopServer', 'method': 'GET', 'form': '' },
                { 'id': 'd-shutdown', 'text': 'DEBUG - EMS server shutdown', 'url': '/ems/shutdown', 'method': 'GET', 'form': '' },
                { 'id': 'd-exit', 'text': 'DEBUG - EMS server shutdown and Exit', 'url': '/ems/exit', 'method': 'GET', 'form': '' },
                { 'id': 'd-restart', 'text': 'DEBUG - EMS server shutdown and Restart', 'url': '/ems/exit/99', 'method': 'GET', 'form': '' },

                { 'id': 'health', 'text': 'Health check', 'url': '/health', 'method': 'GET', 'form': '' }
            ];

export const FORM_SPECS = {
                '': { 'fields': [] },
                'camel-form': {
                    'fields': [
                        { 'name': 'applicationId', 'text': 'CAMEL model path' },
                        { 'name': 'watermark.user', 'text': '-- User', 'defaultValue': function(_this) { return _this.authUsername ?? _this.username ?? 'unknown'; } },
                        { 'name': 'watermark.system', 'text': '-- System', 'defaultValue': 'Ems-Web-Admin' },
                        { 'name': 'watermark.uuid', 'text': '-- UUID', 'defaultValue': function(_this) { return _this.create_UUID(); } },
                        { 'name': 'watermark.date', 'text': '-- Date', 'defaultValue': function() { return new Date().getTime(); } },
                    ],
                },
                'cp-form': {
                    'fields': [
                        { 'name': 'cp-model-id', 'text': 'CP model path' },
                    ]
                },
                'vm-form': {
                    'fields': [
                        { 'name': 'id', 'text': 'VM Id' },
                        { 'name': 'name', 'text': 'VM Name' },
                        { 'name': 'operatingSystem', 'text': 'VM OS', 'defaultValue': 'UBUNTU' },
                        { 'name': 'type', 'text': 'VM type', 'defaultValue': 'VM' },
                        { 'name': 'provider', 'text': 'VM provider' },
                        { 'name': 'address', 'text': 'IP address' },
                        { 'name': 'ssh.port', 'text': 'SSH port', 'defaultValue': '22' },
                        { 'name': 'ssh.username', 'text': 'SSH username' },
                        { 'name': 'ssh.password', 'text': 'SSH password' },
                        { 'name': 'ssh.key', 'text': 'SSH key' },
                    ]
                },
                'app-id-form': {
                    'fields': [
                        { 'name': 'appId', 'text': 'Application Id' },
                    ]
                },
                'ref-form': {
                    'fields': [
                        { 'name': 'ref', 'text': 'VM reference' },
                    ]
                },
                'ip-form': {
                    'fields': [
                        { 'name': 'ip-address', 'text': 'IP Address' },
                    ]
                },
                'otp-form': {
                    'fields': [
                        { 'name': 'otp', 'text': 'OTP' },
                    ]
                },
            };
