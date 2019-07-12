package eu.melodic.upperware.guibackend.exception.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissingSecureVariableErrorResponse extends ErrorResponse {
    private String missingVariableName;

    @Builder
    public MissingSecureVariableErrorResponse(String message, String errorName, String missingVariableName) {
        super(message, errorName);
        this.missingVariableName = missingVariableName;
    }
}
