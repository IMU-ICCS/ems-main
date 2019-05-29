/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

/*
Remark: Need to run this springboot application with the following parameter:
--spring.config.location=classpath:/config/eu.melodic.upperware.metasolver.properties

This provides application with the properties (in that way can be provided externally)

*/

package eu.melodic.upperware.metasolver;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@SpringBootApplication
@EnableAsync
@Configuration
@EnableConfigurationProperties(MelodicSecurityProperties.class)
public class MetaSolverApplication {

    public static void main(String[] args) {
        log.info("MetaSolverApplication: !! 2019-05-29 !!");
        SpringApplication.run(MetaSolverApplication.class, args);
        //UtilCpModelImport.main(args);
    }
}