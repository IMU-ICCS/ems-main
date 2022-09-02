package eu.passage.upperware.commons.model.provider;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CloudDefinition {

    private long id;

    private String endpoint;

    private CloudType cloudType;

    private Api api;

    private Credential credential;

    private CloudConfiguration cloudConfiguration;

    private SSHCredentials sshCredentials;

}
