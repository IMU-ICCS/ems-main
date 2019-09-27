package eu.paasage.upperware.security.server.controller;

import eu.paasage.upperware.security.server.controller.response.ExceptionResponse;
import eu.paasage.upperware.security.server.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final String UNAUTHORIZED_MESSAGE = "Invalid credentials";
    private static final String USER_NOT_FOUND_MESSAGE = "Such user does not exist";

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException() {
        ExceptionResponse response = new ExceptionResponse(UNAUTHORIZED_MESSAGE);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException() {
        ExceptionResponse response = new ExceptionResponse(USER_NOT_FOUND_MESSAGE);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
