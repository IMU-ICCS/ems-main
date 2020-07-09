package eu.melodic.upperware.guibackend.service.testing;

import eu.melodic.upperware.guibackend.controller.testing.response.UploadTestConfigResponse;
import eu.passage.upperware.commons.MelodicConstants;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import eu.passage.upperware.commons.model.testing.TestConfiguration;
import eu.passage.upperware.commons.service.testing.TestConfigurationValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.net4j.connector.ConnectorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.ConstructorException;

import java.io.*;

import static eu.passage.upperware.commons.service.testing.TestConfigurationValidationService.checkFunctionNamesUniqueness;
import static eu.passage.upperware.commons.service.testing.TestConfigurationValidationService.checkTestCasesUniqueness;


@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestingService {
    private final static String CONFIG_FILE_PATH = MelodicConstants.TEST_CONFIG_FILE_DIR + "/tests.yml";

    public String uploadTestConfig(MultipartFile uploadFileRequest) {
        try {
            if (uploadFileRequest.getOriginalFilename() == null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Problem by uploading your %s file. Please try again.",
                        uploadFileRequest.getResource().getFilename()
                    ));
            }
            File newFile = new File(CONFIG_FILE_PATH);
            if (!newFile.exists()) {
                newFile.getParentFile().mkdirs();
            }
            validate(uploadFileRequest.getInputStream());

            uploadFileRequest.transferTo(newFile);
            log.info(
                "File {} will be stored under path: {}",
                uploadFileRequest.getResource().getFilename(),
                CONFIG_FILE_PATH
            );
            return CONFIG_FILE_PATH;

        } catch (IOException | IllegalStateException e) {
            log.error("Error by uploading test configuration file:", e);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Problem by uploading your %s file. Please try again.",
                    uploadFileRequest.getResource().getFilename()
                ));
        } catch (ConnectorException e) {
            log.error("Error by uploading test configuration file:", e);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Problem by uploading your %s file. CDO repository not working. Please try again.",
                    uploadFileRequest.getResource().getFilename()
                ));
        }
    }

    public UploadTestConfigResponse createUploadTestConfigResponse(String fileName) {
        return new  UploadTestConfigResponse(fileName);
    }

    private void validate(InputStream ymlFileInputStream) throws ResponseStatusException {
        Yaml yaml = new Yaml(new Constructor(TestConfiguration.class));
        TestConfiguration configuration;
        try {
            configuration = yaml.load(ymlFileInputStream);
            log.info("Checking uniqueness of function names");
            checkFunctionNamesUniqueness(configuration.getTests());

            log.info("Checking uniqueness of test cases");
            for (FunctionTestConfiguration functionTestConfiguration : configuration.getTests()) {
                checkTestCasesUniqueness(
                    functionTestConfiguration.getTestCases(),
                    functionTestConfiguration.getFunctionName()
                );
            }
        } catch (ConstructorException e) {
            String errorMessage = "The file has a bad format and could not be parsed.";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } catch (
            TestConfigurationValidationService.NotUniqueFunctionNameException
                | TestConfigurationValidationService.NotUniqueTestCaseException e
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
