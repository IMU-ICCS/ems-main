package eu.melodic.upperware.guibackend.controller.testing;

import eu.melodic.upperware.guibackend.communication.testingtool.FunctionizerTestingToolApi;
import eu.melodic.upperware.guibackend.controller.testing.response.UploadTestConfigResponse;
import eu.melodic.upperware.guibackend.service.testing.TestingService;
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

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public UploadTestConfigResponse uploadTestConfigFile(@RequestParam("file") MultipartFile file) {
        String originalName = file.getResource().getFilename();
        log.info(
            "POST request for upload test configuration file with name: {}",
            originalName
        );
        String fileName = testingService.uploadTestConfig(file);
        log.info("File {} successfully uploaded.", originalName);
        return testingService.createUploadTestConfigResponse(fileName);
    }

    @PostMapping(value = "/run")
    @ResponseStatus(HttpStatus.OK)
    public Object runTests(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        return functionizerTestApi.runTests(token);
    }
}
