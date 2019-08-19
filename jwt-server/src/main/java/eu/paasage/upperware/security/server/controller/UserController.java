package eu.paasage.upperware.security.server.controller;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.server.controller.request.ChangePasswordRequest;
import eu.paasage.upperware.security.server.controller.request.NewUserRequest;
import eu.paasage.upperware.security.server.controller.response.ExceptionResponse;
import eu.paasage.upperware.security.server.controller.response.UserResponse;
import eu.paasage.upperware.security.server.data.repository.User;
import eu.paasage.upperware.security.server.data.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class UserController {

    private UserService userService;

    @PostMapping("/user/login")
    public UserResponse login(@RequestBody UserRequest userRequest, HttpServletResponse response)
            throws AuthenticationException {
        log.info("Login request for user with username: {}", userRequest.getUsername());
        if (userService.authenticate(userRequest.getUsername(), userRequest.getPassword())) {
            String token = userService.createToken(userRequest.getUsername());
            response.setHeader(SecurityConstants.HEADER_STRING, token);
            return userService.createUserResponse(userRequest.getUsername());
        } else {
            throw new AuthenticationException();
        }
    }

    @PostMapping("/auth/user")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@PermissionComponent.isUserInAdminGroup(authentication.name)")
    public UserResponse signUp(@RequestBody @Valid NewUserRequest userRequest) {
        log.info("Sign-up request with username: {} and role: {}", userRequest.getUsername(), userRequest.getUserRole());
        UserResponse userResponse = userService.create(userRequest);
        log.info("New user account for user {} with role {} successfully created", userRequest.getUsername(), userRequest.getUserRole());
        return userResponse;
    }

    @PutMapping("/auth/user/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#changePasswordRequest.username.equals(authentication.name)")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest)
            throws AuthenticationException {
        log.info("PUT request for change password from user: {}", changePasswordRequest.getUsername());
        userService.changePassword(changePasswordRequest);
        log.info("Password for user: {} successfully changed", changePasswordRequest.getUsername());
    }

    @PutMapping("/auth/user/unlock/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@PermissionComponent.isUserInAdminGroup(authentication.name)")
    public void unlockUserAccount(@PathVariable("username") String username) {
        log.info("PUT request for unlock account for user: {}", username);
        userService.unlockAccount(username);
        log.info("Account for user: {} successfully unlocked", username);
    }

    @GetMapping("/auth/user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@PermissionComponent.isUserInAdminGroup(authentication.name)")
    public List<User> getUsersList(Authentication authentication) {
        String requesterName = authentication.getName();
        log.info("GET request for all users list from user: {}", requesterName);
        return userService.getUsersList();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ExceptionResponse response = new ExceptionResponse(defaultMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
