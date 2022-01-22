/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.webconf;

import eu.melodic.event.util.PasswordUtil;
import eu.paasage.upperware.security.authapi.JWTAuthorizationFilter;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@Order(1)
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(MelodicSecurityProperties.class)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MelodicSecurityProperties melodicSecurityProperties;

    @Value("${melodic.security.enabled:true}")
    private boolean securityEnabled;

    // JWT Token authentication fields
    @Value("${web.jwt-token-authentication.enabled:true}")
    private boolean jwtTokenAuthEnabled;

    // API-Key authentication fields
    @Value("${web.api-key.header:EMS-API-KEY}")
    private String apiKeyRequestHeader;
    @Value("${web.api-key.parameter:ems-api-key}")
    private String apiKeyRequestParam;
    @Value("${web.api-key.value:#{null}}")
    private String apiKeyValue;

    @Autowired
    private PasswordUtil passwordUtil;


    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        String sep = "--------------------------------------------------------------------------------";
        log.warn("afterPropertiesSet: Sample JWT Token: \n{}\nBearer {}\n{}", sep, jwtService(melodicSecurityProperties).create("USER"), sep);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/favicon.ico")
                .antMatchers("/health");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF and sessions (it's a REST API)
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // No security at all
        boolean apiKeyAuthEnabled = StringUtils.isNotBlank(apiKeyValue);
        log.debug("WebSecurityConfig: security-enabled={}, jwt-auth-enabled={}, api-key-auth-enabled={}",
                securityEnabled, jwtTokenAuthEnabled, apiKeyAuthEnabled);
        if (!securityEnabled || !jwtTokenAuthEnabled && !apiKeyAuthEnabled) {
            log.warn("WebSecurityConfig: Authentication is disabled");
            // Authorize all requests
            /*httpSecurity
                    .authorizeRequests()
                    .antMatchers("/**").permitAll();*/
            return;
        }

        // Add configured authentication filters
        if (apiKeyAuthEnabled) {
            log.debug("WebSecurityConfig: Adding API-Key filter");
            httpSecurity
                    .addFilterBefore(apiKeyAuthenticationFilter(), BasicAuthenticationFilter.class);
        }
        if (jwtTokenAuthEnabled) {
            log.debug("WebSecurityConfig: Adding JWT-Token filter");
            httpSecurity
                    .addFilterAfter(new JWTAuthorizationFilter(authenticationManager(), jwtService(melodicSecurityProperties)),
                            BasicAuthenticationFilter.class);
        }

        // Apply authentication to all URLs
        httpSecurity
                .authorizeRequests()
                .antMatchers("/**").authenticated();
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        return authentication -> authentication;
    }

    public JWTService jwtService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }

    public Filter apiKeyAuthenticationFilter() {
        return (servletRequest, servletResponse, filterChain) -> {
            log.trace("apiKeyAuthenticationFilter: BEGIN: request={}", servletRequest);
            if (StringUtils.isNotBlank(apiKeyValue)) {
                if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
                    HttpServletRequest request = (HttpServletRequest) servletRequest;
                    log.trace("apiKeyAuthenticationFilter: http-request={}", request);
                    String apiKey = request.getHeader(apiKeyRequestHeader);
                    log.debug("apiKeyAuthenticationFilter: Request Header API Key: {}={}", apiKeyRequestHeader, passwordUtil.encodePassword(apiKey));
                    if (StringUtils.isBlank(apiKey)) {
                        apiKey = request.getParameter(apiKeyRequestParam);
                        log.debug("apiKeyAuthenticationFilter: Request Parameter API Key: {}={}", apiKeyRequestParam, passwordUtil.encodePassword(apiKey));
                    }
                    if (StringUtils.isBlank(apiKey) || StringUtils.isNotBlank(apiKey) && ! apiKey.equals(apiKeyValue)) {
                        log.debug("apiKeyAuthenticationFilter: API Key: No Match");
                        ((HttpServletResponse) servletResponse).setStatus(401);
                        return;
                    }
                    log.debug("apiKeyAuthenticationFilter: API Key: Matched");
                } else {
                    throw new IllegalArgumentException("API Key Authentication filter does not support non-HTTP requests and responses. Req-class: "
                            +servletRequest.getClass().getName()+"  Resp-class: "+servletResponse.getClass().getName());
                }
            } else {
                log.warn("apiKeyAuthenticationFilter: No API-Key specified. Access is granted");
            }

            try {
                // construct one of Spring's auth tokens
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(apiKeyRequestHeader, apiKeyValue, Collections.singletonList(
                                new SimpleGrantedAuthority("USER-ROLE")));
                // store completed authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // continue down the chain.
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception e) {
                log.error("apiKeyAuthenticationFilter: EXCEPTION: ", e);
            }
        };
    }
}