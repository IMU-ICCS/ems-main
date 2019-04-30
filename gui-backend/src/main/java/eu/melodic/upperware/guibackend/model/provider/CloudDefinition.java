package eu.melodic.upperware.guibackend.model.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CloudDefinition {

    private Integer id;

    private String endpoint;

    private CloudType cloudType;

    private Api api;

    private Credential credential;

    private CloudConfiguration cloudConfiguration;
}
