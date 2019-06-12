package eu.melodic.upperware.guibackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@AllArgsConstructor
@Getter
public class CamundaErrorVariableException extends RuntimeException {

    private String invalidVariableName;
    private String message;

}
