package eu.paasage.upperware.profiler.generator;

import eu.melodic.cache.properties.CacheProperties;
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
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

@Slf4j
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

    @Bean
    @ConfigurationProperties
    public CacheProperties cacheProperties(){
        return new CacheProperties();
    }

    @Bean
    public MemcachedClient memcachedClient(CacheProperties cacheProperties) throws IOException {
        String host = cacheProperties.getCache().getHost();
        Integer port = cacheProperties.getCache().getPort();
        return new MemcachedClient(new BinaryConnectionFactory(), Collections.singletonList(new InetSocketAddress(host, port)));
    }

    @Bean
    @ConfigurationProperties
    public MelodicSecurityProperties melodicSecurityProperties(){
        return new MelodicSecurityProperties();
    }

    @Bean
    public JWTService getJWTService(){
        return new JWTServiceImpl(melodicSecurityProperties());
    }

}
