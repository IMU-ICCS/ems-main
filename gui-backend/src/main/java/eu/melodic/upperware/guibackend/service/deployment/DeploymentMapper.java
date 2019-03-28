package eu.melodic.upperware.guibackend.service.deployment;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.models.services.frontend.DeploymentProcessRequestImpl;
import eu.melodic.upperware.guibackend.communication.mule.deployment.ApiRequest;
import eu.melodic.upperware.guibackend.communication.mule.deployment.CloudConfigurationRequest;
import eu.melodic.upperware.guibackend.communication.mule.deployment.CloudDefinitionRequest;
import eu.melodic.upperware.guibackend.communication.mule.deployment.CredentialRequest;
import eu.melodic.upperware.guibackend.controller.deployment.request.DeploymentRequest;
import eu.melodic.upperware.guibackend.model.provider.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeploymentMapper {

    public DeploymentProcessRequest mapDeploymentRequestToDeploymentProcessRequest(DeploymentRequest deploymentRequest, Watermark watermark) {
        DeploymentProcessRequest deploymentProcessRequest = new DeploymentProcessRequestImpl();
        deploymentProcessRequest.setUsername(deploymentRequest.getUsername());
        deploymentProcessRequest.setPassword(deploymentRequest.getPassword());
        deploymentProcessRequest.setApplicationId(deploymentRequest.getApplicationId());
        deploymentProcessRequest.setWatermark(watermark);
        deploymentProcessRequest.setCloudDefinitions(mapCloudDefinitionsToRequest(deploymentRequest.getCloudDefinitions()));
        return deploymentProcessRequest;
    }

    private List<Object> mapCloudDefinitionsToRequest(List<CloudDefinition> cloudDefinitions) {
        List<Object> result = new ArrayList<>();
        cloudDefinitions.forEach(cloudDefinition -> {
            CloudDefinitionRequest cloudDefinitionRequest = CloudDefinitionRequest.builder()
                    .cloudConfiguration(mapCloudConfigurationToRequest(cloudDefinition.getCloudConfiguration()))
                    .cloudType(cloudDefinition.getCloudType())
                    .api(mapApiToRequest(cloudDefinition.getApi()))
                    .credential(mapCredentialToRequest(cloudDefinition.getCredential()))
                    .endpoint(cloudDefinition.getEndpoint().trim().equals("") ? null : cloudDefinition.getEndpoint())
                    .id(RandomStringUtils.random(32, true, true))
                    .build();
            result.add(cloudDefinitionRequest);
        });

        return result;
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
        Map<String, String> result = new HashMap<>();
        properties.forEach(parentProperty -> parentProperty.getProperties()
                .forEach(singleProperty -> {
                    result.put(singleProperty.getKey(), singleProperty.getValue());
                }));
        return result;
    }

}
