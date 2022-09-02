package eu.melodic.upperware.guibackend.service.edge;

import eu.passage.upperware.commons.model.edge.EdgeDefinition;
import eu.passage.upperware.commons.model.internal.IpAddress;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EdgeIdCreatorService {
    public void addIdsForEdgeDefinition(EdgeDefinition edgeDefinition, List<EdgeDefinition> edgeDefinitionsList) {

        // index for edgeDefinition
        long lastId = edgeDefinitionsList.stream()
                .map(EdgeDefinition::getId)
                .max(Long::compareTo)
                .orElse(0L);
        edgeDefinition.setId(lastId + 1);

        // index for LoginCredential
        long lastIndexForLoginCredential = edgeDefinitionsList.stream()
                .map(edgeDef -> edgeDef.getLoginCredential().getId())
                .max(Long::compareTo)
                .orElse(0L);
        edgeDefinition.getLoginCredential().setId(lastIndexForLoginCredential + 1);

        // index for each IpAddress
        long lastIndexForIpAddress = findLastIndexForIpAddress(edgeDefinitionsList);
        for (int i = 0; i < edgeDefinition.getIpAddresses().size(); i++) {
            edgeDefinition.getIpAddresses().get(i)
                    .setId(lastIndexForIpAddress + 1 + i);
        }

        // index for NodeProperties
        long lastindexForNodeProperties = edgeDefinitionsList.stream()
                .map(edgeDef -> edgeDef.getNodeProperties().getId())
                .max(Long::compareTo)
                .orElse(0L);
        edgeDefinition.getNodeProperties().setId(lastindexForNodeProperties + 1);

        // index for OperatingSystem
        long lastIndexForOS = edgeDefinitionsList.stream()
                .map(edgeDef -> edgeDef.getNodeProperties().getOperatingSystem().getId())
                .max(Long::compareTo)
                .orElse(0L);
        edgeDefinition.getNodeProperties().getOperatingSystem().setId(lastIndexForOS + 1);

    }

    public void addMissingIdsInIpAddressesForEdgeDefinition(EdgeDefinition edgeDefinitionToUpdate, List<EdgeDefinition> currentedgeDefinitions) {
        long lastIndexForIpAddress = findLastIndexForIpAddress(currentedgeDefinitions);
        List<IpAddress> ipAddressesWithoutIndex = edgeDefinitionToUpdate.getIpAddresses().stream()
                .filter(ipAddress -> ipAddress.getId() == 0)
                .collect(Collectors.toList());
        for (int i = 0; i < ipAddressesWithoutIndex.size(); i++) {
            ipAddressesWithoutIndex.get(i).setId(lastIndexForIpAddress + 1 + i);
        }
    }

    private long findLastIndexForIpAddress(List<EdgeDefinition> edgeDefinitions) {
        return edgeDefinitions.stream()
                .map(EdgeDefinition::getIpAddresses)
                .flatMap(List::stream)
                .map(IpAddress::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }
}
