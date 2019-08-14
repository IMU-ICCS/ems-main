package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class CloudConfigurationRequest {

    private String nodeGroup;

    private Map<String, String> properties;
}
