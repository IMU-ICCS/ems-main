package eu.melodic.upperware.activemqtorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({CloudiatorProperties.class})
@ComponentScan(basePackages = {"eu.melodic.upperware.activemqtorest", "eu.passage.upperware.commons.cloudiator"})
public class ActiveMqToRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveMqToRestApplication.class, args);
	}

}
