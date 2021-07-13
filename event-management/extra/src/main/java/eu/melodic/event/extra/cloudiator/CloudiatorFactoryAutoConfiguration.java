/*
 * Copyright (C) 2017-2021 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.extra.cloudiator;

import eu.melodic.event.baguette.client.install.ClientInstallationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan("eu.melodic.event.extra.cloudiator")
@RequiredArgsConstructor
public class CloudiatorFactoryAutoConfiguration {

    private final ClientInstallationProperties properties;

    @Bean
    public CloudiatorInstallationHelper cloudiatorInstallationHelperFactory() {
        log.debug("cloudiatorInstallationHelperFactory(): Properties before initializing helper: {}", properties);
        CloudiatorInstallationHelper helper = new CloudiatorInstallationHelper();
        helper.setProperties(properties);
        return helper;
    }
}