/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.webconf;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Order(1)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${web.api-key.header:EMS-API-KEY}")
    private String principalRequestHeader;
    @Value("${web.api-key.parameter:ems-api-key}")
    private String principalRequestParam;
    @Getter
    @Value("${web.api-key.value:#{null}}")
    private String principalRequestValue;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (StringUtils.isBlank(principalRequestValue)) {
            log.warn("WebSecurityConfig: No API-KEY specified in configuration. Web security will be disabled");
        }

        if ("generate".equalsIgnoreCase(principalRequestValue)) {
            principalRequestValue = RandomStringUtils.randomAlphanumeric(30);
            log.info("WebSecurityConfig: API key generated: {}", principalRequestValue);
        }

        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader, principalRequestParam);
        filter.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
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
            }
        });

        httpSecurity.
                antMatcher("/**").
                csrf().disable().
                //sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
                addFilter(filter).authorizeRequests().anyRequest().authenticated();
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