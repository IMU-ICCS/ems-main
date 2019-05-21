package eu.melodic.upperware.guibackend.exception.controller;

import eu.melodic.upperware.guibackend.exception.CamundaErrorVariableException;
import eu.melodic.upperware.guibackend.exception.response.CamundaVariableErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GuiExceptionHandler {

    @ExceptionHandler(CamundaErrorVariableException.class)
    public ResponseEntity<CamundaVariableErrorResponse> handleCamundaErrorVariableException(CamundaErrorVariableException ex) {
        CamundaVariableErrorResponse response = CamundaVariableErrorResponse.builder()
                .message(ex.getMessage())
                .variableName(ex.getInvalidVariableName())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
