/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.util;

import eu.paasage.upperware.security.authapi.token.JWTService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.ComponentScan;

/**
 * Run:
 * java -cp .\target\control-service.jar  -Dloader.main=eu.melodic.event.control.util.JwtTokenUtil -Dlogging.level.ROOT=WARN -Dlogging.level.eu.melodic.event.control.util.JwtTokenUtil=INFO org.springframework.boot.loader.PropertiesLauncher create [USER]?
 * -or-
 * java -cp .\target\control-service.jar  -Dloader.main=eu.melodic.event.control.util.JwtTokenUtil -Dlogging.level.ROOT=WARN -Dlogging.level.eu.melodic.event.control.util.JwtTokenUtil=INFO org.springframework.boot.loader.PropertiesLauncher parser [TOKEN]
 */
@Slf4j
@ComponentScan(basePackages = { "eu.paasage.upperware.security.authapi" })
@RequiredArgsConstructor
public class JwtTokenUtil implements CommandLineRunner {
    private final JWTService jwtService;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(JwtTokenUtil.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setLogStartupInfo(false);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length>0) {
            String token;
            if ("create".equalsIgnoreCase(args[0].trim())) {
                String user = args.length > 1 && !args[1].trim().isEmpty() ? args[1].trim() : "USER";
                token = jwtService.create(user);
                log.info("New JWT token for user '{}':\n{}", user, token);
            } else if ("parse".equalsIgnoreCase(args[0].trim())) {
                token = args[1];
                Claims claims = jwtService.parse(token);
                log.info("Token claims: {}", claims);
            } else {
                log.warn("Unknown command: {}", args[0]);
            }
        } else {
            log.warn("No command specified");
        }
    }
}
