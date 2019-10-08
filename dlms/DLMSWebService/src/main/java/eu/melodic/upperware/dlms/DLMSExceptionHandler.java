package eu.melodic.upperware.dlms;

import eu.melodic.upperware.dlms.exception.AcNameNotFoundException;
import eu.melodic.upperware.dlms.exception.DLMSException;
import eu.melodic.upperware.dlms.exception.IdNotFoundException;
import eu.melodic.upperware.dlms.exception.NameNotFoundException;
import io.github.cloudiator.rest.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DLMSExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleApiException(ApiException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DLMSException.class)
    public ResponseEntity handleDLMSException(DLMSException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AcNameNotFoundException.class)
    public ResponseEntity handleAcNameNotFoundException(AcNameNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity handleNameNotFoundException(NameNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity handleIdNotFoundException(IdNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
