package spring;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;

@EnableAsync
@Configuration
@SpringBootApplication
@EnableConfigurationProperties({UtilityGeneratorProperties.class, MelodicSecurityProperties.class, CacheProperties.class, PenaltyFunctionProperties.class})
public class GeneticApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneticApplication.class, args);
    }
}