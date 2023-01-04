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
import eu.melodic.security.authorization.client.AuthorizationServiceTomcatInterceptor;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ComponentScan(basePackages={"eu.melodic.security.authorization.util.properties"})
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    public final static String[] DEFAULT_PATHS_PROTECTED = { "/**" };
    public final static String[] DEFAULT_PATHS_EXCLUDED = { };

    private final AuthorizationServiceClientProperties authProperties;
    private final ControlServiceProperties controlServiceProperties;
    private final ApplicationContext applicationContext;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        // Add authorization interceptor (if configured)
        boolean authEnabled = controlServiceProperties.getAuthorization().isEnabled();
        String[] authPathsProtected = controlServiceProperties.getAuthorization().getPathsProtected().toArray(new String[0]);
        String[] authPathsExcluded = controlServiceProperties.getAuthorization().getPathsExcluded().toArray(new String[0]);
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

    @Bean
    public javax.servlet.Filter contentCachingFilter() {
        log.debug("contentCachingFilter(): Registering content caching request filter");
        return (servletRequest, servletResponse, filterChain) -> {
            log.trace("contentCachingFilter(): request={}", servletRequest);
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            //HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

            ServletRequest contentCachingRequestWrapper = new ContentCachingRequestWrapper(httpRequest);
            //ServletResponse contentCachingResponseWrapper = new ContentCachingResponseWrapper(httpResponse);
            log.trace("contentCachingFilter(): request={}, content-caching-request={}", servletRequest, contentCachingRequestWrapper);
            //log.trace("contentCachingFilter(): response={}, content-caching-response={}", servletResponse, contentCachingResponseWrapper);

            filterChain.doFilter(contentCachingRequestWrapper, servletResponse);
            //filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
        };
    }
}