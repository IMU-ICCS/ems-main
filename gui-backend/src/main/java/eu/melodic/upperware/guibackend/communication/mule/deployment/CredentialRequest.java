package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CredentialRequest {

    private String user;

    private String secret;
}
