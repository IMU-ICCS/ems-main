package eu.melodic.upperware.guibackend.exception.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CamundaVariableErrorResponse extends ErrorResponse {
    private String variableName;

    @Builder
    public CamundaVariableErrorResponse(String message, String errorName, String variableName) {
        super(message, errorName);
        this.variableName = variableName;
    }
}
