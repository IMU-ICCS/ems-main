package eu.melodic.upperware.guibackend.service.byon;

import eu.passage.upperware.commons.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.exception.ByonDefinitionNotFoundException;
import eu.passage.upperware.commons.model.byon.ByonDefinition;
import eu.passage.upperware.commons.model.byon.ByonEnums;
import eu.passage.upperware.commons.model.byon.LoginCredential;
import eu.passage.upperware.commons.service.store.SecureStoreService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
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

    public Optional<List<ByonDefinition>> getByonDefList(boolean fillSecureVariables) {
        List<ByonDefinition> byonDefinitions = yamlDataService.getDataFromYaml().getByonDefinitions();
        if (byonDefinitions != null && fillSecureVariables) {
            fillCredentials(byonDefinitions);
        }
        return Optional.ofNullable(byonDefinitions);
    }

    private void fillCredentials(List<ByonDefinition> byonDefinitions) {
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

    public ByonDefinition createByonDef(ByonDefinition newByonDefinitionRequest) {
        List<ByonDefinition> byonDefinitionsList = getByonDefList(false).orElseGet(ArrayList::new);
        byonIdCreatorService.addIdsForByonDefinition(newByonDefinitionRequest, byonDefinitionsList);

        saveCredentialsInSecureStore(newByonDefinitionRequest);

        byonDefinitionsList.add(newByonDefinitionRequest);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
        return newByonDefinitionRequest;
    }

    private void saveCredentialsInSecureStore(ByonDefinition newByonDefinitionRequest) {
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

    public ByonDefinition getByonDef(int byonDefinitionId) {
        return getByonDefList(true).orElseGet(ArrayList::new)
                .stream()
                .filter(byonDefinition -> byonDefinitionId == byonDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
    }

    public void deleteByonDef(int byonDefinitionId) {
        List<ByonDefinition> byonDefinitionsList = getByonDefList(false).orElseGet(ArrayList::new);
        deleteByonDefFromList(byonDefinitionId, byonDefinitionsList);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
    }

    public ByonDefinition updateByonDef(int byonDefinitionId, ByonDefinition byonDefinitionToUpdate) {
        List<ByonDefinition> currentByonDefinitionsList = getByonDefList(false).orElseGet(ArrayList::new);
        byonIdCreatorService.addMissingIdsInIpAddressesForByonDefinition(byonDefinitionToUpdate, currentByonDefinitionsList);
        deleteByonDefFromList(byonDefinitionId, currentByonDefinitionsList);
        saveCredentialsInSecureStore(byonDefinitionToUpdate);
        currentByonDefinitionsList.add(byonDefinitionToUpdate);
        yamlDataService.updateByonDefinitionInYamlFile(currentByonDefinitionsList);
        return getByonDef(byonDefinitionId);
    }

    private void deleteByonDefFromList(int byonDefinitionId, List<ByonDefinition> byonDefinitionsList) {
        ByonDefinition byonDefinitionToDelete = byonDefinitionsList.stream()
                .filter(byonDefinition -> byonDefinitionId == byonDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
        deleteSecureVariables(byonDefinitionToDelete);
        byonDefinitionsList.remove(byonDefinitionToDelete);
    }

    private void deleteSecureVariables(ByonDefinition byonDefinitionToDelete) {
        if (fieldIsDefined(byonDefinitionToDelete.getLoginCredential().getPassword())) {
            secureStoreService.deleteSecureVariableByLabel(byonDefinitionToDelete.getLoginCredential().getPassword());
        }
        if (fieldIsDefined(byonDefinitionToDelete.getLoginCredential().getPrivateKey())) {
            secureStoreService.deleteSecureVariableByLabel(byonDefinitionToDelete.getLoginCredential().getPrivateKey());
        }
    }

    public ByonNode createByonNode(int byonDefinitionId) {
        ByonDefinition byonDefinitionForNode = getByonDefList(true).orElseGet(ArrayList::new)
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
