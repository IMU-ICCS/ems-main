package eu.melodic.upperware.guibackend.controller.deployment;

import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.service.deployment.DeploymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deployment")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeploymentController {

    private DeploymentService deploymentService;

    @PostMapping(value = "/process")
    @ResponseStatus(HttpStatus.CREATED)
    public DeploymentResponse deployApplication(@RequestBody DeploymentRequest deploymentRequest) {
        log.info("POST request for deployment new process");
        return deploymentService.createDeploymentProcess(deploymentRequest);
    }
}
