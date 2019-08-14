package eu.melodic.upperware.guibackend.communication.mule.deployment;

import eu.melodic.upperware.guibackend.model.provider.CloudType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CloudDefinitionRequest {

    private String endpoint;

    private CloudType cloudType;

    private ApiRequest api;

    private CredentialRequest credential;

    private CloudConfigurationRequest cloudConfiguration;

    private String id;
}
