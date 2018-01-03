package eu.paasage.upperware.profiler.generator.orchestrator;

import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.service.camel.NewConstraintProblemService;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationService;
import eu.paasage.upperware.profiler.generator.service.camel.SloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GenerationOrchestratorFactory extends AbstractFactoryBean<GenerationOrchestrator> {

    @Autowired
    private ApplicationContext applicationContext;

    public GenerationOrchestratorFactory() {
        this.setSingleton(false);
    }

    @Override
    public Class<?> getObjectType() {
        return GenerationOrchestrator.class;
    }

    @Override
    protected GenerationOrchestrator createInstance() throws Exception {

        IDatabaseProxy database = applicationContext.getBean(IDatabaseProxy.class);
        PaasageConfigurationService paaSageConfigurationService = applicationContext.getBean(PaasageConfigurationService.class);
        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        SloService sloService = applicationContext.getBean(SloService.class);
        RequestSynchronizer requestSynchronizer = applicationContext.getBean(RequestSynchronizer.class);

        CdoService cdoService = applicationContext.getBean(CdoService.class);
        NewConstraintProblemService bean = applicationContext.getBean(NewConstraintProblemService.class);

        return new GenerationOrchestrator(database, paaSageConfigurationService,
                notificationService, sloService, requestSynchronizer, cdoService, bean);
    }
}
