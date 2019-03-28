package eu.melodic.upperware.guibackend.model.provider;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CloudDefinition {

    private Integer id;

    private String endpoint;

    private CloudType cloudType;

    private Api api;

    private Credential credential;

    private CloudConfiguration cloudConfiguration;
}
