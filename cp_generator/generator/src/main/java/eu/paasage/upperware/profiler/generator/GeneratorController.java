package eu.paasage.upperware.profiler.generator;

import eu.melodic.models.interfaces.cpGenerator.ConstraintProblemRequestImpl;
import eu.paasage.upperware.profiler.generator.orchestrator.GenerationOrchestrator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GeneratorController {

    private GeneratorContext generatorContext;

    @RequestMapping(value = "/constraintProblem", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void generateConstraintProblem(@RequestBody ConstraintProblemRequestImpl request) throws Exception {
//        validator.validate(request);

        String resourceName = request.getApplicationId();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();

      log.info("resourceName: " + resourceName + ", notificationUri: " + notificationUri + ", requestUuid: " + requestUuid);

        GenerationOrchestrator generationOrchestrator = generatorContext.generationOrchestrator();
        generationOrchestrator.generateCPModelAndSendNotification(resourceName, notificationUri, requestUuid);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleException(BadRequestException exception) {
        log.error("Returning error response: invalid request {}", exception.getMessage());
        return exception.getMessage();
    }
}