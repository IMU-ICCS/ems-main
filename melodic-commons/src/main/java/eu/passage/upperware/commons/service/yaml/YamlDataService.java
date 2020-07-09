package eu.passage.upperware.commons.service.yaml;

import eu.passage.upperware.commons.model.byon.ByonDefinition;
import eu.passage.upperware.commons.model.provider.CloudDefinition;
import eu.passage.upperware.commons.model.GuiYamlData;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;

@Service
@Slf4j
public class YamlDataService {

    private final static String CONFIG_FILE_PATH = System.getenv("MELODIC_CONFIG_DIR") + "/gui-data.yaml";

    public GuiYamlData getDataFromYaml() {
        Yaml yaml = new Yaml();
        try (FileInputStream fileInputStream = new FileInputStream(new File(CONFIG_FILE_PATH))) {
            Object resultLoadedFromYaml = yaml.load(fileInputStream);
            ObjectMapper mapper = new ObjectMapper();
            return resultLoadedFromYaml == null ? new GuiYamlData() : mapper.convertValue(resultLoadedFromYaml,
                    new TypeReference<GuiYamlData>() {
                    });
        } catch (FileNotFoundException e) {
            String errorMessage = String.format("File with cloud and byon definitions: %s is missing.", CONFIG_FILE_PATH);
            log.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } catch (IOException e) {
            String errorMessage = String.format("Problem by reading file with cloud and byon definitions: %s", CONFIG_FILE_PATH);
            log.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    public void updateCloudDefinitionInYamlFile(List<CloudDefinition> cloudDefinitionsForAllProviders) {
        GuiYamlData guiYamlData = getDataFromYaml();
        guiYamlData.setCloudDefinitions(cloudDefinitionsForAllProviders);
        saveDataInYamlFile(guiYamlData);
    }

    public void updateByonDefinitionInYamlFile(List<ByonDefinition> byonDefinitionsList) {
        GuiYamlData guiYamlData = getDataFromYaml();
        guiYamlData.setByonDefinitions(byonDefinitionsList);
        saveDataInYamlFile(guiYamlData);
    }

    private void saveDataInYamlFile(GuiYamlData guiYamlData) {
        Yaml yaml = new Yaml();
        FileWriter writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("Error by writing to file {} with cloud and byon definitions: ", CONFIG_FILE_PATH, e);
        }
        yaml.dump(guiYamlData, writer);
    }
}
