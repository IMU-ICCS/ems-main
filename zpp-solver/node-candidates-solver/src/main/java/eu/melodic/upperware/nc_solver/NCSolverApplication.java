package eu.melodic.upperware.nc_solver;


import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@ComponentScan(basePackages = {"eu.melodic.upperware.nc_solver", "eu.melodic.cache", "eu.melodic.upperware.utilitygenerator"})
@Configuration
@SpringBootApplication
@EnableConfigurationProperties({UtilityGeneratorProperties.class, MelodicSecurityProperties.class, CacheProperties.class, PenaltyFunctionProperties.class})
public class NCSolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NCSolverApplication.class, args);
    }
}