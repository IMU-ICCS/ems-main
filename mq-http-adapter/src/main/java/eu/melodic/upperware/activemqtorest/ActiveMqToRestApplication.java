package eu.melodic.upperware.activemqtorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * 2019 CAS Software AG
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages = {"eu.melodic.upperware.activemqtorest", "eu.passage.upperware.commons.cloudiator"})
public class ActiveMqToRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveMqToRestApplication.class, args);
	}

}
