package eu.melodic.upperware.guibackend.service.byon;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.exception.ByonDefinitionNotFoundException;
import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import eu.melodic.upperware.guibackend.model.byon.ByonEnums;
import eu.melodic.upperware.guibackend.service.yaml.YamlDataService;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ByonService {

    private YamlDataService yamlDataService;
    private ByonIdCreatorService byonIdCreatorService;
    private CloudiatorApi cloudiatorApi;

    public List<ByonDefinition> getByonDefinitionsList() {
        //todo fill login credentilas from secure store
        return yamlDataService.getDataFromYaml().getByonDefinitions();
    }

    public ByonDefinition createNewByonDefinition(ByonDefinition newByonDefinitionRequest) {
        List<ByonDefinition> byonDefinitionsList = getByonDefinitionsList();
        byonIdCreatorService.addIdForByonDefinition(newByonDefinitionRequest, byonDefinitionsList);

        // todo save login credentials in secure way (maybe in SecureStore?)

        byonDefinitionsList.add(newByonDefinitionRequest);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
        return newByonDefinitionRequest;
    }

    public ByonNode createByonNodeFromByonDefinition(int byonDefinitionId) {
        // todo fill login credentials
        ByonDefinition byonDefinitionForNode = getByonDefinitionsList().stream()
                .filter(byonDefinition -> byonDefinition.getId() == byonDefinitionId)
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
        return cloudiatorApi.createNewByonNode(byonDefinitionForNode);
    }

    public ByonDefinition getByonDefinition(int byonDefinitionId) {
        // todo get login credentials from secure way (maybe in SecureStore?)
        return getByonDefinitionsList().stream()
                .filter(byonDefinition -> byonDefinitionId == byonDefinition.getId())
                .findFirst()
                .orElseThrow(() -> new ByonDefinitionNotFoundException(byonDefinitionId));
    }

    public void deleteByonDefinition(int byonDefinitionId) {
        // todo delete login credentials from secure store
        List<ByonDefinition> byonDefinitionsList = getByonDefinitionsList();
        ByonDefinition byonDefinitionToDelete = getByonDefinition(byonDefinitionId);
        byonDefinitionsList.remove(byonDefinitionToDelete);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
    }

    public ByonDefinition updateByonDefinition(int byonDefinitionId, ByonDefinition byonDefinitionToUpdate) {
        // todo update login credentials
        ByonDefinition oldByonDefinition = getByonDefinition(byonDefinitionId);
        List<ByonDefinition> byonDefinitionsList = getByonDefinitionsList();
        byonDefinitionsList.remove(oldByonDefinition);
        byonDefinitionsList.add(byonDefinitionToUpdate);
        yamlDataService.updateByonDefinitionInYamlFile(byonDefinitionsList);
        return getByonDefinition(byonDefinitionId);
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
}
