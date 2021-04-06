package eu.melodic.upperware.guibackend.service.testing;

import eu.melodic.upperware.guibackend.controller.testing.response.TestConfigurationResponse;
import eu.passage.upperware.commons.MelodicConstants;
import eu.passage.upperware.commons.model.testing.FunctionTestConfiguration;
import eu.passage.upperware.commons.model.testing.TestConfiguration;
import eu.passage.upperware.commons.service.testing.TestConfigurationValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.ConstructorException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

import static eu.passage.upperware.commons.service.testing.TestConfigurationValidationService.checkFunctionNamesUniqueness;
import static eu.passage.upperware.commons.service.testing.TestConfigurationValidationService.checkTestCasesUniqueness;


@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestingService {
    private final static String CONFIG_FILE_PATH = MelodicConstants.TEST_CONFIG_FILE_DIR + "/tests.yml";

    public TestConfigurationResponse uploadTestConfig(MultipartFile uploadFileRequest) {
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
            TestConfiguration configuration = loadAndValidateTestConfiguration(
                uploadFileRequest.getInputStream()
            );

            uploadFileRequest.transferTo(newFile);
            log.info(
                "File {} successfully stored under path: {}",
                uploadFileRequest.getResource().getFilename(),
                CONFIG_FILE_PATH
            );
            return createTestConfigResponse(configuration);

        } catch (IOException | IllegalStateException e) {
            log.error("Error by uploading test configuration file:", e);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Problem by uploading your %s file. Please try again.",
                    uploadFileRequest.getResource().getFilename()
                ));
        }
    }

    public TestConfigurationResponse getTestConfiguration() {
        File configFile = new File(CONFIG_FILE_PATH);
        InputStream ymlFileInputStream;
        try {
            ymlFileInputStream = new FileInputStream(configFile);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Test configuration file not found on server."
            );
        }
        TestConfiguration testConfiguration = loadTestConfiguration(ymlFileInputStream);
        return createTestConfigResponse(testConfiguration);
    }

    public void removeTestConfigFile() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            log.info("No file to delete. Ending");
            return;
        }
        if (configFile.delete()) {
            log.info("Successfully removed file {}", configFile.getName());
        } else {
            log.error("Error while deleting test configuration file.");
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Could not remove file %s.", configFile.getName())
            );
        }
    }

    private TestConfigurationResponse createTestConfigResponse(TestConfiguration configuration) {
        return new TestConfigurationResponse(CONFIG_FILE_PATH, configuration);
    }

    private TestConfiguration loadTestConfiguration(InputStream ymlFileInputStream) {
        Yaml yaml = new Yaml(new Constructor(TestConfiguration.class));
        try {
            return yaml.load(ymlFileInputStream);
        } catch (ConstructorException e) {
            String errorMessage = "The file has a bad structure and could not be parsed.";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    private TestConfiguration loadAndValidateTestConfiguration(
        InputStream ymlFileInputStream
    ) throws ResponseStatusException {
        TestConfiguration configuration;
        try {
            configuration = loadTestConfiguration(ymlFileInputStream);
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
            String errorMessage = "The file has a bad structure and could not be parsed.";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } catch (
            TestConfigurationValidationService.NotUniqueFunctionNameException
                | TestConfigurationValidationService.NotUniqueTestCaseException e
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return configuration;
    }
}
