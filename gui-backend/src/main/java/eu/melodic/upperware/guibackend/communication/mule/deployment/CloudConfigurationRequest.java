package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CloudConfigurationRequest {

    private String nodeGroup;

    private Map<String, String> properties;
}
