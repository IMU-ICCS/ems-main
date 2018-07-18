package eu.paasage.upperware.security.server.controller;

import eu.paasage.upperware.security.server.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SecurityException.class})
    public ResponseEntity<Object> handleSecurityException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Invalid credentials", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Invalid credentials", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}