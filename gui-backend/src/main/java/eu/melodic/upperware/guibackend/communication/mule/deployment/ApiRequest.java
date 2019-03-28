package eu.melodic.upperware.guibackend.communication.mule.deployment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApiRequest {
    private String providerName;
}
