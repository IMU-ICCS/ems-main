package eu.melodic.upperware.guibackend.service.byon;

import eu.melodic.upperware.guibackend.exception.ByonDefinitionNotFoundException;
import eu.passage.upperware.commons.model.byon.ByonDefinition;
import eu.passage.upperware.commons.model.byon.ByonEnums;
import eu.passage.upperware.commons.model.byon.LoginCredential;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import io.github.cloudiator.rest.model.ByonNode;
import io.github.cloudiator.rest.model.IpAddressType;
import io.github.cloudiator.rest.model.IpVersion;
import io.github.cloudiator.rest.model.NewNode;
import io.github.cloudiator.rest.model.OperatingSystemArchitecture;
import io.github.cloudiator.rest.model.OperatingSystemFamily;
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
    //    private CloudiatorApi cloudiatorApi;
    private ByonMapper byonMapper;
    private final SecureStoreDBService secureStoreDBService;

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
                String password2 = secureStoreDBService.getValueForSecureVariableLabel(byonDefinition.getLoginCredential().getPassword());
                log.info("LSZ DEV[ByonService]: fillCredentials: password2={}", secureStoreDBService.maskSensitiveText(password2));
                byonDefinition.getLoginCredential().setPassword(password2);
            }
            if (fieldIsDefined(byonDefinition.getLoginCredential().getPrivateKey())) {
                String privateKey2 = secureStoreDBService.getValueForSecureVariableLabel(byonDefinition.getLoginCredential().getPrivateKey());
                log.info("LSZ DEV[ByonService]: fillCredentials: privateKey2={}", secureStoreDBService.maskSensitiveText(privateKey2));
                byonDefinition.getLoginCredential().setPrivateKey(privateKey2);
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
            Pair<String, String> keyLabelForByonPassword2 = secureStoreDBService.createKeyLabelForByonPassword(loginCredential);
            log.info("LSZ DEV[ByonService]: saveCredentialsInSecureStore: keyLabelForByonPassword2={}", keyLabelForByonPassword2);
            this.secureStoreDBService.storeSecureVariable(keyLabelForByonPassword2.getLeft(), loginCredential.getPassword());
            loginCredential.setPassword(keyLabelForByonPassword2.getRight());
            log.info("Password saved in secure store under key: {}", keyLabelForByonPassword2.getLeft());
        }
        if (fieldIsDefined(loginCredential.getPrivateKey())) {
            Pair<String, String> keyLabelForByonKey2 = secureStoreDBService.createKeyLabelForByonKey(loginCredential);
            log.info("LSZ DEV[ByonService]: saveCredentialsInSecureStore: keyLabelForByonKey2={}", keyLabelForByonKey2);
            this.secureStoreDBService.storeSecureVariable(keyLabelForByonKey2.getLeft(), loginCredential.getPrivateKey());
            loginCredential.setPrivateKey(keyLabelForByonKey2.getRight());
            log.info("Private key saved in secure store under key: {}", keyLabelForByonKey2.getLeft());
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
            log.info("LSZ DEV[ByonService]: deleteSecureVariables: byonDefinitionToDelete.getLoginCredential().getPassword()={}", byonDefinitionToDelete.getLoginCredential().getPassword());
            secureStoreDBService.deleteSecureVariableByLabel(byonDefinitionToDelete.getLoginCredential().getPassword());
        }
        if (fieldIsDefined(byonDefinitionToDelete.getLoginCredential().getPrivateKey())) {
            log.info("LSZ DEV[ByonService]: deleteSecureVariables: byonDefinitionToDelete.getLoginCredential().getPrivateKey()={}", byonDefinitionToDelete.getLoginCredential().getPrivateKey());
            secureStoreDBService.deleteSecureVariableByLabel(byonDefinitionToDelete.getLoginCredential().getPrivateKey());
        }
    }

    public ByonNode createByonNode(int byonDefinitionId) {
        ByonDefinition byonDefinitionForNode = getByonDefList(true).orElseGet(ArrayList::new)
                .stream()
                .filter(byonDefinition -> byonDefinition.getId() == byonDefinitionId)
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
        NewNode newNode = byonMapper.mapByonDefinitionToNewNode(byonDefinitionForNode);
//        return cloudiatorApi.createNewByonNode(newNode);
        return new ByonNode();
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
