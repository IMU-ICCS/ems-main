package eu.paasage.upperware.security.server.controller;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.server.controller.request.ChangePasswordRequest;
import eu.paasage.upperware.security.server.controller.response.ExceptionResponse;
import eu.paasage.upperware.security.server.controller.response.UserLoginResponse;
import eu.paasage.upperware.security.server.data.service.UserService;
import eu.paasage.upperware.security.server.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class UserController {

    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserRequest userRequest, HttpServletResponse response)
            throws UserNotFoundException {
        log.info("Login request for user with username: {}", userRequest.getUsername());
        userService.authenticate(userRequest.getUsername(), userRequest.getPassword());
        String token = userService.createToken(userRequest.getUsername());
        response.setHeader(SecurityConstants.HEADER_STRING, token);
        return new UserLoginResponse(userRequest.getUsername());
    }

    @PostMapping("/user/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody UserRequest userRequest) {
        log.info("Sign-up request for username: {}", userRequest.getUsername());
        if (!userService.exists(userRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is used, please try with another username");
        }
        userService.create(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PutMapping("/user/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        log.info("PUT request for change password from user: {}", changePasswordRequest.getUsername());
        userService.changePassword(changePasswordRequest);
        log.info("Password for user: {} successfully changed", changePasswordRequest.getUsername());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ExceptionResponse response = new ExceptionResponse(defaultMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
