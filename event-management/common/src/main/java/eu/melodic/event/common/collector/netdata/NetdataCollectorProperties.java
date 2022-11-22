/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.common.collector.netdata;

import eu.melodic.event.common.collector.AbstractEndpointCollectorProperties;
import eu.melodic.event.util.EmsConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = EmsConstant.EMS_PROPERTIES_PREFIX + "collector.netdata")
public class NetdataCollectorProperties extends AbstractEndpointCollectorProperties {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("NetdataCollectorProperties: {}", this);
    }
}
