package eu.functionizer.functionizertestingtool.service.yaml;


import eu.passage.upperware.commons.model.testing.TestConfiguration;
import eu.passage.upperware.commons.MelodicConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.ConstructorException;

import java.io.*;

import static eu.passage.upperware.commons.service.testing.TestConfigurationValidationService.validate;

@Service
@Slf4j
public class TestConfigurationLoader {

    private final static String CONFIG_FILE_PATH = MelodicConstants.TEST_CONFIG_FILE_DIR + "/tests.yml";

    public static TestConfiguration loadTestConfiguration() throws Exception {
        Yaml yaml = new Yaml(new Constructor(TestConfiguration.class));
        TestConfiguration configuration;

        try (FileInputStream fileInputStream = new FileInputStream(new File(CONFIG_FILE_PATH))) {
            configuration = yaml.load(fileInputStream);

        } catch (FileNotFoundException e) {
            String errorMessage = String.format(
                "Test configuration file: %s is missing.",
                CONFIG_FILE_PATH
            );
            log.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } catch (IOException e) {
            String errorMessage = String.format(
                "Problem by reading file with test configuration: %s",
                CONFIG_FILE_PATH
            );
            log.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } catch (ConstructorException e) {
            String errorMessage = String.format(
                "The file has a bad format and could not be parsed: %s", e.getMessage()
            );
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        validate(configuration);
        return configuration;
    }


    private void saveTestConfiguration(TestConfiguration testConfiguration) {
        Yaml yaml = new Yaml();
        FileWriter writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("Error by writing to file {} with test configuration: ", CONFIG_FILE_PATH, e);
        }
        yaml.dump(testConfiguration, writer);
    }
}
