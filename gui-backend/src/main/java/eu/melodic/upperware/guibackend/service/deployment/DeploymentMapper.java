package eu.melodic.upperware.guibackend.service.deployment;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.models.services.frontend.DeploymentProcessRequestImpl;
import eu.melodic.upperware.guibackend.communication.mule.deployment.ApiRequest;
import eu.melodic.upperware.guibackend.communication.mule.deployment.CloudConfigurationRequest;
import eu.melodic.upperware.guibackend.communication.mule.deployment.CloudDefinitionRequest;
import eu.melodic.upperware.guibackend.communication.mule.deployment.CredentialRequest;
import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.passage.upperware.commons.model.provider.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeploymentMapper {

    public DeploymentProcessRequest mapDeploymentRequestToDeploymentProcessRequest(DeploymentRequest deploymentRequest, Watermark watermark) {
        DeploymentProcessRequest deploymentProcessRequest = new DeploymentProcessRequestImpl();
        deploymentProcessRequest.setApplicationId(deploymentRequest.getApplicationId());
        deploymentProcessRequest.setWatermark(watermark);
        deploymentProcessRequest.setIsSimulation(deploymentRequest.getIsSimulation());
        deploymentProcessRequest.setCloudDefinitions(mapCloudDefinitionsToRequest(deploymentRequest.getCloudDefinitions()));
        return deploymentProcessRequest;
    }

    private List<Object> mapCloudDefinitionsToRequest(List<CloudDefinition> cloudDefinitions) {
        return cloudDefinitions.stream().map(cloudDefinition -> CloudDefinitionRequest.builder()
                .cloudConfiguration(mapCloudConfigurationToRequest(cloudDefinition.getCloudConfiguration()))
                .cloudType(cloudDefinition.getCloudType())
                .api(mapApiToRequest(cloudDefinition.getApi()))
                .credential(mapCredentialToRequest(cloudDefinition.getCredential()))
                .endpoint(StringUtils.isBlank(cloudDefinition.getEndpoint().trim()) ? null : cloudDefinition.getEndpoint())
                .id(RandomStringUtils.random(16, true, true))
                .build()).collect(Collectors.toList());
    }

    private CredentialRequest mapCredentialToRequest(Credential credential) {
        return CredentialRequest.builder()
                .user(credential.getUser())
                .secret(credential.getSecret())
                .build();
    }

    private ApiRequest mapApiToRequest(Api api) {
        return ApiRequest.builder()
                .providerName(api.getProviderName())
                .build();
    }

    private CloudConfigurationRequest mapCloudConfigurationToRequest(CloudConfiguration cloudConfiguration) {
        return CloudConfigurationRequest.builder()
                .nodeGroup(cloudConfiguration.getNodeGroup())
                .properties(mapCloudPropertiesToMap(cloudConfiguration.getProperties()))
                .build();
    }

    private Map<String, String> mapCloudPropertiesToMap(List<ParentProperty> properties) {
        return properties.stream()
                .map(ParentProperty::getProperties)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(SingleProperty::getKey, SingleProperty::getValue));
    }

}
