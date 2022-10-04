package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SSHCredentialsRequest {

    private String username;

    private String keyPairName;

    private String privateKey;

}
