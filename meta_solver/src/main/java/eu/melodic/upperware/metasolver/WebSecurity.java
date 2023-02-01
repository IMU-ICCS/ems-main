/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.upperware.metasolver.util.jwt.JwtTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private static final String ROLE_JWT_TOKEN = "ROLE_JWT_TOKEN";
    private static final String JWT_REQUEST_PARAM = "jwt";

    private final JwtTokenService jwtTokenService;
    @Value("${melodic.security.enabled:true}")
    private boolean securityEnabled;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors().and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        if (securityEnabled) {
            log.info("Running WITH security");
            httpSecurity
                    .authorizeHttpRequests(
                            authorize -> authorize.anyRequest().authenticated())
                    .addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        } else {
            log.info("Running WITHOUT security");
            httpSecurity
                    .authorizeHttpRequests(
                            authorize -> authorize.anyRequest().permitAll());
        }

        return httpSecurity.build();
    }

    public Filter jwtAuthorizationFilter() {
        return (servletRequest, servletResponse, filterChain) -> {
            if (servletRequest instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) servletRequest;

                // Get JWT token from Authorization header
                String jwtValue = req.getHeader(JwtTokenService.HEADER_STRING);
                log.debug("jwtAuthorizationFilter: Authorization Header: {}", jwtValue);

                // ...else get JWT token from 'jwtRequestParam' query parameter
                if (StringUtils.isBlank(jwtValue)) {
                    if (StringUtils.isNotBlank(JWT_REQUEST_PARAM)) {
                        log.debug("jwtAuthorizationFilter: Authorization Header is missing. Checking for '{}' parameter", JWT_REQUEST_PARAM);
                        jwtValue = req.getParameter(JWT_REQUEST_PARAM);
                        log.debug("jwtAuthorizationFilter: '{}' parameter value: {}", JWT_REQUEST_PARAM, jwtValue);
                        if (StringUtils.isNotBlank(jwtValue))
                            jwtValue = JwtTokenService.TOKEN_PREFIX + jwtValue;
                    } else {
                        log.debug("jwtAuthorizationFilter: JWT token not found in headers and no JWT token parameter has been set");
                    }
                }

                // Check JWT token validity
                if (jwtValue!=null && jwtValue.startsWith(JwtTokenService.TOKEN_PREFIX)) {
                    try {
                        log.debug("jwtAuthorizationFilter: Parsing Authorization header...");
                        Claims claims = jwtTokenService.parseToken(jwtValue);
                        String user = claims.getSubject();
                        String audience  = claims.getAudience();
                        log.debug("jwtAuthorizationFilter: Authorization header -->     user: {}", user);
                        log.debug("jwtAuthorizationFilter: Authorization header --> audience: {}", audience);
                        if (user!=null && audience!=null) {
                            if (JwtTokenService.AUDIENCE_UPPERWARE.equals(audience)) {
                                log.debug("jwtAuthorizationFilter: JWT token is valid");
                                UsernamePasswordAuthenticationToken authentication =
                                        new UsernamePasswordAuthenticationToken(user, null,
                                                Collections.singletonList(new SimpleGrantedAuthority(ROLE_JWT_TOKEN)));
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                log.debug("jwtAuthorizationFilter: Security context updated");
                            } else {
                                log.debug("jwtAuthorizationFilter: Audience claim is invalid: {}", audience);
                            }
                        } else {
                            log.debug("jwtAuthorizationFilter: JWT token does not contain claim Audience");
                        }
                    } catch (Exception ex) {
                        log.debug("jwtAuthorizationFilter: JWT token is not valid: EXCEPTION: ", ex);
                    }
                } else {
                    log.debug("jwtAuthorizationFilter: No or invalid Authorization header");
                }
            } else {
                log.warn("jwtAuthorizationFilter: Not an HttpServletRequest");
            }

            // continue filter chain processing
            filterChain.doFilter(servletRequest, servletResponse);
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        if (securityEnabled) {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
            return source;
        }
        return null;
    }

}
