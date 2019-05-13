package eu.melodic.upperware.guibackend.service.provider;

import eu.melodic.upperware.guibackend.exception.CloudDefinitionNotFoundException;
import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderService {

    private ProviderIdCreatorService providerIdCreatorService;
    private ProviderValidationService providerValidationService;
    private final static String YAML_CONFIG_FILE_NAME = "gui_providers_data.yaml";

    // todo get from DB
    public List<CloudDefinition> getCloudDefinitionsForAllProviders() {
        Yaml yaml = new Yaml();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(System.getenv("MELODIC_CONFIG_DIR") + "/" + YAML_CONFIG_FILE_NAME));
        } catch (FileNotFoundException e) {
            log.error("File with providers configuration is missing.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("File with providers configuration: %s is missing.", YAML_CONFIG_FILE_NAME));
        }
        Object resultLoadedFromYaml = yaml.load(fileInputStream);
        return mapResultFromYamlToCloudDefinitionList(resultLoadedFromYaml);

    }

    private List<CloudDefinition> mapResultFromYamlToCloudDefinitionList(Object resultLoadedFromYaml) {
        ObjectMapper mapper = new ObjectMapper();
        return resultLoadedFromYaml == null ? Collections.emptyList() : mapper.convertValue(resultLoadedFromYaml,
                new TypeReference<List<CloudDefinition>>() {
                });
    }

    // todo get from DB
    public CloudDefinition getCloudDefinition(int cloudDefId) {
        return getCloudDefinitionsForAllProviders().stream()
                .filter(cloudDefinition -> cloudDefId == cloudDefinition.getId()).findAny()
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));
    }

    // todo save in DB
    public CloudDefinition createCloudDefinition(CloudDefinition cloudDefinition) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();

        providerValidationService.validateProviderUser(cloudDefinition, cloudDefinitionsForAllProviders);
        providerValidationService.validateUniquenessOfPropertyName(cloudDefinition);
        providerValidationService.validateUniquenessOfKeysInSingleProperties(cloudDefinition);

        // add id for each objects, todo remove for using DB
        providerIdCreatorService.addIdForCloudDefinitionElements(cloudDefinition, cloudDefinitionsForAllProviders);

        cloudDefinitionsForAllProviders.add(cloudDefinition);

        updateYamlFile(cloudDefinitionsForAllProviders);
        return cloudDefinition;
    }

    // todo update in DB
    public CloudDefinition updateCloudDefinition(int cloudDefId, CloudDefinition cloudDefinitionToUpdate) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();
        CloudDefinition oldCloudDefinition = cloudDefinitionsForAllProviders.stream()
                .filter(cloudDefinition -> cloudDefinition.getId() == cloudDefId)
                .findFirst()
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));

        if (!cloudDefinitionToUpdate.getCredential().getUser().equals(oldCloudDefinition.getCredential().getUser())) {
            providerValidationService.validateProviderUser(cloudDefinitionToUpdate, cloudDefinitionsForAllProviders);
        }
        providerValidationService.validateUniquenessOfKeysInSingleProperties(cloudDefinitionToUpdate);
        providerValidationService.validateUniquenessOfPropertyName(cloudDefinitionToUpdate);

        // validate if elements have the same ids and create ids for new elements
        providerValidationService.validateElementsIds(oldCloudDefinition, cloudDefinitionToUpdate);
        providerIdCreatorService.createIdsForNewElements(cloudDefinitionToUpdate, cloudDefinitionsForAllProviders);

        cloudDefinitionsForAllProviders.remove(oldCloudDefinition);
        cloudDefinitionsForAllProviders.add(cloudDefinitionToUpdate);

        updateYamlFile(cloudDefinitionsForAllProviders);

        return cloudDefinitionToUpdate;
    }

    // todo delete from db
    public void deleteCloudDefinition(int cloudDefId) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();
        CloudDefinition cloudDefinitionToDelete = cloudDefinitionsForAllProviders.stream()
                .filter(cloudDefinition -> cloudDefinition.getId() == cloudDefId)
                .findFirst()
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));
        cloudDefinitionsForAllProviders.remove(cloudDefinitionToDelete);
        updateYamlFile(cloudDefinitionsForAllProviders);
    }

    private void updateYamlFile(List<CloudDefinition> cloudDefinitionsForAllProviders) {
        Yaml yaml = new Yaml();
        FileWriter writer = null;
        try {
            writer = new FileWriter(System.getenv("MELODIC_CONFIG_DIR") + "/" + YAML_CONFIG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        yaml.dump(cloudDefinitionsForAllProviders, writer);
    }
}
