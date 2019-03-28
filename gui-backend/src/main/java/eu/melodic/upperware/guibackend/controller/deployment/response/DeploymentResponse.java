package eu.melodic.upperware.guibackend.controller.deployment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentResponse {

    private List<ProcessCreationResultItem> processCreationResult;
    private List<Object> feedCloudiatorResponse;
    private int processAuthorizationStatus;
    private List<Map<String, String>> processAuthorizationResponse;
}
