package eu.melodic.upperware.guibackend.exception.controller;

import eu.melodic.upperware.guibackend.exception.CamundaErrorVariableException;
import eu.passage.upperware.commons.exception.SecureVariableNotFoundException;
import eu.melodic.upperware.guibackend.exception.response.CamundaVariableErrorResponse;
import eu.melodic.upperware.guibackend.exception.response.MissingSecureVariableErrorResponse;
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
                .errorName(CamundaErrorVariableException.class.getSimpleName())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecureVariableNotFoundException.class)
    public ResponseEntity<MissingSecureVariableErrorResponse> handleSecureVariableNotFoundException(SecureVariableNotFoundException ex) {
        MissingSecureVariableErrorResponse response = MissingSecureVariableErrorResponse.builder()
                .message(ex.getMessage())
                .missingVariableName(ex.getMissingVariableName())
                .errorName(SecureVariableNotFoundException.class.getSimpleName())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
