package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CredentialRequest {

    private String user;

    private String secret;
}
