package eu.paasage.upperware.profiler.generator;

import eu.paasage.camel.CamelFactory;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasageFactory;
import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.orchestrator.GenerationOrchestrator;
import eu.paasage.upperware.profiler.generator.orchestrator.RequestSynchronizer;
import eu.paasage.upperware.profiler.generator.service.camel.IdGenerator;
import eu.paasage.upperware.profiler.generator.service.camel.NewConstraintProblemService;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationService;
import eu.paasage.upperware.profiler.generator.service.camel.SloService;
import eu.paasage.upperware.profiler.generator.service.camel.impl.IdGeneratorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneratorContext {

    @Autowired
    private ApplicationContext applicationContext;

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
    public CamelFactory camelFactory() {
        return CamelFactory.eINSTANCE;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

   /* @Bean
    @Scope("prototype")
    public CCDODatabaseProxy cDODatabaseProxy() {
        CDOClientExtended cDOClientExtended = applicationContext.getBean(CDOClientExtended.class);
        return new CCDODatabaseProxy(cDOClientExtended);
    }*/

    @Bean
    @Scope("prototype")
    protected GenerationOrchestrator generationOrchestrator() throws Exception {

        IDatabaseProxy database = applicationContext.getBean(IDatabaseProxy.class);
        PaasageConfigurationService paaSageConfigurationService = applicationContext.getBean(PaasageConfigurationService.class);
        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        SloService sloService = applicationContext.getBean(SloService.class);
        RequestSynchronizer requestSynchronizer = applicationContext.getBean(RequestSynchronizer.class);

        CdoService cdoService = applicationContext.getBean(CdoService.class);
        NewConstraintProblemService newConstraintProblemService = applicationContext.getBean(NewConstraintProblemService.class);

        return new GenerationOrchestrator(database, paaSageConfigurationService,
                notificationService, sloService, requestSynchronizer, cdoService, newConstraintProblemService);
    }

}
