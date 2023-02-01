/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.util.jwt;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.security.properties")
public class JwtTokenProperties implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        log.debug("JwtTokenProperties: {}", this);
    }

    @ToString.Exclude
    private String secret;
    private Long expirationTime = Duration.ofDays(1).toMillis();
    private Long refreshTokenExpirationTime = Duration.ofDays(1).toMillis();
}