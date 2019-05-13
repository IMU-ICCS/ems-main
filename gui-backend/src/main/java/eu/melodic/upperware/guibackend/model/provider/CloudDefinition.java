package eu.melodic.upperware.guibackend.model.provider;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CloudDefinition {

    private long id;

    private String endpoint;

    private CloudType cloudType;

    private Api api;

    private Credential credential;

    private CloudConfiguration cloudConfiguration;
}
