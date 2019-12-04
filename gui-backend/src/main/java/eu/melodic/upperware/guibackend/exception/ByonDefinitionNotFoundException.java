package eu.melodic.upperware.guibackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ByonDefinitionNotFoundException extends RuntimeException {
    public ByonDefinitionNotFoundException(long byonDefinitionId) {
        super(String.format("Byon definition with id = %d not found", byonDefinitionId));
    }
}
