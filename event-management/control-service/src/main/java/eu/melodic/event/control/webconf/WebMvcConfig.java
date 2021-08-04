/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.webconf;

import eu.melodic.security.authorization.client.AuthorizationServiceTomcatInterceptor;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ComponentScan(basePackages={"eu.melodic.security.authorization.util.properties"})
public class WebMvcConfig implements WebMvcConfigurer {
    private final static String[] DEFAULT_PATHS_PROTECTED = { "/**" };
    private final static String[] DEFAULT_PATHS_EXCLUDED = { };

    @Autowired
    private AuthorizationServiceClientProperties authProperties;
    @Autowired
    private ApplicationContext applicationContext;

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

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(applicationContext.getBean("asyncExecutor", AsyncTaskExecutor.class));
    }

    @Bean(name="asyncExecutor")
    public AsyncTaskExecutor asyncExecutor() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        log.debug("asyncExecutor(): ThreadPoolExecutor: core={}, max={}, size={}, active={}, keep-alive={}",
                executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getPoolSize(),
                executor.getActiveCount(), executor.getKeepAliveTime(TimeUnit.SECONDS));
        return new ConcurrentTaskExecutor(executor);
    }
}