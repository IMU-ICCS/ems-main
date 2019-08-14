package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiRequest {
    private String providerName;
}
