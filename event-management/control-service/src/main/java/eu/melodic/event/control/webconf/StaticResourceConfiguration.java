/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.webconf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Slf4j
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Value("${static.resource.context:/**}")
    private String staticResourceContext;
    @Value("${static.resource.path:#{null}}")
    private String[] staticResourcePath;
    @Value("${static.resource.redirect:#{null}}")
    private String staticResourceRedirect;

    @Value("${event-debug.resource.context:/event-debug/**}")
    private String eventDebugResourceContext;
    @Value("${event-debug.resource.path:#{null}}")
    private String[] eventDebugResourcePath;
    @Value("${control.event-debug-enabled:false}")
    private boolean eventDebugEnabled;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(staticResourcePath != null) {
            log.info("Serving static content from: {} --> {}", staticResourceContext, staticResourcePath);
            registry
                    .addResourceHandler(staticResourceContext)
                    .addResourceLocations(staticResourcePath);
        }

        if(eventDebugEnabled && eventDebugResourcePath != null) {
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
        if (staticResourceRedirect != null) {
            log.info("Redirecting / to: {}", staticResourceRedirect);
            registry
                    .addViewController("/")
                    .setViewName("redirect:" + staticResourceRedirect);
        }

        WebMvcConfigurer.super.addViewControllers(registry);
    }
}
