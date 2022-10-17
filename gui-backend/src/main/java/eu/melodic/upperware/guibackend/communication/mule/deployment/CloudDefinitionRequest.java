package eu.melodic.upperware.guibackend.communication.mule.deployment;

import eu.passage.upperware.commons.model.provider.CloudType;
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

    private SSHCredentialsRequest SSHCredentials;

    private String id;
}
