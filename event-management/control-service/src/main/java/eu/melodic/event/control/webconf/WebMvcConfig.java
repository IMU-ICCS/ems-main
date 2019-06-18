/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.webconf;

import eu.melodic.security.authorization.client.AuthorizationServiceTomcatInterceptor;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@ComponentScan(basePackages={"eu.melodic.security.authorization.util.properties"})
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    private final static String[] DEFAULT_PATHS_PROTECTED = { "/**" };
    private final static String[] DEFAULT_PATHS_EXCLUDED = { };

    @Autowired
    private AuthorizationServiceClientProperties authProperties;

    @Value("${authorization.enabled:true}")
    private boolean authEnabled;
    @Value("${authorization.paths-protected:}")
    private String[] authPathsProtected;
    @Value("${authorization.paths-excluded:}")
    private String[] authPathsExcluded;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add authorization interceptor (if configured)
        if (!authEnabled || authProperties.getPdp().isDisabled()) {
            log.warn("WebMvcConfig.addInterceptors(): Authorization check is disabled");
        } else {
            log.info("WebMvcConfig.addInterceptors(): Authorization check is enabled");

            if (ArrayUtils.isEmpty(authPathsProtected)) authPathsProtected = DEFAULT_PATHS_PROTECTED;
            if (ArrayUtils.isEmpty(authPathsExcluded)) authPathsExcluded = DEFAULT_PATHS_EXCLUDED;
            log.warn("WebMvcConfig.addInterceptors(): Authorization check: paths-protected={}, paths-excluded={}",
                    authPathsProtected, authPathsExcluded);

            log.debug("WebMvcConfig.addInterceptors(): Authorization properties: {}", authProperties);
            registry
                    .addInterceptor(AuthorizationServiceTomcatInterceptor.getSingleton(authProperties))
                    .addPathPatterns(authPathsProtected)
                    .excludePathPatterns(authPathsExcluded)
            ;
            log.debug("WebMvcConfig.addInterceptors(): Registered Authorization interceptor");
        }
    }
}