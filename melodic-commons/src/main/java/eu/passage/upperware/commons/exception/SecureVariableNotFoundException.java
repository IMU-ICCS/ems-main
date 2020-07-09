package eu.passage.upperware.commons.exception;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.NotFoundException;

@Getter
@Setter
public class SecureVariableNotFoundException extends NotFoundException {
    private String missingVariableName;

    public SecureVariableNotFoundException(String message, String missingVariableName) {
        super(message);
        this.missingVariableName = missingVariableName;
    }
}
