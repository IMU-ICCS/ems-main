package eu.passage.upperware.commons.service.provider;

import eu.passage.upperware.commons.exception.CloudDefinitionNotFoundException;
import eu.passage.upperware.commons.model.provider.CloudDefinition;
import eu.passage.upperware.commons.model.provider.Provider;
import eu.passage.upperware.commons.model.provider.ProviderEnums;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import org.activeeon.morphemic.model.CloudType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderService {

    private ProviderIdCreatorService providerIdCreatorService;
    private ProviderValidationService providerValidationService;
    private YamlDataService yamlDataService;
    private SecureStoreDBService secureStoreDBService;

    // todo get from DB
    public List<CloudDefinition> getCloudDefinitionsForAllProviders() {
        return yamlDataService.getDataFromYaml().getCloudDefinitions()
                .stream()
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
        List<String> secureVariables2 = secureStoreDBService.findSecureVariables(cloudDefinition.getCredential().getSecret());
        log.info("LSZ DEV[ProviderService]: fillSecureVariableInCredentials: secureVariables2={}", secureVariables2);
        if (secureVariables2.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid format of secret placeholder: %s", cloudDefinition.getCredential().getSecret()));
        } else if (secureVariables2.size() == 1) {
            cloudDefinition.getCredential().setSecret(secureStoreDBService.getSecureVariable(secureVariables2.get(0)));
        }
        return cloudDefinition;
    }

    // todo save in DB
    public CloudDefinition createCloudDefinition(CloudDefinition cloudDefinition) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();

        providerValidationService.validateNodeGroup(cloudDefinition.getCloudConfiguration().getNodeGroup());
        providerValidationService.validateProviderUser(cloudDefinition, cloudDefinitionsForAllProviders);
        providerValidationService.validateUniquenessOfPropertyName(cloudDefinition);
        providerValidationService.validateUniquenessOfKeysInSingleProperties(cloudDefinition);

        // add id for each objects, todo remove for using DB
        providerIdCreatorService.addIdForCloudDefinitionElements(cloudDefinition, cloudDefinitionsForAllProviders);

        saveSecretInSecureStore(cloudDefinition);

        cloudDefinitionsForAllProviders.add(cloudDefinition);

        updateCloudDefinitionsInYamlFile(cloudDefinitionsForAllProviders);
        return cloudDefinition;
    }

    private void saveSecretInSecureStore(CloudDefinition cloudDefinition) {
        Pair<String, String> keyLabelForSecret2 = secureStoreDBService.createKeyLabelForSecret(cloudDefinition);
        log.info("Saving secret in secure store for user {} under key: {}", cloudDefinition.getCredential().getUser(), keyLabelForSecret2.getKey());
        log.info("LSZ DEV[ProviderService]: storing securely in db; keyLabelForSecret2={}", keyLabelForSecret2);
        secureStoreDBService.storeSecureVariable(keyLabelForSecret2.getKey(), cloudDefinition.getCredential().getSecret());
        cloudDefinition.getCredential().setSecret(keyLabelForSecret2.getValue());
    }

    // todo update in DB
    public CloudDefinition updateCloudDefinition(int cloudDefId, CloudDefinition cloudDefinitionToUpdate) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();
        CloudDefinition oldCloudDefinition = cloudDefinitionsForAllProviders.stream()
                .filter(cloudDefinition -> cloudDefinition.getId() == cloudDefId)
                .findFirst()
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));

        providerValidationService.validateNodeGroup(cloudDefinitionToUpdate.getCloudConfiguration().getNodeGroup());

        if (!cloudDefinitionToUpdate.getCredential().getUser().equals(oldCloudDefinition.getCredential().getUser())) {
            providerValidationService.validateProviderUser(cloudDefinitionToUpdate, cloudDefinitionsForAllProviders);
        }
        providerValidationService.validateUniquenessOfKeysInSingleProperties(cloudDefinitionToUpdate);
        providerValidationService.validateUniquenessOfPropertyName(cloudDefinitionToUpdate);

        // validate if elements have the same ids and create ids for new elements
        providerValidationService.validateElementsIds(oldCloudDefinition, cloudDefinitionToUpdate);
        providerIdCreatorService.createIdsForNewElements(cloudDefinitionToUpdate, cloudDefinitionsForAllProviders);

        cloudDefinitionsForAllProviders.remove(oldCloudDefinition);

        if (providerUserSecretChanged(oldCloudDefinition, cloudDefinitionToUpdate)) {
            String oldSecureVariableKey2 = secureStoreDBService.createKeyLabelForSecret(oldCloudDefinition).getKey();
            try {
                log.info("Provider user's secret has changed and secure variable with key: {} will be deleted", oldSecureVariableKey2);
                secureStoreDBService.deleteSecureVariable(oldSecureVariableKey2);
                log.info("New provider user's secret will be securely saved with key: {}", oldSecureVariableKey2);
                saveSecretInSecureStore(cloudDefinitionToUpdate);
                log.info("New provider user's secret with key: {} has been successfully saved", oldSecureVariableKey2);
            } catch (NotFoundException ex) {
                log.info("Secure variable with key {} did not exist in secure store.", oldSecureVariableKey2);
            }
        }

        cloudDefinitionsForAllProviders.add(cloudDefinitionToUpdate);

        updateCloudDefinitionsInYamlFile(cloudDefinitionsForAllProviders);

        return cloudDefinitionToUpdate;
    }

    private boolean providerUserSecretChanged(CloudDefinition oldCloudDefinition, CloudDefinition cloudDefinitionToUpdate) {
        return !oldCloudDefinition.getCredential().getSecret()
                .equals(cloudDefinitionToUpdate.getCredential().getSecret());
    }

    // todo delete from db
    public void deleteCloudDefinition(int cloudDefId) {
        List<CloudDefinition> cloudDefinitionsForAllProviders = getCloudDefinitionsForAllProviders();
        CloudDefinition cloudDefinitionToDelete = cloudDefinitionsForAllProviders.stream()
                .filter(cloudDefinition -> cloudDefinition.getId() == cloudDefId)
                .findFirst()
                .orElseThrow(() -> new CloudDefinitionNotFoundException(cloudDefId));
        cloudDefinitionsForAllProviders.remove(cloudDefinitionToDelete);

        log.info("LSZ DEV[ProviderService]: deleteCloudDefinition(int cloudDefId) - deleting from db; secureStoreDBService.createKeyLabelForSecret(cloudDefinitionToDelete).getKey()={}", secureStoreDBService.createKeyLabelForSecret(cloudDefinitionToDelete).getKey());
        secureStoreDBService.deleteSecureVariable(secureStoreDBService.createKeyLabelForSecret(cloudDefinitionToDelete).getKey());
        updateCloudDefinitionsInYamlFile(cloudDefinitionsForAllProviders);
    }

    private void updateCloudDefinitionsInYamlFile(List<CloudDefinition> cloudDefinitionsForAllProviders) {
        // replace plain text secrets with labels
        cloudDefinitionsForAllProviders = cloudDefinitionsForAllProviders.stream()
                .peek(cloudDefinition -> cloudDefinition.getCredential()
                        .setSecret(secureStoreDBService.createKeyLabelForSecret(cloudDefinition).getValue()))
                .collect(Collectors.toList());

        yamlDataService.updateCloudDefinitionInYamlFile(cloudDefinitionsForAllProviders);
    }

    public ProviderEnums getProviderEnums() {
        List<String> providerNames = new ArrayList<>();
        for (Provider value : Provider.values()) {
            providerNames.add(value.value);
        }
        List<String> cloudTypes = new ArrayList<>();
        for (CloudType value : CloudType.values()) {
            cloudTypes.add(value.toString());
        }
        return ProviderEnums.builder()
                .providerNames(providerNames)
                .cloudTypes(cloudTypes)
                .build();
    }
}
