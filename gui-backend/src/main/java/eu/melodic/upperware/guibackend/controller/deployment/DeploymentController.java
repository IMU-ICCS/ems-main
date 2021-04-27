package eu.melodic.upperware.guibackend.controller.deployment;

import eu.melodic.upperware.guibackend.controller.common.MelodicHeaders;
import eu.passage.upperware.commons.model.SecureVariable;
import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.controller.deployment.response.UploadXmiResponse;
import eu.melodic.upperware.guibackend.service.deployment.DeploymentService;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.store.SecureStoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/deployment")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeploymentController {

    private DeploymentService deploymentService;
    private SecureStoreService secureStoreService;
    private final SecureStoreDBService secureStoreDBService;

    @PostMapping(value = "/xmi")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadXmiResponse uploadXmi(@RequestParam("file") MultipartFile file) {
        log.info("POST request for upload xmi file with name: {}", file.getResource().getFilename());
        String cdoName = deploymentService.uploadXmi(file);
        log.info("File {} successfully uploaded. Finding secure variables in progress.", cdoName);
        return deploymentService.createUploadSingleXmiResponse(file, cdoName);
    }

    @PostMapping(value = "/xmi/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UploadXmiResponse> uploadXmiList(@RequestParam("files") List<MultipartFile> files) {
        final String names = files.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.joining(", ", "[", "]"));
        log.info("POST request for upload xmi files list in number: {}, files names: {}", files.size(), names);
        return deploymentService.uploadXmiList(files);
    }

    @DeleteMapping(value = "/xmi/{xmiName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteXmiModel(@PathVariable("xmiName") String xmiName) {
        log.info("DELETE request for xmi model: {}", xmiName);
        deploymentService.deleteXmiModel(xmiName);
    }

    @GetMapping(value = "/xmi")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllXmiModels() {
        log.info("GET request for all xmi models");
        return deploymentService.getAllXmiModels();
    }

    @PostMapping(value = "/process")
    @ResponseStatus(HttpStatus.CREATED)
    public DeploymentResponse deployApplication(@RequestBody DeploymentRequest deploymentRequest,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @RequestHeader(MelodicHeaders.REFRESH) String refreshToken) {
        log.info("POST request for deployment new process");
        return deploymentService.createDeploymentProcess(deploymentRequest, token, refreshToken);
    }

    @PostMapping(value = "/secure/variable")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> saveSecureVariables(@RequestBody List<SecureVariable> secureVariablesRequest) {
        log.info("POST request for save secure variables");
        secureStoreDBService.validateSecureVariables(secureVariablesRequest);
        return secureStoreDBService.saveSecureVariables(secureVariablesRequest);
    }
}
