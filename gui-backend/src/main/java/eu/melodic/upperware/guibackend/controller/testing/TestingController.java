package eu.melodic.upperware.guibackend.controller.testing;

import eu.melodic.upperware.guibackend.communication.testingtool.FunctionizerTestingToolApi;
import eu.melodic.upperware.guibackend.controller.testing.response.TestConfigurationResponse;
import eu.melodic.upperware.guibackend.service.testing.TestingService;
import eu.passage.upperware.commons.model.testing.FunctionizerTestResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth/test")
@Slf4j
@AllArgsConstructor
public class TestingController {

    TestingService testingService;
    FunctionizerTestingToolApi functionizerTestApi;

    @PostMapping(value = "/config", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public TestConfigurationResponse uploadTestConfigFile(@RequestParam("file") MultipartFile file) {
        String originalName = file.getResource().getFilename();
        log.info(
            "POST request for upload test configuration file with name: {}",
            originalName
        );
        return testingService.uploadTestConfig(file);
    }

    @GetMapping(value = "/config")
    @ResponseStatus(HttpStatus.OK)
    public TestConfigurationResponse getTestConfigResponse() {
        log.info("GET request for the test configuration");
        return testingService.getTestConfiguration();
    }

    @DeleteMapping(value = "/config")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTestConfigFile() {
        log.info("DELETE request for deleting the test configuration file");
        testingService.removeTestConfigFile();
    }

    @PostMapping(value = "/run")
    @ResponseStatus(HttpStatus.OK)
    public FunctionizerTestResult runTests(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        log.info("POST request for running the tests");
        return functionizerTestApi.runTests(token);
    }
}
