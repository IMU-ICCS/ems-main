package eu.passage.upperware.commons.model.provider;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SSHCredentials {

    private String username;

    private String keyPairName;

    private String privateKey;

}
