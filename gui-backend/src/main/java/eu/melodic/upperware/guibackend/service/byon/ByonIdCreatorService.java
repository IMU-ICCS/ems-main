package eu.melodic.upperware.guibackend.service.byon;

import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import eu.melodic.upperware.guibackend.model.byon.IpAddress;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ByonIdCreatorService {
    public void addIdsForByonDefinition(ByonDefinition byonDefinition, List<ByonDefinition> byonDefinitionsList) {

        // index for ByonDefinition
        long lastId = byonDefinitionsList.stream()
                .map(ByonDefinition::getId)
                .max(Long::compareTo)
                .orElse(0L);
        byonDefinition.setId(lastId + 1);

        // index for LoginCredential
        long lastIndexForLoginCredential = byonDefinitionsList.stream()
                .map(byonDef -> byonDef.getLoginCredential().getId())
                .max(Long::compareTo)
                .orElse(0L);
        byonDefinition.getLoginCredential().setId(lastIndexForLoginCredential + 1);

        // index for each IpAddress
        long lastIndexForIpAddress = findLastIndexForIpAddress(byonDefinitionsList);
        for (int i = 0; i < byonDefinition.getIpAddresses().size(); i++) {
            byonDefinition.getIpAddresses().get(i)
                    .setId(lastIndexForIpAddress + 1 + i);
        }

        // index for NodeProperties
        long lastindexForNodeProperties = byonDefinitionsList.stream()
                .map(byonDef -> byonDef.getNodeProperties().getId())
                .max(Long::compareTo)
                .orElse(0L);
        byonDefinition.getNodeProperties().setId(lastindexForNodeProperties + 1);

        // index for OperatingSystem
        long lastIndexForOS = byonDefinitionsList.stream()
                .map(byonDef -> byonDef.getNodeProperties().getOperatingSystem().getId())
                .max(Long::compareTo)
                .orElse(0L);
        byonDefinition.getNodeProperties().getOperatingSystem().setId(lastIndexForOS + 1);

        // index for GeoLocation
        long lastIndexForGeoLocation = byonDefinitionsList.stream()
                .map(byonDef -> byonDef.getNodeProperties().getGeoLocation().getId())
                .max(Long::compareTo)
                .orElse(0L);
        byonDefinition.getNodeProperties().getGeoLocation().setId(lastIndexForGeoLocation + 1);
    }

    public void addMissingIdsInIpAddressesForByonDefinition(ByonDefinition byonDefinitionToUpdate, List<ByonDefinition> currentByonDefinitions) {
        long lastIndexForIpAddress = findLastIndexForIpAddress(currentByonDefinitions);
        List<IpAddress> ipAddressesWithoutIndex = byonDefinitionToUpdate.getIpAddresses().stream()
                .filter(ipAddress -> ipAddress.getId() == 0)
                .collect(Collectors.toList());
        for (int i = 0; i < ipAddressesWithoutIndex.size(); i++) {
            ipAddressesWithoutIndex.get(i).setId(lastIndexForIpAddress + 1 + i);
        }
    }

    private long findLastIndexForIpAddress(List<ByonDefinition> byonDefinitions) {
        return byonDefinitions.stream()
                .map(ByonDefinition::getIpAddresses)
                .flatMap(List::stream)
                .map(IpAddress::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }
}
