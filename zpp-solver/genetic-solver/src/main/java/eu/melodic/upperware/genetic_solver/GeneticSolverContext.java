package eu.melodic.upperware.genetic_solver;

import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class GeneticSolverContext {
    ApplicationContext applicationContext;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }

    @Bean
    public CDOClientX cDOClientX() {
        return new CDOClientXImpl(Arrays.asList(TypesPackage.eINSTANCE, CpPackage.eINSTANCE));
    }
}
