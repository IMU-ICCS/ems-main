package eu.melodic.upperware.guibackend;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "eu.melodic.upperware.guibackend",
        "eu.passage.upperware.commons.cloudiator"})
@SpringBootApplication
@EnableConfigurationProperties({MelodicSecurityProperties.class})
public class GuiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiBackendApplication.class, args);
    }

}
