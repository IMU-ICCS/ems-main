/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.webconf;

import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.control.properties.StaticResourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer, InitializingBean {
    @Autowired
    private StaticResourceProperties properties;
    @Autowired
    private ControlServiceProperties controlServiceProperties;

    public void afterPropertiesSet() {
        log.debug("StaticResourceConfiguration: afterPropertiesSet: {}", properties);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String faviconContext = properties.getFavicon().getContext();
        String faviconPath = properties.getFavicon().getPath();
        if(StringUtils.isNotBlank(faviconPath)) {
            log.info("Serving favicon.ico from: {} --> {}", faviconContext, faviconPath);
            registry
                    .addResourceHandler(faviconContext)
                    .addResourceLocations(faviconPath);
        }
        String resourceContext = properties.getResource().getContext();
        List<String> resourcePath = properties.getResource().getPath();
        if (resourcePath != null && resourcePath.size() > 0) {
            log.info("Serving static content from: {} --> {}", resourceContext, resourcePath);
            registry
                    .addResourceHandler(resourceContext)
                    .addResourceLocations(resourcePath.toArray(new String[0]));
        }
        String logsContext = properties.getLogs().getContext();
        List<String> logsPath = properties.getLogs().getPath();
        if (logsPath != null && logsPath.size() > 0) {
            log.info("Serving logs from: {} --> {}", logsContext, logsPath);
            registry
                    .addResourceHandler(logsContext)
                    .addResourceLocations(logsPath.toArray(new String[0]));
        }

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Remains for backward compatibility (of properties file)
        String resourceRedirect = properties.getRedirect();
        if (StringUtils.isNotBlank(resourceRedirect)) {
            log.info("Redirecting / to: {}", resourceRedirect);
            registry
                    .addViewController("/")
                    .setViewName("redirect:" + resourceRedirect);
        }

        Map<String,String> resourceRedirects = properties.getRedirects();
        log.debug("Configured resource redirects: {}", resourceRedirects);
        if (resourceRedirects!=null) {
            resourceRedirects.forEach((context, redirect) -> {
                if (StringUtils.isNotBlank(context) && StringUtils.isNotBlank(redirect)) {
                    context = context.trim();
                    redirect = redirect.trim();
                    log.info("Redirecting {} to: {}", context, redirect);
                    registry
                            .addViewController(context)
                            .setViewName("redirect:" + redirect);
                }
            });
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
