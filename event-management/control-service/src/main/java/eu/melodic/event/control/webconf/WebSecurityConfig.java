/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.webconf;

import eu.paasage.upperware.security.authapi.JWTAuthorizationFilter;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(MelodicSecurityProperties.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Order(1)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // JWT authentication fields
    private final JWTService jwtService;

    @Value("${melodic.security.enabled:true}")
    private boolean securityEnabled;

    // API-Key authentication fields
    @Value("${web.api-key.header:EMS-API-KEY}")
    private String principalRequestHeader;
    @Value("${web.api-key.parameter:ems-api-key}")
    private String principalRequestParam;
    @Value("${web.api-key.value:#{null}}")
    private String principalRequestValue;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (! securityEnabled) {
            log.warn("WebSecurityConfig: JWT-based authentication is disabled");
        }

        if (StringUtils.isBlank(principalRequestValue)) {
            log.warn("WebSecurityConfig: No API-KEY specified in configuration. API-Key-based authentication will be disabled");
        }

        if ("generate".equalsIgnoreCase(principalRequestValue)) {
            principalRequestValue = RandomStringUtils.randomAlphanumeric(30);
            log.info("WebSecurityConfig: API key generated: {}", principalRequestValue);
        }

        // Initialize API-Key authentication filter, if configured
        APIKeyAuthFilter apiKeyFilter = null;
        if (! StringUtils.isBlank(principalRequestValue)) {
            // initialize API-Key authentication filter
            apiKeyFilter = new APIKeyAuthFilter(principalRequestHeader, principalRequestParam);
            apiKeyFilter.setAuthenticationManager(authenticationManager());
        }

        // Add JWT-based authentication, if configured
        if (securityEnabled) {
            log.info("WebSecurityConfig: Running WITH JWT security");

            if (apiKeyFilter!=null) {
                log.info("WebSecurityConfig: Registering API-Key authentication");
                httpSecurity
                        .cors()
                        .and()
                        .csrf().disable()
                        .authorizeRequests()
                        .anyRequest().authenticated()
                        .and()
                        .addFilter(apiKeyFilter)
                        .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
                        // this disables session creation on Spring Security
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            } else {
                log.info("WebSecurityConfig: API-Key authentication NOT used");
                httpSecurity
                        .cors()
                        .and()
                        .csrf().disable()
                        .authorizeRequests()
                        .anyRequest().authenticated()
                        .and()
                        .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
                        // this disables session creation on Spring Security
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            }
        } else {
            log.info("WebSecurityConfig: Running WITHOUT JWT security");

            if (apiKeyFilter!=null) {
                log.info("WebSecurityConfig: Registering API-Key authentication");
                httpSecurity
                        .csrf().disable()
                        .antMatcher("/**")
                        .addFilter(apiKeyFilter)
                        .authorizeRequests().anyRequest().authenticated()
                        .and()
                        // this disables session creation on Spring Security
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            } else {
                log.info("WebSecurityConfig: API-Key authentication NOT used");
                httpSecurity
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        // this disables session creation on Spring Security
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            }
        }

    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers("/baguette/registerNode");
    }

    // Cors configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        if (securityEnabled) {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
            return source;
        }
        return null;
    }

    // JWT authentication manager
    @Bean
    protected AuthenticationManager authenticationManager() {
        return authentication -> {
            log.debug("APIKeyAuthFilter.authenticate(): Authenticating request: {}", authentication);
            if (StringUtils.isNotBlank(principalRequestValue)) {
                String principal = (String) authentication.getPrincipal();
                log.debug("APIKeyAuthFilter.authenticate(): Comparing configured api-key to request principal: api-key={}, principal={}", principalRequestValue, principal);
                if (!principalRequestValue.equals(principal)) {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
            }
            authentication.setAuthenticated(true);
            log.debug("APIKeyAuthFilter.authenticate(): Authenticated");
            return authentication;
        };
    }

    // API Key authentication filter
    @Slf4j
    public static class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

        private String principalRequestHeader;
        private String principalRequestParameter;

        public APIKeyAuthFilter(String principalRequestHeader, String principalRequestParam) {
            this.principalRequestHeader = StringUtils.stripToNull(principalRequestHeader);
            this.principalRequestParameter = StringUtils.stripToNull(principalRequestParam);
            log.debug("APIKeyAuthFilter: API-KEY Header: {}", principalRequestHeader);
            log.debug("APIKeyAuthFilter: API-KEY Parameter: {}", principalRequestParam);
        }

        @Override
        protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
            String headerApiKey = null;
            if (principalRequestHeader!=null) {
                headerApiKey = request.getHeader(principalRequestHeader);
                log.debug("APIKeyAuthFilter: Api-Key Header '{}' value: {}", principalRequestHeader, headerApiKey);
            }
            String paramApiKey = null;
            if (principalRequestParameter!=null) {
                paramApiKey = request.getParameter(principalRequestParameter);
                log.debug("APIKeyAuthFilter: Api-Key Parameter '{}' value: {}", principalRequestParameter, paramApiKey);
            }
            String effectiveApiKey = Optional.ofNullable(headerApiKey).orElse(paramApiKey);
            log.debug("APIKeyAuthFilter: Effective Api-Key: {}", effectiveApiKey);
            return Optional.ofNullable(effectiveApiKey).orElse("");
        }

        @Override
        protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
            return "N/A";
        }
    }
}