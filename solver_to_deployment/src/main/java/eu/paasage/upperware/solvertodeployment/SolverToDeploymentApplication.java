/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

/*
Remark: Need to run this springboot application with the following parameter:
--spring.config.location=classpath:/config/eu.melodic.upperware.solverToDeployment.properties

This provides application with the properties (in that way can be provided externally)

*/


package eu.paasage.upperware.solvertodeployment;

import eu.melodic.cache.properties.CacheProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@ComponentScan(basePackages = {"eu.paasage.upperware.solvertodeployment", "eu.melodic.cache"})
@SpringBootApplication
@EnableConfigurationProperties({MelodicSecurityProperties.class, CacheProperties.class})
public class SolverToDeploymentApplication {

  public static void main(String[] args) {
    SpringApplication.run(SolverToDeploymentApplication.class, args);
  }
}