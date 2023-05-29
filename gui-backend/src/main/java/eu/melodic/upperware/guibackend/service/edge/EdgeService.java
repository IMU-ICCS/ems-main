package eu.melodic.upperware.guibackend.service.edge;


import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUI;
import eu.melodic.upperware.guibackend.exception.EdgeDefinitionNotFoundException;

import eu.passage.upperware.commons.model.edge.EdgeDefinition;
import eu.passage.upperware.commons.model.edge.EdgeEnums;
import eu.passage.upperware.commons.model.edge.EdgeNode;
import eu.passage.upperware.commons.model.internal.*;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.Job;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EdgeService {

    private YamlDataService yamlDataService;
    private EdgeIdCreatorService edgeIdCreatorService;
    private EdgeMapper edgeMapper;
    private final SecureStoreDBService secureStoreDBService;
    private ProactiveClientServiceGUI proactiveClientServiceGUI;

    public Optional<List<EdgeDefinition>> getEdgeDefList(boolean fillSecureVariables) {
        List<EdgeDefinition> EdgeDefinitions = yamlDataService.getDataFromYaml().getEdgeDefinitions();
        if (EdgeDefinitions != null && fillSecureVariables) {
            fillCredentials(EdgeDefinitions);
        }
        return Optional.ofNullable(EdgeDefinitions);
    }

    private void fillCredentials(List<EdgeDefinition> EdgeDefinitions) {
        EdgeDefinitions.forEach(EdgeDefinition -> {
            if (fieldIsDefined(EdgeDefinition.getLoginCredential().getPassword())) {
                String password2 = secureStoreDBService.getValueForSecureVariableLabel(EdgeDefinition.getLoginCredential().getPassword());
                log.info("JM DEV[EdgeService]: fillCredentials: password2={}", secureStoreDBService.maskSensitiveText(password2));
                EdgeDefinition.getLoginCredential().setPassword(password2);
            }
            if (fieldIsDefined(EdgeDefinition.getLoginCredential().getPrivateKey())) {
                String privateKey2 = secureStoreDBService.getValueForSecureVariableLabel(EdgeDefinition.getLoginCredential().getPrivateKey());
                log.info("JM DEV[EdgeService]: fillCredentials: privateKey2={}", secureStoreDBService.maskSensitiveText(privateKey2));
                EdgeDefinition.getLoginCredential().setPrivateKey(privateKey2);
            }
        });
    }

    public EdgeDefinition createEdgeDef(EdgeDefinition newEdgeDefinitionRequest) {
        List<EdgeDefinition> EdgeDefinitionsList = getEdgeDefList(false).orElseGet(ArrayList::new);
        edgeIdCreatorService.addIdsForEdgeDefinition(newEdgeDefinitionRequest, EdgeDefinitionsList);

        saveCredentialsInSecureStore(newEdgeDefinitionRequest);

        EdgeDefinitionsList.add(newEdgeDefinitionRequest);
        yamlDataService.updateEdgeDefinitionInYamlFile(EdgeDefinitionsList);
        return newEdgeDefinitionRequest;
    }

    private void saveCredentialsInSecureStore(EdgeDefinition newEdgeDefinitionRequest) {
        LoginCredential loginCredential = newEdgeDefinitionRequest.getLoginCredential();
        if (fieldIsDefined(loginCredential.getPassword())) {
            Pair<String, String> keyLabelForEdgePassword2 = secureStoreDBService.createKeyLabelForEdgePassword(loginCredential);
            log.info("JM DEV[EdgeService]: saveCredentialsInSecureStore: keyLabelForEdgePassword2={}", keyLabelForEdgePassword2);
            this.secureStoreDBService.storeSecureVariable(keyLabelForEdgePassword2.getLeft(), loginCredential.getPassword());
            loginCredential.setPassword(keyLabelForEdgePassword2.getRight());
            log.info("Password saved in secure store under key: {}", keyLabelForEdgePassword2.getLeft());
        }
        if (fieldIsDefined(loginCredential.getPrivateKey())) {
            Pair<String, String> keyLabelForEdgeKey2 = secureStoreDBService.createKeyLabelForEdgeKey(loginCredential);
            log.info("JM DEV[EdgeService]: saveCredentialsInSecureStore: keyLabelForEdgeKey2={}", keyLabelForEdgeKey2);
            this.secureStoreDBService.storeSecureVariable(keyLabelForEdgeKey2.getLeft(), loginCredential.getPrivateKey());
            loginCredential.setPrivateKey(keyLabelForEdgeKey2.getRight());
            log.info("Private key saved in secure store under key: {}", keyLabelForEdgeKey2.getLeft());
        }
    }

    public EdgeDefinition getEdgeDef(int EdgeDefinitionId) {
        return getEdgeDefList(true).orElseGet(ArrayList::new)
                .stream()
                .filter(EdgeDefinition -> EdgeDefinitionId == EdgeDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new EdgeDefinitionNotFoundException(EdgeDefinitionId));
    }

    public void deleteEdgeDef(int EdgeDefinitionId) {
        List<EdgeDefinition> EdgeDefinitionsList = getEdgeDefList(false).orElseGet(ArrayList::new);
        deleteEdgeDefFromList(EdgeDefinitionId, EdgeDefinitionsList);
        yamlDataService.updateEdgeDefinitionInYamlFile(EdgeDefinitionsList);
    }

    public EdgeDefinition updateEdgeDef(int EdgeDefinitionId, EdgeDefinition EdgeDefinitionToUpdate) {
        List<EdgeDefinition> currentEdgeDefinitionsList = getEdgeDefList(false).orElseGet(ArrayList::new);
        edgeIdCreatorService.addMissingIdsInIpAddressesForEdgeDefinition(EdgeDefinitionToUpdate, currentEdgeDefinitionsList);
        deleteEdgeDefFromList(EdgeDefinitionId, currentEdgeDefinitionsList);
        saveCredentialsInSecureStore(EdgeDefinitionToUpdate);
        currentEdgeDefinitionsList.add(EdgeDefinitionToUpdate);
        yamlDataService.updateEdgeDefinitionInYamlFile(currentEdgeDefinitionsList);
        return getEdgeDef(EdgeDefinitionId);
    }

    private void deleteEdgeDefFromList(int edgeDefinitionId, List<EdgeDefinition> edgeDefinitionsList) {
        EdgeDefinition edgeDefinitionToDelete = edgeDefinitionsList.stream()
                .filter(edgeDefinition -> edgeDefinitionId == edgeDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new EdgeDefinitionNotFoundException(edgeDefinitionId));
        deleteSecureVariables(edgeDefinitionToDelete);
        edgeDefinitionsList.remove(edgeDefinitionToDelete);
    }

    private void deleteSecureVariables(EdgeDefinition edgeDefinitionToDelete) {
        if (fieldIsDefined(edgeDefinitionToDelete.getLoginCredential().getPassword())) {
            log.info("JM DEV[EdgeService]: deleteSecureVariables: EdgeDefinitionToDelete.getLoginCredential().getPassword()={}", edgeDefinitionToDelete.getLoginCredential().getPassword());
            secureStoreDBService.deleteSecureVariableByLabel(edgeDefinitionToDelete.getLoginCredential().getPassword());
        }
        if (fieldIsDefined(edgeDefinitionToDelete.getLoginCredential().getPrivateKey())) {
            log.info("JM DEV[EdgeService]: deleteSecureVariables: EdgeDefinitionToDelete.getLoginCredential().getPrivateKey()={}", edgeDefinitionToDelete.getLoginCredential().getPrivateKey());
            secureStoreDBService.deleteSecureVariableByLabel(edgeDefinitionToDelete.getLoginCredential().getPrivateKey());
        }
    }

    public EdgeNode createEdgeNode(int edgeDefinitionId, String applicationId) {
        EdgeDefinition edgeDefinitionForNode = getEdgeDefList(true).orElseGet(ArrayList::new)
                .stream()
                .filter(edgeDefinition -> edgeDefinition.getId() == edgeDefinitionId)
                .findFirst()
                .orElseThrow(() -> new EdgeDefinitionNotFoundException(edgeDefinitionId));
        final org.ow2.proactive.sal.model.EdgeDefinition edgeDefinitionProactive = edgeMapper.mapEdgeDefinitionToProactive(edgeDefinitionForNode);
        log.info("JM DEV[EdgeService]: createEdgeNode: EdgeDefinitionProactive={}", edgeDefinitionProactive);
        final Optional<org.ow2.proactive.sal.model.EdgeNode> EdgeNodeProactive = Optional.ofNullable(proactiveClientServiceGUI.registerNewEdgeNode(edgeDefinitionProactive, applicationId));
        log.info("JM DEV[EdgeService]: createEdgeNode: EdgeNodeProactive={}", EdgeNodeProactive);
        EdgeNode EdgeNode = null;
        if(EdgeNodeProactive.isPresent()) {
            EdgeNode = edgeMapper.mapProactiveEdgeNodeToInternal(EdgeNodeProactive.get());
        }
        log.info("JM DEV[EdgeService]: createEdgeNode: internal EdgeNode={}", EdgeNode);
        return EdgeNode;
    }

    public EdgeEnums getEdgeEnums() {
        List<String> systemArch = new ArrayList<>();
        List<String> ipAddressesTypes = new ArrayList<>();
        List<String> ipVersions = new ArrayList<>();
        List<String> osFamilies = new ArrayList<>();
        List<String> osArchitecture = new ArrayList<>();

        for (IpAddressType value : IpAddressType.values()) {
            ipAddressesTypes.add(value.name());
        }
        for (IpVersion value : IpVersion.values()) {
            ipVersions.add(value.name());
        }
        for (OperatingSystemFamily value : OperatingSystemFamily.values()) {
            osFamilies.add(value.name());
        }
        for (OperatingSystemArchitecture value : OperatingSystemArchitecture.values()) {
            osArchitecture.add(value.name());
        }
        for (EdgeSystemArchitecture value : EdgeSystemArchitecture.values()) {
            systemArch.add(value.name());
        }
        return EdgeEnums.builder()
                .systemArch(systemArch)
                .ipAddressTypes(ipAddressesTypes)
                .ipVersions(ipVersions)
                .osArchitectures(osArchitecture)
                .osFamilies(osFamilies)
                .build();
    }

    private boolean fieldIsDefined(String fieldValue) {
        return fieldValue != null && !StringUtils.isEmpty(fieldValue);
    }

    public List<EdgeNode> getEdgeNodesList(String applicationId) {
        log.info("JM DEV[EdgeService]: getEdgeNodesList: applicationId={}", applicationId);
        final List<org.ow2.proactive.sal.model.EdgeNode> EdgeNodeList = proactiveClientServiceGUI.getEdgeNodeList(applicationId);
        log.info("JM DEV[EdgeService]: getEdgeNodesList: EdgeNodeList={}", EdgeNodeList);
        return EdgeNodeList.stream()
                .map(edgeMapper::mapProactiveEdgeNodeToInternal)
                .collect(Collectors.toList());
    }

    public List<EdgeNode> getAllEdgeNodesList() {
        log.info("JM DEV[EdgeService]: getAllEdgeNodesList starting");
        final List<Job> allJobs = proactiveClientServiceGUI.getAllJobs();
        log.info("JM DEV[EdgeService]: getAllEdgeNodesList: allJobs={}", allJobs);

        final List<EdgeNode> EdgeNodes = allJobs.stream()
                .map(job -> this.getEdgeNodesList(job.getJobId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        log.info("JM DEV[EdgeService]: getAllEdgeNodesList: EdgeNodes={}", EdgeNodes);

        return EdgeNodes;
    }
}
