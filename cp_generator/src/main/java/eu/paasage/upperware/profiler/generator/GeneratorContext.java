package eu.paasage.upperware.profiler.generator;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import eu.melodic.cache.properties.CacheProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.paasage.upperware.profiler.generator.communication.ProactiveClientServiceForGenerator;
import eu.paasage.upperware.profiler.generator.communication.impl.ProactiveClientServiceForGeneratorImpl;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.orchestrator.GenerationOrchestrator;
import eu.paasage.upperware.profiler.generator.orchestrator.RequestSynchronizer;
import eu.paasage.upperware.profiler.generator.properties.GeneratorProperties;
import eu.paasage.upperware.profiler.generator.service.camel.IdGenerator;
import eu.paasage.upperware.profiler.generator.service.camel.NewConstraintProblemServiceX;
import eu.paasage.upperware.profiler.generator.service.camel.impl.IdGeneratorImpl;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
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
@AllArgsConstructor
public class GeneratorContext {

    private ApplicationContext applicationContext;

    private static final String CONSTRAINT_PREFIX = "c_";
    private static final String AUX_EXPRESSION_PREFIX = "aux_expression_";
    private static final String CONSTANT_PREFIX = "constant_";

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
    public TypesFactory typesFactory() {
        return TypesFactory.eINSTANCE;
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Scope("prototype")
    protected GenerationOrchestrator generationOrchestrator() {
        //TODO - repleace this with spring initialization ??
        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        RequestSynchronizer requestSynchronizer = applicationContext.getBean(RequestSynchronizer.class);

        CdoService cdoService = applicationContext.getBean(CdoService.class);
        NewConstraintProblemServiceX newConstraintProblemServiceX = applicationContext.getBean(NewConstraintProblemServiceX.class);

        return new GenerationOrchestrator(notificationService, requestSynchronizer, cdoService, newConstraintProblemServiceX);
    }

    @Bean
    public MemcachedClient memcachedClient(CacheProperties cacheProperties) throws IOException {
        String host = cacheProperties.getCache().getHost();
        Integer port = cacheProperties.getCache().getPort();
        return new MemcachedClient(new BinaryConnectionFactory(), Collections.singletonList(new InetSocketAddress(host, port)));
    }

    @Bean
    public CDOClientX cdoClientX() {
        return new CDOClientXImpl(Collections.emptyList());
    }

    @Bean
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }

    @Bean
    public ProactiveClientServiceForGenerator proactiveClientServiceForGenerator(ProactiveClientConnectorService proactiveClientConnectorService) {
        return new ProactiveClientServiceForGeneratorImpl(proactiveClientConnectorService);
    }
}
