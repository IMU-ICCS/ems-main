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


package eu.passage.upperware.security.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Configuration
@ComponentScan(basePackages = {"eu.passage.upperware.security.server", "eu.passage.upperware.security.authapi.properties"})
public class JwtServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(JwtServerApplication.class, args);
  }
}