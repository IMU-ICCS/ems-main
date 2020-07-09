package eu.passage.upperware.commons.service.provider;

import eu.passage.upperware.commons.exception.ValidationException;
import eu.passage.upperware.commons.model.provider.CloudDefinition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProviderValidationService {

    private final static Pattern NODE_GROUP_PATTERN = Pattern.compile("^[a-z0-9]{3,}$");

    public void validateProviderUser(CloudDefinition cloudDefinition, List<CloudDefinition> cloudDefinitionsForAllProviders) {
        //user must be unique for credentials from one provider
        if (cloudDefinitionsForAllProviders.stream()
                .filter(cloudDefinition1 -> cloudDefinition1.getApi().getProviderName().equals(cloudDefinition.getApi().getProviderName()))
                .anyMatch(cloudDefinition1 -> cloudDefinition1.getCredential().getUser().equals(cloudDefinition.getCredential().getUser()))) {
            throw new ValidationException(String.format("Such user %s in credentials for provider %s already exists",
                    cloudDefinition.getCredential().getUser(), cloudDefinition.getApi().getProviderName()));
        }
    }

    public void validateUniquenessOfKeysInSingleProperties(CloudDefinition cloudDefinition) {
        cloudDefinition.getCloudConfiguration().getProperties().forEach(parentProperty ->
                parentProperty.getProperties().forEach(singlePropertyForValidation -> {
                    if (parentProperty.getProperties().stream()
                            .anyMatch(singleProperty -> (singleProperty.getKey().equals(singlePropertyForValidation.getKey()) &&
                                    (!singlePropertyForValidation.equals(singleProperty))))) {
                        throw new ValidationException(String.format("Key: %s already exists for property %s. " +
                                "Keys for one single property must be unique.", singlePropertyForValidation.getKey(), parentProperty.getName()));
                    }
                })
        );
    }

    public void validateElementsIds(CloudDefinition oldCloudDefinition, CloudDefinition cloudDefinitionToUpdate) {
        if (oldCloudDefinition.getApi().getId() != (cloudDefinitionToUpdate.getApi().getId())) {
            throw new ValidationException(String.format("New api for provider for cloud definition has invalid id. Id has changed from %d to %d",
                    oldCloudDefinition.getApi().getId(), cloudDefinitionToUpdate.getApi().getId()));
        }
        if (oldCloudDefinition.getCredential().getId() != (cloudDefinitionToUpdate.getCredential().getId())) {
            throw new ValidationException(String.format("New credential for provider for cloud definition has invalid id. Id has changed from %d to %d",
                    oldCloudDefinition.getCredential().getId(), cloudDefinitionToUpdate.getCredential().getId()));
        }
        if (oldCloudDefinition.getCloudConfiguration().getId() != cloudDefinitionToUpdate.getCloudConfiguration().getId()) {
            throw new ValidationException(String.format("New cloud configuration has invalid id. Id has changed from %d to %d",
                    oldCloudDefinition.getCloudConfiguration().getId(), cloudDefinitionToUpdate.getCloudConfiguration().getId()));
        }
    }

    public void validateUniquenessOfPropertyName(CloudDefinition cloudDefinition) {
        cloudDefinition.getCloudConfiguration().getProperties().forEach(validatedParentProperty -> {
            if (cloudDefinition.getCloudConfiguration().getProperties().stream()
                    .anyMatch(parentProperty -> (parentProperty.getName().equals(validatedParentProperty.getName()) && !parentProperty.equals(validatedParentProperty)))) {
                throw new ValidationException(String.format("Such property name %s for provider %s already exists. Names must be unique.",
                        validatedParentProperty.getName(), cloudDefinition.getApi().getProviderName()));
            }
        });
    }

    public void validateNodeGroup(String nodeGroup) {
        if (!NODE_GROUP_PATTERN.matcher(nodeGroup).matches()) {
            throw new ValidationException(String.format("Invalid node group: %s. Required min 3 sings length, built only from lowercase and digits.", nodeGroup));
        }
    }
}
