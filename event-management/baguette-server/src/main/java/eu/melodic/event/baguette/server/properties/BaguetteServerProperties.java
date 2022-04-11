/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.properties;

import eu.melodic.event.baguette.server.ServerCoordinator;
import eu.melodic.event.util.CredentialsMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "baguette.server")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.baguette-server.properties")
@Slf4j
public class BaguetteServerProperties {

    /*XXX: TODO: Add combinatorial properties check
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.warn("!!!!!!!!!!!!  BaguetteServerProperties: {}", this);

        // Check that either coordinator class or id is provided
        if (coordinatorClass==null && StringUtils.isBlank(coordinatorId))
            throw new IllegalArgumentException("Either coordinator class or id must be provided");
        if (StringUtils.isNotBlank(coordinatorId)) {
            CoordinatorConfig cc = getCoordinatorConfig().get(coordinatorId);
            if (cc==null)
                throw new IllegalArgumentException("Not found coordinator configuration with id: "+coordinatorId);
            if (cc.getCoordinatorClass()==null)
                throw new IllegalArgumentException("No coordinator class in configuration with id: "+coordinatorId);
        }
    }*/

    //@Size(min = 1, message = "Please provide a valid Coordinator class (use Fully-Qualified Class Name)")
    @Value("${baguette.server.coordinator.class:}")
    private Class<ServerCoordinator> coordinatorClass;
    private Map<String,String> coordinatorParameters = new HashMap<>();

    @Value("${baguette.server.coordinator.id:}")
    private List<String> coordinatorId;
    private Map<String, CoordinatorConfig> coordinatorConfig = new HashMap<>();

    @Value("${baguette.server.registration-window:30000}")
    @Min(-1)
    private long registrationWindow;
    @Value("${baguette.server.num-of-instances:-1}")
    @Min(-1)
    private int numberOfInstances;
    @Value("${baguette.server.num-of-segments:-1}")
    @Min(-1)
    private int NumberOfSegments;

    @Value("${baguette.server.address:}")
    private String serverAddress;

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

    @Data
    public static class CoordinatorConfig {
        private Class<ServerCoordinator> coordinatorClass;
        private Map<String,String> parameters;
    }
}
