package eu.passage.upperware.commons.service.provider;

import eu.passage.upperware.commons.model.provider.CloudDefinition;
import eu.passage.upperware.commons.model.provider.ParentProperty;
import eu.passage.upperware.commons.model.provider.SingleProperty;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// todo remove this class for using DB
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderIdCreatorService {

    public void addIdForCloudDefinitionElements(CloudDefinition cloudDefinition, List<CloudDefinition> cloudDefinitionsForAllProviders) {

        // indexForCloudDefinition
        Long lastIndexForCloudDefinition = cloudDefinitionsForAllProviders.stream()
                .map(CloudDefinition::getId)
                .max(Long::compareTo)
                .orElse(0L);
        cloudDefinition.setId(lastIndexForCloudDefinition + 1);

//        indexForApi
        Long lastIndexForApi = cloudDefinitionsForAllProviders.stream()
                .map(cloudDefinition1 -> cloudDefinition1.getApi().getId())
                .max(Long::compareTo)
                .orElse(0L);
        cloudDefinition.getApi().setId(lastIndexForApi + 1);

//        indexForCredential
        Long lastIndexForCredential = cloudDefinitionsForAllProviders.stream()
                .map(cloudDefinition1 -> cloudDefinition1.getCredential().getId())
                .max(Long::compareTo)
                .orElse(0L);
        cloudDefinition.getCredential().setId(lastIndexForCredential + 1);

//        indexForCloudConfiguration
        Long lastIndexForCloudConfiguration = cloudDefinitionsForAllProviders.stream()
                .map(cloudDefinition1 -> cloudDefinition1.getCloudConfiguration().getId())
                .max(Long::compareTo)
                .orElse(0L);
        cloudDefinition.getCloudConfiguration().setId(lastIndexForCloudConfiguration + 1);

//        indexForParentProperty and single properties
        addMissingIdsForNewParentProperty(cloudDefinition.getCloudConfiguration().getProperties(), cloudDefinitionsForAllProviders);
    }

    private void addMissingIdsForNewParentProperty(List<ParentProperty> parentProperties, List<CloudDefinition> cloudDefinitionsForAllProviders) {
        int newDefinedSinglePropertiesNumber = 0;
        for (int parentI = 0; parentI < parentProperties.size(); parentI++) {
            parentProperties.get(parentI)
                    .setId(findLastIndexForParentProperty(cloudDefinitionsForAllProviders) + 1 + parentI);

            List<SingleProperty> singleProperties = parentProperties.get(parentI).getProperties();

            for (SingleProperty singleProperty : singleProperties) {
                singleProperty
                        .setId(findLastIndexForSingleProperty(cloudDefinitionsForAllProviders) + 1 + newDefinedSinglePropertiesNumber);
                newDefinedSinglePropertiesNumber++;
            }
        }
    }

    private long findLastIndexForParentProperty(List<CloudDefinition> cloudDefinitionsForAllProviders) {
        return cloudDefinitionsForAllProviders.stream()
                .map(cloudDefinition1 -> cloudDefinition1.getCloudConfiguration().getProperties())
                .flatMap(List::stream)
                .map(ParentProperty::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    private long findLastIndexForSingleProperty(List<CloudDefinition> cloudDefinitionsForAllProviders) {
        return cloudDefinitionsForAllProviders.stream()
                .map(cloudDefinition1 -> cloudDefinition1.getCloudConfiguration().getProperties())
                .flatMap(List::stream)
                .map(ParentProperty::getProperties)
                .flatMap(List::stream)
                .map(SingleProperty::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    public void createIdsForNewElements(CloudDefinition cloudDefinitionToUpdate, List<CloudDefinition> cloudDefinitionsForAllProviders) {
        List<ParentProperty> parentPropertiesWithoutId = cloudDefinitionToUpdate.getCloudConfiguration().getProperties().stream()
                .filter(parentProperty -> parentProperty.getId() == 0)
                .collect(Collectors.toList());

        addMissingIdsForNewParentProperty(parentPropertiesWithoutId, cloudDefinitionsForAllProviders);

        // add missing ids for single properties
        List<SingleProperty> singlePropertiesWithoutId = cloudDefinitionToUpdate.getCloudConfiguration().getProperties().stream()
                .filter(parentProperty -> parentProperty.getId() != 0)
                .map(ParentProperty::getProperties)
                .flatMap(List::stream)
                .filter(singleProperty -> singleProperty.getId() == 0)
                .collect(Collectors.toList());

        addMissingIdsForNewSinglePropertiesInOldParentProperties(singlePropertiesWithoutId, parentPropertiesWithoutId, cloudDefinitionsForAllProviders);
    }

    private void addMissingIdsForNewSinglePropertiesInOldParentProperties(List<SingleProperty> singlePropertiesWithoutId, List<ParentProperty> parentPropertiesWithoutId,
                                                                          List<CloudDefinition> cloudDefinitionsForAllProviders) {
        long newDefinedSinglePropertiesNumber = parentPropertiesWithoutId.stream()
                .map(ParentProperty::getProperties)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .size();
        for (SingleProperty singleProperty : singlePropertiesWithoutId) {
            singleProperty
                    .setId(findLastIndexForSingleProperty(cloudDefinitionsForAllProviders) + 1 + newDefinedSinglePropertiesNumber);
            newDefinedSinglePropertiesNumber++;
        }
    }
}
