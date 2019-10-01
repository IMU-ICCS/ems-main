package eu.melodic.upperware.guibackend.service.provider;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.exception.CloudDefinitionNotFoundException;
import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
import eu.melodic.upperware.guibackend.service.secure.store.SecureStoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;

import javax.ws.rs.NotFoundException;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderService {

    private ProviderIdCreatorService providerIdCreatorService;
    private ProviderValidationService providerValidationService;
    private CloudiatorApi cloudiatorApi;
    private SecureStoreService secureStoreService;

    private final static String YAML_CONFIG_FILE_NAME = "gui_providers_data.yaml";
    private final static String SECURE_VARIABLE_PREFIX = "{{";
    private final static String SECURE_VARIABLE_SUFFIX = "}}";


    // todo get from DB
    public List<CloudDefinition> getCloudDefinitionsForAllProviders() {
        Yaml yaml = new Yaml();
        try (FileInputStream fileInputStream = new FileInputStream(new File(System.getenv("MELODIC_CONFIG_DIR") + "/" + YAML_CONFIG_FILE_NAME))) {
            List resultLoadedFromYaml = yaml.load(fileInputStream);
            return mapResultFromYamlToCloudDefinitionList(resultLoadedFromYaml);
        } catch (FileNotFoundException e) {
            log.error("File with providers configuration is missing.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("File with providers configuration: %s is missing.", YAML_CONFIG_FILE_NAME));
        } catch (IOException e) {
            log.error("Problem by reading file with providers configuration");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by reading file: %s with providers configuration", YAML_CONFIG_FILE_NAME));
        }
    }

    private List<CloudDefinition> mapResultFromYamlToCloudDefinitionList(List resultLoadedFromYaml) {
        ObjectMapper mapper = new ObjectMapper();
        List<CloudDefinition> result = resultLoadedFromYaml == null ? Collections.emptyList() : mapper.convertValue(resultLoadedFromYaml,
                new TypeReference<List<CloudDefinition>>() {
                });
        return result.stream()
                .map(this::fillSecureVariableInCredentials)
                .collect(Collectors.toList());
    }

    // todo get from DB
    public CloudDefinition getCloudDefinition(int cloudDefId) {
        return getCloudDefinitionsForAllProviders().stream()
                .filter(cloudDefinition -> cloudDefId == cloudDefinition.getId()).findAny()
                .map(this::fillSecureVariableInCredentials)
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));
    }

    public CloudDefinition fillSecureVariableInCredentials(CloudDefinition cloudDefinition) {
        log.info("Checking secure variables for secret key: {}", cloudDefinition.getCredential().getSecret());
        List<String> secureVariables = secureStoreService.findSecureVariables(cloudDefinition.getCredential().getSecret());
        if (secureVariables.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid format of secret placeholder: %s", cloudDefinition.getCredential().getSecret()));
        } else if (secureVariables.size() == 1) {
            cloudDefinition.getCredential().setSecret(cloudiatorApi.getSecureVariable(secureVariables.get(0)));
        }
        return cloudDefinition;
    }

    // todo save in DB
    public CloudDefinition createCloudDefinition(CloudDefinition cloudDefinition) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();

        providerValidationService.validateProviderUser(cloudDefinition, cloudDefinitionsForAllProviders);
        providerValidationService.validateUniquenessOfPropertyName(cloudDefinition);
        providerValidationService.validateUniquenessOfKeysInSingleProperties(cloudDefinition);

        // add id for each objects, todo remove for using DB
        providerIdCreatorService.addIdForCloudDefinitionElements(cloudDefinition, cloudDefinitionsForAllProviders);

        saveSecretInSecureStore(cloudDefinition);

        cloudDefinitionsForAllProviders.add(cloudDefinition);

        updateYamlFile(cloudDefinitionsForAllProviders);
        return cloudDefinition;
    }

    private void saveSecretInSecureStore(CloudDefinition cloudDefinition) {
        Pair<String, String> keyLabelForSecret = createKeyLabelForSecret(cloudDefinition);
        log.info("Saving secret in secure store for user {} under key: {}", cloudDefinition.getCredential().getUser(), keyLabelForSecret.getKey());
        this.cloudiatorApi.storeSecureVariable(keyLabelForSecret.getKey(), cloudDefinition.getCredential().getSecret());
        cloudDefinition.getCredential().setSecret(keyLabelForSecret.getValue());
    }

    private Pair<String, String> createKeyLabelForSecret(CloudDefinition cloudDefinition) {
        String keyForSecret = cloudDefinition.getApi().getProviderName() + "-" + cloudDefinition.getCredential().getUser() + "-SECRET";
        return Pair.of(keyForSecret, SECURE_VARIABLE_PREFIX + keyForSecret + SECURE_VARIABLE_SUFFIX);
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

        if (providerUserChanged(oldCloudDefinition, cloudDefinitionToUpdate)) {
            String oldSecureVariableKey = createKeyLabelForSecret(oldCloudDefinition).getKey();
            try {
                cloudiatorApi.deleteSecureVariable(oldSecureVariableKey);
                log.info("Provider user changed and secure variable from key {} deleted", oldSecureVariableKey);
            } catch (NotFoundException ex) {
                log.info("Secure variable with key {} did not exist in secure store.", oldSecureVariableKey);
            }
        }
        saveSecretInSecureStore(cloudDefinitionToUpdate);

        cloudDefinitionsForAllProviders.add(cloudDefinitionToUpdate);

        updateYamlFile(cloudDefinitionsForAllProviders);

        return cloudDefinitionToUpdate;
    }

    private boolean providerUserChanged(CloudDefinition oldCloudDefinition, CloudDefinition cloudDefinitionToUpdate) {
        return !oldCloudDefinition.getCredential().getUser()
                .equals(cloudDefinitionToUpdate.getCredential().getUser());
    }

    // todo delete from db
    public void deleteCloudDefinition(int cloudDefId) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();
        CloudDefinition cloudDefinitionToDelete = cloudDefinitionsForAllProviders.stream()
                .filter(cloudDefinition -> cloudDefinition.getId() == cloudDefId)
                .findFirst()
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));
        cloudDefinitionsForAllProviders.remove(cloudDefinitionToDelete);

        cloudiatorApi.deleteSecureVariable(createKeyLabelForSecret(cloudDefinitionToDelete).getKey());
        updateYamlFile(cloudDefinitionsForAllProviders);
    }

    private void updateYamlFile(List<CloudDefinition> cloudDefinitionsForAllProviders) {
        log.info("Updating yaml file");

        // replace plain text secrets with labels
        cloudDefinitionsForAllProviders = cloudDefinitionsForAllProviders.stream()
                .peek(cloudDefinition -> cloudDefinition.getCredential()
                        .setSecret(createKeyLabelForSecret(cloudDefinition).getValue()))
                .collect(Collectors.toList());

        Yaml yaml = new Yaml();
        FileWriter writer = null;
        try {
            writer = new FileWriter(System.getenv("MELODIC_CONFIG_DIR") + "/" + YAML_CONFIG_FILE_NAME);
        } catch (IOException e) {
            log.error("Error by writing to yaml file: ", e);
        }
        yaml.dump(cloudDefinitionsForAllProviders, writer);
    }
}
