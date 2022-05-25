/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.properties;

import eu.melodic.event.util.EmsConstant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = EmsConstant.EMS_PROPERTIES_PREFIX + "web.static")
public class StaticResourceProperties {
    private ResourceOneMapping favicon = ResourceOneMapping.builder().context("/favicon.ico").build();
    private ResourceMappings resource = ResourceMappings.builder().context("/resources/**").build();
    private ResourceMappings logs = ResourceMappings.builder().context("/logs/**").build();

    private String redirect;
    private Map<String,String> redirects = new LinkedHashMap<>();

    @Data
    @Builder
    public static class ResourceOneMapping {
        private String context;
        private String path;
    }

    @Data
    @Builder
    public static class ResourceMappings {
        private String context;
        private List<String> path;
    }
}
