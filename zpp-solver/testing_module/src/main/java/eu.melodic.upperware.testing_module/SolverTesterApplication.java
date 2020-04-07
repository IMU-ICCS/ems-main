package eu.melodic.upperware.testing_module;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({UtilityGeneratorProperties.class, MelodicSecurityProperties.class, CacheProperties.class, PenaltyFunctionProperties.class})
public class SolverTesterApplication implements CommandLineRunner {
    @Autowired
    private
    SolverTester solverTester;
    public static void main(String[] args) {
        SpringApplication.run(SolverTesterApplication.class, args).close();
    }

    public void run(String... args) throws Exception {
        solverTester.runTests();
    }
}