/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.properties;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/cloudiator.properties")
@Slf4j
public class CloudiatorUtilProperties {
    @NotNull @Value("${colosseum.endpoint}")
    private String colosseumEndpoint;
    @NotNull @Value("${colosseum.auth.email}")
    private String colosseumAuthEmail;
    @NotNull @Value("${colosseum.auth.tenant}")
    private String colosseumAuthTenant;
    @NotNull @Value("${colosseum.auth.password}")
    private String colosseumAuthPassword;

    @Value("${colosseum.db.enabled:false}")
    private boolean colosseumDbEnabled;
    @Value("${colosseum.db.driver:}")
    private String colosseumDbDriver;
    @Value("${colosseum.db.url:}")
    private String colosseumDbUrl;
    @Value("${colosseum.db.username:}")
    private String colosseumDbUsername;
    @Value("${colosseum.db.password:}")
    private String colosseumDbPassword;
    @Value("${colosseum.db.database:}")
    private String colosseumDbDatabase;

    @Value("${colosseum.owner-id:-1}")
    private int colosseumOwnerId;

    @NotNull
    private final List<ProviderPatternPair> providerEndpointPatterns = new ArrayList<>();
    @NotNull
    private final List<ProviderPatternPair> providerLocationPatterns = new ArrayList<>();

    @Getter
    @Setter
    @ToString
    public static class ProviderPatternPair {
        private String pattern;
        private String provider;
    }
}