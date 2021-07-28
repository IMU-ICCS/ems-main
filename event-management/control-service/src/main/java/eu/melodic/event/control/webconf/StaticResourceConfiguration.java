/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.webconf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Slf4j
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Value("${static.favicon.context:/favicon.ico}")
    private String faviconContext;
    @Value("${static.favicon.path:#{null}}")
    private String faviconPath;

    @Value("${static.resource.context:/**}")
    private String staticResourceContext;
    @Value("${static.resource.path:#{null}}")
    private String[] staticResourcePath;
    @Value("${static.resource.redirect:#{null}}")
    private String staticResourceRedirect;

    @Value("${static.logs.context:/logs/**}")
    private String staticLogsContext;
    @Value("${static.logs.path:#{null}}")
    private String[] staticLogsPath;

    @Value("${event-debug.resource.context:/event-debug/**}")
    private String eventDebugResourceContext;
    @Value("${event-debug.resource.path:#{null}}")
    private String[] eventDebugResourcePath;
    @Value("${control.event-debug-enabled:false}")
    private boolean eventDebugEnabled;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(StringUtils.isNotBlank(faviconPath)) {
            log.info("Serving favicon.ico from: {} --> {}", faviconContext, faviconPath);
            registry
                    .addResourceHandler(faviconContext)
                    .addResourceLocations(faviconPath);
        }
        if(staticResourcePath != null && staticResourcePath.length > 0) {
            log.info("Serving static content from: {} --> {}", staticResourceContext, staticResourcePath);
            registry
                    .addResourceHandler(staticResourceContext)
                    .addResourceLocations(staticResourcePath);
        }
        if(staticLogsPath != null && staticLogsPath.length > 0) {
            log.info("Serving logs from: {} --> {}", staticLogsContext, staticLogsPath);
            registry
                    .addResourceHandler(staticLogsContext)
                    .addResourceLocations(staticLogsPath);
        }

        if(eventDebugEnabled && eventDebugResourcePath != null && eventDebugResourcePath.length > 0) {
            log.info("Serving event-debug content from: {} --> {}", eventDebugResourceContext, eventDebugResourcePath);
            registry
                    .addResourceHandler(eventDebugResourceContext)
                    .addResourceLocations(eventDebugResourcePath)
                    //.setCachePeriod(0)
            ;
        }

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (StringUtils.isNotBlank(staticResourceRedirect)) {
            log.info("Redirecting / to: {}", staticResourceRedirect);
            registry
                    .addViewController("/")
                    .setViewName("redirect:" + staticResourceRedirect);
        }

        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @ConditionalOnProperty(name="control.log-requests", matchIfMissing = true)
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setIncludeClientInfo(true);

        filter.setBeforeMessagePrefix("REQUEST DATA BEFORE: >>");
        filter.setBeforeMessageSuffix("<< REQUEST DATA BEFORE");
        filter.setAfterMessagePrefix("REQUEST DATA AFTER: >>");
        filter.setAfterMessageSuffix("<< REQUEST DATA AFTER");
        return filter;
    }
}
