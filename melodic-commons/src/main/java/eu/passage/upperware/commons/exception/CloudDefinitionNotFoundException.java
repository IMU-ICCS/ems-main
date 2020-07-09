package eu.passage.upperware.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CloudDefinitionNotFoundException extends RuntimeException {
    public CloudDefinitionNotFoundException(long cloudDefinitionId) {
        super(String.format("Cloud definition with id = %d not found", cloudDefinitionId));
    }
}
