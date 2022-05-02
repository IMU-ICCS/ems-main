/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "static")
public class StaticResourceProperties {
    private String faviconContext = "/favicon.ico";
    private String faviconPath;

    private String resourceContext = "/resources/**";
    private String[] resourcePath;

    private String resourceRedirect;
    private Map<String,String> resourceRedirects = new LinkedHashMap<>();

    private String logsContext = "/logs/**";
    private String[] logsPath;
}
