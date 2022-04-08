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
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import io.jsonwebtoken.*;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Order(1)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(MelodicSecurityProperties.class)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public final static String ROLE_USER_FORM = "ROLE_USER_FORM";
    public static final String ROLE_JWT_TOKEN = "ROLE_JWT_TOKEN";
    public static final String ROLE_API_KEY = "ROLE_API_KEY";

    private final MelodicSecurityProperties melodicSecurityProperties;

    @Value("${melodic.security.enabled:true}")
    private boolean securityEnabled;

    // JWT Token authentication fields
    @Value("${web.jwt-token-authentication.enabled:true}")
    private boolean jwtTokenAuthEnabled;
    @Value("${web.jwt-print-sample-token:false}")
    private boolean printSampleJwt;

    // API-Key authentication fields
    @Value("${web.api-key-authentication.enabled:true}")
    private boolean apiKeyAuthEnabled;
    @Value("${web.api-key.header:EMS-API-KEY}")
    private String apiKeyRequestHeader;
    @Value("${web.api-key.parameter:ems-api-key}")
    private String apiKeyRequestParam;
    @Value("${web.api-key.value:#{null}}")
    private String apiKeyValue;

    @Autowired
    private PasswordUtil passwordUtil;

    // User form authentication fields
    @Value("${web.form-authentication.enabled:true}")
    private boolean userFormAuthEnabled;
    @Value("${web.form-authentication.username:admin}")
    private String username;
    @Value("${web.form-authentication.password:}")
    private String password;

    @Value("${web.permitted.urls:/login*,/logout*,/admin/login.html,/admin/favicon.ico,/admin/assets/**,/resources/*}")
    private String[] permittedUrls;
    @Value("${web.login.page:/admin/login.html}")
    private String loginPage;
    @Value("${web.login.url:/login}")
    private String loginUrl;
    @Value("${web.login.success.url:/}")
    private String loginSuccessUrl;
    @Value("${web.login.failure.url:/admin/login.html?error=Invalid+username+or+password}")
    private String loginFailureUrl;
    @Value("${web.logout.url:/logout}")
    private String logoutUrl;
    @Value("${web.logout.success.url:/admin/login.html?message=Signed+out}")
    private String logoutSuccessUrl;

    private final static String divider = "--------------------------------------------------------------------------------";

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        if (securityEnabled && userFormAuthEnabled && (StringUtils.isBlank(username) || password.isEmpty()))
            throw new InvalidParameterException("User form authentication is enabled but username or password are blank");
        if (securityEnabled && apiKeyAuthEnabled && StringUtils.isBlank(apiKeyValue))
            throw new InvalidParameterException("API Key authentication is enabled but no API Key provided or it is blank");
        if (permittedUrls==null) permittedUrls = new String[0];

        if (securityEnabled && userFormAuthEnabled) {
            log.info("afterPropertiesSet: Admin Username: {}", username);
            log.info("afterPropertiesSet: Admin Password: {}", passwordUtil.encodePassword(password));
        }
        if (securityEnabled && apiKeyAuthEnabled) {
            log.info("afterPropertiesSet: API Key: {}", passwordUtil.encodePassword(apiKeyValue));
        }
        if (printSampleJwt)
            log.info("afterPropertiesSet:\n{}\nSample JWT Token: \nBearer {}\n{}",
                divider, jwtService(melodicSecurityProperties).create("USER"), divider);

        log.debug("afterPropertiesSet:     securityEnabled: {}", securityEnabled);
        log.debug("afterPropertiesSet: jwtTokenAuthEnabled: {}", jwtTokenAuthEnabled);
        log.debug("afterPropertiesSet: apiKeyRequestHeader: {}", apiKeyRequestHeader);
        log.debug("afterPropertiesSet:  apiKeyRequestParam: {}", apiKeyRequestParam);
        log.debug("afterPropertiesSet:    permittedUrls: {}", Arrays.asList(permittedUrls));
        log.debug("afterPropertiesSet:        loginPage: {}", loginPage);
        log.debug("afterPropertiesSet:         loginUrl: {}", loginUrl);
        log.debug("afterPropertiesSet:  loginSuccessUrl: {}", loginSuccessUrl);
        log.debug("afterPropertiesSet:     loginFailUrl: {}", loginFailureUrl);
        log.debug("afterPropertiesSet:        logoutUrl: {}", logoutUrl);
        log.debug("afterPropertiesSet: logoutSuccessUrl: {}", logoutSuccessUrl);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if (this.userFormAuthEnabled && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            auth.inMemoryAuthentication()
                    .withUser(username).password(passwordEncoder().encode(password)).authorities(ROLE_USER_FORM);
            log.info("WebSecurityConfig: User Form Admin credentials have been set: username={}", username);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Spring Security should completely ignore the following URLs
                .antMatchers("/favicon.ico", "/health");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // Check configuration settings
        checkSettings();

        // Check if authentication is disabled
        log.debug("WebSecurityConfig: security-enabled={}, user-form-auth-enabled={}, jwt-token-auth-enabled={}, api-key-auth-enabled={}",
                securityEnabled, userFormAuthEnabled, jwtTokenAuthEnabled, apiKeyAuthEnabled);
        if (!securityEnabled || !userFormAuthEnabled && !jwtTokenAuthEnabled && !apiKeyAuthEnabled) {
            log.warn("WebSecurityConfig: Authentication is disabled");
            // Authorize all requests
            httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                    .anyRequest().permitAll();
            return;
        }

        // Add and Configure User Form authentication
        if (userFormAuthEnabled) {
            log.info("WebSecurityConfig: User form Authentication is enabled");
            httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                        //.antMatchers("//broker/credentials").hasAnyAuthority(ROLE_JWT_TOKEN, ROLE_API_KEY)
                        //.antMatchers("/baguette/ref/**").hasAnyAuthority(ROLE_JWT_TOKEN, ROLE_API_KEY)
                        .antMatchers(permittedUrls).permitAll()
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()
                        .loginPage(loginPage).permitAll()
                        .loginProcessingUrl(loginUrl).permitAll()
                        //.usernameParameter("username")
                        //.passwordParameter("password")
                        .defaultSuccessUrl(loginSuccessUrl, false)
                        .failureUrl(loginFailureUrl).permitAll()
                        .and()
                    .logout()
                        .logoutUrl(logoutUrl).permitAll()
                        .logoutSuccessUrl(logoutSuccessUrl).permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");
            log.debug("WebSecurityConfig: User form Authentication has been configured");
        } else {
            httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                    .anyRequest().authenticated();
        }

        // Add configured authentication filters
        if (apiKeyAuthEnabled) {
            log.info("WebSecurityConfig: API-Key Authentication is enabled");
            httpSecurity
                    .addFilterAfter(apiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .anyRequest().authenticated();
            log.debug("WebSecurityConfig: API-Key Authentication filter added");
        }
        if (jwtTokenAuthEnabled) {
            log.info("WebSecurityConfig: JWT-Token Authentication is enabled");
            httpSecurity
                    .addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .anyRequest().authenticated();
            log.debug("WebSecurityConfig: JWT-Token Authentication filter added");
        }
    }

    private void checkSettings() {
        // Check User Form authentication settings
        boolean userFormAuthEnabled = this.userFormAuthEnabled && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
        if (this.userFormAuthEnabled && !userFormAuthEnabled) {
            if (StringUtils.isBlank(username))
                log.warn("WebSecurityConfig: User Form authentication is enabled but -no- Username has been provided. It will not be possible to login from User form");
            if (StringUtils.isBlank(password))
                log.warn("WebSecurityConfig: User Form authentication is enabled but -no- Password has been provided. It will not be possible to login from User form");
        }

        // Check JWT Token authentication settings
        // Nothing to do

        // Check API Key authentication settings
        boolean apiKeyAuthEnabled = this.apiKeyAuthEnabled && StringUtils.isNotBlank(apiKeyValue)
                && (StringUtils.isNotBlank(apiKeyRequestHeader) || StringUtils.isNotBlank(apiKeyRequestParam));
        if (this.apiKeyAuthEnabled && !apiKeyAuthEnabled) {
            if (StringUtils.isBlank(apiKeyValue))
                log.warn("WebSecurityConfig: API Key authentication is enabled but -no- API Key has been provided. It will not be possible to authenticate using API Key");
            else
                log.warn("WebSecurityConfig: API Key authentication is enabled but -no- API Key request header or parameter have been set. It will not be possible to authenticate using API Key");
        }
    }

    public JWTService jwtService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }

    public Filter jwtAuthorizationFilter() {
        return (servletRequest, servletResponse, filterChain) -> {
            if (servletRequest instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) servletRequest;

                String header = req.getHeader(SecurityConstants.HEADER_STRING);
                log.debug("jwtAuthorizationFilter: Authorization Header: {}", header);
                if (header!=null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                    try {
                        log.debug("jwtAuthorizationFilter: Parsing Authorization header...");
                        Claims claims = jwtService(melodicSecurityProperties).parse(header);
                        String user = claims.getSubject();
                        String audience  = claims.getAudience();
                        log.debug("jwtAuthorizationFilter: Authorization header -->     user: {}", user);
                        log.debug("jwtAuthorizationFilter: Authorization header --> audience: {}", audience);
                        if (user!=null && audience!=null) {
                            if (SecurityConstants.AUDIENCE_UPPERWARE.equals(audience)) {
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
                    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
                        log.debug("jwtAuthorizationFilter: JWT token is not valid: EXCEPTION: ", ex);
                    }
                } else {
                    log.debug("jwtAuthorizationFilter: No or invalid Authorization header");
                }
            } else {
                log.warn("jwtAuthorizationFilter: Not an HttpServletRequest");
            }

            // continue down the chain
            filterChain.doFilter(servletRequest, servletResponse);
        };
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
                    if (StringUtils.isNotBlank(apiKey)) {
                        log.debug("apiKeyAuthenticationFilter: API Key found");

                        if (apiKeyValue.equals(apiKey)) {
                            log.debug("apiKeyAuthenticationFilter: API Key is correct");
                            try {
                                // construct one of Spring's auth tokens
                                UsernamePasswordAuthenticationToken authentication =
                                        new UsernamePasswordAuthenticationToken(apiKeyRequestHeader, apiKeyValue,
                                                Collections.singletonList(new SimpleGrantedAuthority(ROLE_API_KEY)));
                                // store completed authentication in security context
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                log.debug("apiKeyAuthenticationFilter: Security context has been updated");
                            } catch (Exception e) {
                                log.error("apiKeyAuthenticationFilter: EXCEPTION: ", e);
                            }
                        } else {
                            log.debug("apiKeyAuthenticationFilter: API Key is incorrect");
                        }
                    } else {
                        log.debug("apiKeyAuthenticationFilter: No API Key found in request header or parameters");
                    }
                } else {
                    throw new IllegalArgumentException("API Key Authentication filter does not support non-HTTP requests and responses. Req-class: "
                            +servletRequest.getClass().getName()+"  Resp-class: "+servletResponse.getClass().getName());
                }
            } else {
                log.warn("apiKeyAuthenticationFilter: No API-Key specified. Access is granted");
            }

            // continue down the chain
            filterChain.doFilter(servletRequest, servletResponse);
        };
    }
}