package eu.melodic.upperware.guibackend.exception.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CamundaVariableErrorResponse {
    private String message;
    private String variableName;
}
