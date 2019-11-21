package eu.melodic.upperware.guibackend.service.byon;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.exception.ByonDefinitionNotFoundException;
import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import eu.melodic.upperware.guibackend.model.byon.ByonEnums;
import eu.melodic.upperware.guibackend.model.byon.LoginCredential;
import eu.melodic.upperware.guibackend.service.secure.store.SecureStoreService;
import eu.melodic.upperware.guibackend.service.yaml.YamlDataService;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ByonService {

    private YamlDataService yamlDataService;
    private ByonIdCreatorService byonIdCreatorService;
    private CloudiatorApi cloudiatorApi;
    private ByonMapper byonMapper;
    private SecureStoreService secureStoreService;

    public Optional<List<ByonDefinition>> getByonDefinitionsListWithFilledCredentials() {
        List<ByonDefinition> byonDefinitions = yamlDataService.getDataFromYaml().getByonDefinitions();
        if (byonDefinitions != null) {
            fillLoginCredentialsInByonDefinition(byonDefinitions);
        }
        return Optional.ofNullable(byonDefinitions);
    }

    private void fillLoginCredentialsInByonDefinition(List<ByonDefinition> byonDefinitions) {
        byonDefinitions.forEach(byonDefinition -> {
            if (fieldIsDefined(byonDefinition.getLoginCredential().getPassword())) {
                String password = secureStoreService.getValueForSecureVariableLabel(byonDefinition.getLoginCredential().getPassword());
                byonDefinition.getLoginCredential().setPassword(password);
            }
            if (fieldIsDefined(byonDefinition.getLoginCredential().getPrivateKey())) {
                String privateKey = secureStoreService.getValueForSecureVariableLabel(byonDefinition.getLoginCredential().getPrivateKey());
                byonDefinition.getLoginCredential().setPrivateKey(privateKey);
            }
        });
    }

    public ByonDefinition createNewByonDefinition(ByonDefinition newByonDefinitionRequest) {
        List<ByonDefinition> byonDefinitionsList = getByonDefinitionsListWithoutFillingSecureVariables().orElseGet(ArrayList::new);
        byonIdCreatorService.addIdsForByonDefinition(newByonDefinitionRequest, byonDefinitionsList);

        saveLoginCredentialsInSecureStore(newByonDefinitionRequest);

        byonDefinitionsList.add(newByonDefinitionRequest);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
        return newByonDefinitionRequest;
    }

    private void saveLoginCredentialsInSecureStore(ByonDefinition newByonDefinitionRequest) {
        LoginCredential loginCredential = newByonDefinitionRequest.getLoginCredential();
        if (fieldIsDefined(loginCredential.getPassword())) {
            Pair<String, String> keyLabelForByonPassword = secureStoreService.createKeyLabelForByonPassword(loginCredential);
            this.cloudiatorApi.storeSecureVariable(keyLabelForByonPassword.getLeft(), loginCredential.getPassword());
            loginCredential.setPassword(keyLabelForByonPassword.getRight());
            log.info("Password saved in secure store under key: {}", keyLabelForByonPassword.getLeft());
        }
        if (fieldIsDefined(loginCredential.getPrivateKey())) {
            Pair<String, String> keyLabelForByonKey = secureStoreService.createKeyLabelForByonKey(loginCredential);
            this.cloudiatorApi.storeSecureVariable(keyLabelForByonKey.getLeft(), loginCredential.getPrivateKey());
            loginCredential.setPrivateKey(keyLabelForByonKey.getRight());
            log.info("Private key saved in secure store under key: {}", keyLabelForByonKey.getLeft());
        }
    }

    public ByonDefinition getByonDefinition(int byonDefinitionId) {
        return getByonDefinitionsListWithFilledCredentials().orElseGet(ArrayList::new)
                .stream()
                .filter(byonDefinition -> byonDefinitionId == byonDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
    }

    public void deleteByonDefinition(int byonDefinitionId) {
        List<ByonDefinition> byonDefinitionsList = deleteByonDefinitionFromList(byonDefinitionId);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
    }

    public ByonDefinition updateByonDefinition(int byonDefinitionId, ByonDefinition byonDefinitionToUpdate) {
        List<ByonDefinition> currentByonDefinitionsList = getByonDefinitionsListWithoutFillingSecureVariables().orElseGet(ArrayList::new);
        byonIdCreatorService.addMissingIdsInIpAddressesForByonDefinition(byonDefinitionToUpdate, currentByonDefinitionsList);
        List<ByonDefinition> byonDefinitionsList = deleteByonDefinitionFromList(byonDefinitionId);
        saveLoginCredentialsInSecureStore(byonDefinitionToUpdate);
        byonDefinitionsList.add(byonDefinitionToUpdate);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
        return getByonDefinition(byonDefinitionId);
    }

    private List<ByonDefinition> deleteByonDefinitionFromList(int byonDefinitionId) {
        List<ByonDefinition> byonDefinitionsList = getByonDefinitionsListWithoutFillingSecureVariables().orElseGet(ArrayList::new);
        ByonDefinition byonDefinitionToDelete = byonDefinitionsList.stream()
                .filter(byonDefinition -> byonDefinitionId == byonDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
        deleteSecureVariablesForByonDefinition(byonDefinitionToDelete);
        byonDefinitionsList.remove(byonDefinitionToDelete);
        return byonDefinitionsList;
    }

    private Optional<List<ByonDefinition>> getByonDefinitionsListWithoutFillingSecureVariables() {
        return Optional.ofNullable(yamlDataService.getDataFromYaml().getByonDefinitions());
    }

    private void deleteSecureVariablesForByonDefinition(ByonDefinition byonDefinitionToDelete) {
        if (fieldIsDefined(byonDefinitionToDelete.getLoginCredential().getPassword())) {
            secureStoreService.deleteSecureVariableByLabel(byonDefinitionToDelete.getLoginCredential().getPassword());
        }
        if (fieldIsDefined(byonDefinitionToDelete.getLoginCredential().getPrivateKey())) {
            secureStoreService.deleteSecureVariableByLabel(byonDefinitionToDelete.getLoginCredential().getPrivateKey());
        }
    }

    public ByonNode createByonNodeFromByonDefinition(int byonDefinitionId) {
        ByonDefinition byonDefinitionForNode = getByonDefinitionsListWithFilledCredentials().orElseGet(ArrayList::new)
                .stream()
                .filter(byonDefinition -> byonDefinition.getId() == byonDefinitionId)
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
        NewNode newNode = byonMapper.mapByonDefinitionToNewNode(byonDefinitionForNode);
        return cloudiatorApi.createNewByonNode(newNode);
    }

    public ByonEnums getByonEnums() {
        List<String> ipAddressesTypes = new ArrayList<>();
        List<String> ipVersions = new ArrayList<>();
        List<String> osFamilies = new ArrayList<>();
        List<String> osArchitecture = new ArrayList<>();
        for (IpAddressType value : IpAddressType.values()) {
            ipAddressesTypes.add(value.getValue());
        }
        for (IpVersion value : IpVersion.values()) {
            ipVersions.add(value.getValue());
        }
        for (OperatingSystemFamily value : OperatingSystemFamily.values()) {
            osFamilies.add(value.getValue());
        }
        for (OperatingSystemArchitecture value : OperatingSystemArchitecture.values()) {
            osArchitecture.add(value.getValue());
        }
        return ByonEnums.builder()
                .ipAddressTypes(ipAddressesTypes)
                .ipVersions(ipVersions)
                .osArchitectures(osArchitecture)
                .osFamilies(osFamilies)
                .build();
    }

    private boolean fieldIsDefined(String fieldValue) {
        return fieldValue != null && !StringUtils.isEmpty(fieldValue);
    }
}
