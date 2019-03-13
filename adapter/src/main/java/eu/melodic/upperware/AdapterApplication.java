/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan(basePackages = {
        "eu.melodic.upperware.adapter",
        "eu.melodic.security.authorization.util.properties",
        "eu.melodic.cache"})
@SpringBootApplication
@EnableConfigurationProperties({MelodicSecurityProperties.class, AuthorizationServiceClientProperties.class, CacheProperties.class})
public class AdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdapterApplication.class, args);
    }
}