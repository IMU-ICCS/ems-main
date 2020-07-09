package eu.functionizer.functionizertestingtool;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CloudiatorProperties.class, MelodicSecurityProperties.class})
public class FunctionizerTestingToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunctionizerTestingToolApplication.class, args);
	}

}
