/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.extra.cloudiator;

import eu.melodic.event.util.EmsConstant;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = EmsConstant.EMS_PROPERTIES_PREFIX + "colosseum")
@PropertySource("file:${MELODIC_CONFIG_DIR}/cloudiator.properties")
public class CloudiatorUtilProperties {
    @NotNull
    private Colosseum colosseum = new Colosseum();

    @Data
    public static class Colosseum {
        @NotNull
        private String endpoint;
        @NotNull
        private String authEmail;
        @NotNull
        private String authTenant;
        @NotNull
        @ToString.Exclude
        private String authPassword;

        private boolean dbEnabled;
        private String dbDriver;
        private String dbUrl;
        private String dbUsername;
        @ToString.Exclude
        private String dbPassword;
        private String dbDatabase;

        private int ownerId = -1;
    }

    @NotNull
    private final List<ProviderPatternPair> providerEndpointPatterns = new ArrayList<>();
    @NotNull
    private final List<ProviderPatternPair> providerLocationPatterns = new ArrayList<>();

    @Data
    public static class ProviderPatternPair {
        private String pattern;
        private String provider;
    }
}