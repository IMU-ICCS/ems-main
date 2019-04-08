package eu.melodic.upperware.guibackend.controller.deployment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NcQueryErrorResponse {
    private String providerName;
    private String username;
    private String status;
    private String error;
}
