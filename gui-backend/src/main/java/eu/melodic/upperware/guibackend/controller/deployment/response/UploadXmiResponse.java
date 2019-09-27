package eu.melodic.upperware.guibackend.controller.deployment.response;

import eu.melodic.upperware.guibackend.controller.deployment.common.SecureVariable;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadXmiResponse {
    private String modelName;
    private List<SecureVariable> secureVariables;
    private HttpStatus httpStatus;
    private String message;
}
