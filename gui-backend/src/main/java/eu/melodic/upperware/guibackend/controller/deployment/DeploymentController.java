package eu.melodic.upperware.guibackend.controller.deployment;

import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.controller.deployment.response.UploadXmiResponse;
import eu.melodic.upperware.guibackend.service.deployment.DeploymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/deployment")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeploymentController {

    private DeploymentService deploymentService;

    @PostMapping(value = "/xmi")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadXmiResponse uploadXmi(@RequestParam("file") MultipartFile file) {
        log.info("POST request for upload xmi file with name: {}", file.getResource().getFilename());
        return deploymentService.uploadXmi(file);
    }

    @DeleteMapping(value = "/xmi/{xmiName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteXmiModel(@PathVariable("xmiName") String xmiName) {
        log.info("DELETE request for xmi model: {}", xmiName);
        deploymentService.deleteXmiModel(xmiName);
    }

    @PostMapping(value = "/process")
    @ResponseStatus(HttpStatus.CREATED)
    public DeploymentResponse deployApplication(@RequestBody DeploymentRequest deploymentRequest) {
        log.info("POST request for deployment new process");
        return deploymentService.createDeploymentProcess(deploymentRequest);
    }
}
