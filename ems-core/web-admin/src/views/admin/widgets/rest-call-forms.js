/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

export const FORM_TYPE_OPTIONS = [
                { 'id': 'new-camel', 'text': 'Send CAMEL model request', 'url': '/camelModel', 'method': 'POST', 'form': 'camel-form', 'priority': -1008 },
                { 'id': 'new-cp', 'text': 'Send CP model request', 'url': '/cpModelJson', 'method': 'POST', 'form': 'cp-form', 'priority': -1007 },
                { 'id': 'const', 'text': 'Set constants (add name-value pairs in Payload)', 'url': '/cpConstants', 'method': 'POST', 'form': '', 'priority': -1006 },
                { 'id': 'new-vm', 'text': 'Register Node', 'url': '/baguette/registerNode', 'method': 'POST', 'form': 'vm-form', 'priority': -1005 },
                { 'id': 'vm-list', 'text': 'Node IP addresses', 'url': '/baguette/node/list', 'method': 'GET', 'form': '', 'priority': -1004 },
                { 'id': 'vm-reinstall', 'text': 'Reinstall Node', 'url': '/baguette/node/reinstall/{ip-address}', 'method': 'GET', 'form': 'vm-reinstall', 'priority': -1003 },
                { 'id': 'get-camel', 'text': 'Current CAMEL model', 'url': '/translator/currentCamelModel', 'method': 'GET', 'form': '', 'priority': -1002 },
                { 'id': 'get-cp', 'text': 'Current CP model', 'url': '/translator/currentCpModel', 'method': 'GET', 'form': '', 'priority': -1001 },

                { 'id': 'get-all-logger-levels', 'text': 'Get All Loggers', 'url': '/actuator/loggers', 'method': 'GET', 'form': '', 'priority': 1001 },
                { 'id': 'get-logger-level', 'text': 'Get Logger Level', 'url': '/actuator/loggers/{logger}', 'method': 'GET', 'form': 'logger-form', 'priority': 1002 },
                { 'id': 'set-logger-level', 'text': 'Set Logger Level', 'url': '/actuator/loggers/{logger}', 'method': 'POST', 'form': 'logger-form', 'priority': 1003 },

                { 'id': 'get-cred', 'text': 'EMS server Broker credentials', 'url': '/broker/credentials', 'method': 'GET', 'form': '', 'priority': 1004 },
                { 'id': 'get-ref', 'text': 'VM credentials by Ref', 'url': '/baguette/ref/{ref}', 'method': 'GET', 'form': 'ref-form', 'priority': 1005 },

                { 'id': 'client-list', 'text': 'Client list', 'url': '/client/list', 'method': 'GET', 'form': '', 'priority': 1006 },
                { 'id': 'client-map', 'text': 'Client map', 'url': '/client/list/map', 'method': 'GET', 'form': '', 'priority': 1007 },
                { 'id': 'node-info', 'text': 'Node Info by IP address', 'url': '/baguette/getNodeInfoByAddress/{ip-address}', 'method': 'GET', 'form': 'ip-form', 'priority': 1008 },
                { 'id': 'node-name', 'text': 'Node Name by IP address', 'url': '/baguette/getNodeNameByAddress/{ip-address}', 'method': 'GET', 'form': 'ip-form', 'priority': 1009 },

                { 'id': 'new-otp', 'text': 'New OTP', 'url': '/ems/otp/new', 'method': 'GET', 'form': '', 'priority': 1010 },
                { 'id': 'del-otp', 'text': 'Delete OTP', 'url': '/ems/otp/remove/{otp}', 'method': 'GET', 'form': 'otp-form', 'priority': 1011 },

                { 'id': 'd-stop-baguette', 'text': 'DEBUG - Stop Baguette Server', 'url': '/baguette/stopServer', 'method': 'GET', 'form': '', 'priority': 1012 },
                { 'id': 'd-shutdown', 'text': 'DEBUG - EMS server shutdown', 'url': '/ems/shutdown', 'method': 'GET', 'form': '', 'priority': 1013 },
                { 'id': 'd-exit', 'text': 'DEBUG - EMS server shutdown and Exit', 'url': '/ems/exit', 'method': 'GET', 'form': '', 'priority': 1014 },
                { 'id': 'd-restart', 'text': 'DEBUG - EMS server shutdown and Restart', 'url': '/ems/exit/99', 'method': 'GET', 'form': '', 'priority': 1015 },

                { 'id': 'health', 'text': 'Health check', 'url': '/health', 'method': 'GET', 'form': '', 'priority': 1016 }
            ];

export const FORM_SPECS = {
                '': { 'fields': [] },
                'camel-form': {
                    'fields': [
                        { 'name': 'applicationId', 'text': 'CAMEL model path' },
                        { 'name': 'notificationURI', 'text': 'Notification URI' },
                        { 'name': 'watermark.user', 'text': '-- User', 'defaultValue': function(_this) { return ('authUsername' in _this) ? _this.authUsername : ('username' in _this) ? _this.username : 'unknown'; } },
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
                        { 'name': 'ssh.password', 'text': 'SSH password', 'type': 'password' },
                        { 'name': 'ssh.key', 'text': 'SSH key', 'type': 'password' },
                    ]
                },
                'vm-reinstall': {
                    'fields': [
                        { 'name': 'ip-address', 'text': 'IP address' },
                    ]
                },
                'logger-form': {
                    'fields': [
                        { 'name': 'logger', 'text': 'Logger name' },
                        { 'name': 'configuredLevel', 'text': 'New Level' },
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
