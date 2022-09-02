package eu.melodic.upperware.guibackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EdgeDefinitionNotFoundException extends RuntimeException {
    public EdgeDefinitionNotFoundException(long edgeDefinitionId) {
        super(String.format("Edge definition with id = %d not found", edgeDefinitionId));
    }
}

