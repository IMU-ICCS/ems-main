package eu.paasage.upperware.profiler.generator;

import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasageFactory;
import eu.paasage.upperware.profiler.generator.service.camel.impl.IdGeneratorImpl;
import eu.paasage.upperware.profiler.generator.service.camel.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneratorContext {

    private static final String CONSTRAINT_PREFIX= "c_";
    private static final String AUX_EXPRESSION_PREFIX= "aux_expression_";
    private static final String CONSTANT_PREFIX ="constant_";

    @Bean(name = "constraintIdGenerator")
    public IdGenerator constraintIdGenerator() {
        return new IdGeneratorImpl(CONSTRAINT_PREFIX);
    }

    @Bean(name = "auxExpressionIdGenerator")
    public IdGenerator auxExpressionIdGenerator() {
        return new IdGeneratorImpl(AUX_EXPRESSION_PREFIX);
    }

    @Bean(name = "constantIdGenerator")
    public IdGenerator constantIdGenerator() {
        return new IdGeneratorImpl(CONSTANT_PREFIX);
    }

    @Bean
    public CpFactory cpFactory() {
        return CpFactory.eINSTANCE;
    }

    @Bean
    public ApplicationFactory applicationFactory() {
        return ApplicationFactory.eINSTANCE;
    }

    @Bean
    public TypesFactory typesFactory() {
        return TypesFactory.eINSTANCE;
    }

    @Bean
    public TypesPaasageFactory typesPaasageFactory() {
        return TypesPaasageFactory.eINSTANCE;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
