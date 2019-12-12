/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.properties;

import eu.melodic.event.util.CredentialsMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "baguette.server")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.baguette-server.properties")
@Slf4j
public class BaguetteServerProperties {
    @NotNull
    @Size(min = 1, message = "Please provide a valid Coordinator class (use Fully-Qualified Class Name)")
    @Value("${baguette.server.coordinator.class}")
    private String coordinatorClass;

    @Value("${baguette.server.registration-window:30000}")
    @Min(-1)
    private long registrationWindow;
    @Value("${baguette.server.num-of-instances:-1}")
    @Min(-1)
    private int numberOfInstances;
    @Value("${baguette.server.num-of-segments:-1}")
    @Min(-1)
    private int NumberOfSegments;

    //@Value("#{ '${baguette.server.address}'!='' ? '${baguette.server.address}' : T(eu.melodic.event.util.NetUtil).getPublicIpAddress() }")
    @Value("${baguette.server.address:}")
    private String serverAddress;

    public String getServerAddress() {
        String oldVal = serverAddress;
        if (StringUtils.isEmpty(serverAddress) || "%{PUBLIC_IP}%".equals(serverAddress.trim())) {
            serverAddress = eu.melodic.event.util.NetUtil.getPublicIpAddress();
            log.info("BaguetteServerProperties: Set serverAddress to PUBLIC: {} -> {}", oldVal, serverAddress);
        } else if ("%{DEFAULT_IP}%".equals(serverAddress.trim())) {
            serverAddress = eu.melodic.event.util.NetUtil.getDefaultIpAddress();
            log.info("BaguetteServerProperties: Set serverAddress to DEFAULT: {} -> {}", oldVal, serverAddress);
        }
        return serverAddress;
    }

    @Value("${baguette.server.port:2222}")
    @Min(value = 1, message = "Valid server ports are between 1 and 65535. Please prefer ports higher than 1023.")
    @Max(value = 65535, message = "Valid server ports are between 1 and 65535. Please prefer ports higher than 1023.")
    private int serverPort;

    @Value("${baguette.server.key.file:hostkey.ser}")
    private String serverKeyFile;
    @Value("${baguette.server.heartbeat:false}")
    private boolean heartbeatEnabled;
    @Value("${baguette.server.heartbeat.period:60000}")
    @Min(-1)
    private long heartbeatPeriod;

    @Value("${baguette.server.debug.client-address-override-allowed:false}")
    private boolean clientAddressOverrideAllowed;
    @Value("${baguette.server.client-id-format}")
    private String clientIdFormat;
    @Value("${baguette.server.client-id-format.escape:~}")
    private String clientIdFormatEscape;

    private final CredentialsMap credentials = new CredentialsMap();
}
