package eu.paasage.upperware.profiler.generator;

import eu.melodic.models.interfaces.cpGenerator.ConstraintProblemRequestImpl;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.paasage.upperware.profiler.generator.communication.impl.CdoServiceImpl;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.notification.NotificationService;
import eu.paasage.upperware.profiler.generator.orchestrator.GenerationOrchestrator;
import eu.paasage.upperware.profiler.generator.orchestrator.RequestSynchronizer;
import eu.paasage.upperware.profiler.generator.service.camel.NewConstraintProblemService;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationService;
import eu.paasage.upperware.profiler.generator.service.camel.SloService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GeneratorController {

//    private GeneratorContext generatorContext;

//    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/constraintProblem", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void generateConstraintProblem(@RequestBody ConstraintProblemRequestImpl request) throws Exception {
//        validator.validate(request);

        String resourceName = request.getApplicationId();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();

      log.info("resourceName: " + resourceName + ", notificationUri: " + notificationUri + ", requestUuid: " + requestUuid);

        IDatabaseProxy database = applicationContext.getBean(IDatabaseProxy.class);
        PaasageConfigurationService paaSageConfigurationService = applicationContext.getBean(PaasageConfigurationService.class);
        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        SloService sloService = applicationContext.getBean(SloService.class);
        RequestSynchronizer requestSynchronizer = applicationContext.getBean(RequestSynchronizer.class);

//        CdoService cdoService = applicationContext.getBean(CdoService.class);

        CDOClient cdoClient = applicationContext.getBean(CDOClient.class);
        log.info("Getting cdoClient {}", cdoClient);

        CdoService cdoService = new CdoServiceImpl(cdoClient);

        NewConstraintProblemService newConstraintProblemService = applicationContext.getBean(NewConstraintProblemService.class);

        GenerationOrchestrator generationOrchestrator = new GenerationOrchestrator(database, paaSageConfigurationService,
                notificationService, sloService, requestSynchronizer, cdoService, newConstraintProblemService);

//        GenerationOrchestrator generationOrchestrator = generatorContext.generationOrchestrator();
        generationOrchestrator.generateCPModelAndSendNotification(resourceName, notificationUri, requestUuid);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleException(BadRequestException exception) {
        log.error("Returning error response: invalid request {}", exception.getMessage());
        return exception.getMessage();
    }
}