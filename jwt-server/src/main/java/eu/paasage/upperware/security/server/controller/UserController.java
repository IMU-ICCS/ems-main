package eu.paasage.upperware.security.server.controller;

import eu.melodic.models.interfaces.security.InvalidateTokenRequest;
import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.server.controller.request.ChangePasswordRequest;
import eu.paasage.upperware.security.server.controller.response.ExceptionResponse;
import eu.paasage.upperware.security.server.controller.response.UserLoginResponse;
import eu.paasage.upperware.security.server.data.repository.RefreshToken;
import eu.paasage.upperware.security.server.data.repository.User;
import eu.paasage.upperware.security.server.data.service.RefreshTokenService;
import eu.paasage.upperware.security.server.data.service.UserService;
import eu.paasage.upperware.security.server.exception.RefreshTokenInvalidException;
import eu.paasage.upperware.security.server.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private RefreshTokenService refreshTokenService;

    @PostMapping("/user/login")
    public UserLoginResponse login(@RequestBody UserRequest userRequest, HttpServletResponse response)
            throws AuthenticationException {
        String username = userRequest.getUsername();
        log.info("Login request for user with username: {}", username);
        if (userService.authenticate(username, userRequest.getPassword())) {
            String token = userService.createToken(username);
            String refreshToken = refreshTokenService.createToken(username);
            response.setHeader(SecurityConstants.HEADER_STRING, token);
            response.setHeader(SecurityConstants.REFRESH_HEADER_STRING, refreshToken);
            return new UserLoginResponse(username);
        } else {
            throw new AuthenticationException();
        }


    }

    @PostMapping("/auth/user/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody UserRequest userRequest) {
        log.info("Sign-up request for username: {}", userRequest.getUsername());
        if (!userService.exists(userRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is used, please try with another username");
        }
        userService.create(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PutMapping("/auth/user/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest)
            throws AuthenticationException {
        log.info("PUT request for change password from user: {}", changePasswordRequest.getUsername());
        userService.changePassword(changePasswordRequest);
        log.info("Password for user: {} successfully changed", changePasswordRequest.getUsername());
    }

    @PutMapping("/auth/user/unlock/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlockUserAccount(@PathVariable("username") String username) {
        log.info("PUT request for unlock account for user: {}", username);
        userService.unlockAccount(username);
        log.info("Account for user: {} successfully unlocked", username);
    }

    @GetMapping("/auth/user")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersList() {
        log.info("GET request for all users list");
        return userService.getUsersList();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ExceptionResponse response = new ExceptionResponse(defaultMessage);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION)
            String authorization, HttpServletResponse response)
            throws UserNotFoundException, RefreshTokenInvalidException {

        log.info("Refresh token request");
        RefreshToken refreshToken;

        try {
            refreshToken = refreshTokenService.validateToken(authorization);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException ex) {
            log.error("Error during validating token: " + ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (RefreshTokenInvalidException ex) {
            log.error("Error during validating token: " + ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }

        log.info("Refresh token has been accepted");

        String username = refreshToken.getUsername();
        String token = userService.createToken(username);
        String newRefreshToken = refreshTokenService.createToken(username);
        refreshTokenService.useToken(refreshToken);

        response.setHeader(SecurityConstants.HEADER_STRING, token);
        response.setHeader(SecurityConstants.REFRESH_HEADER_STRING, newRefreshToken);
        return ResponseEntity.status(HttpStatus.CREATED).body("New tokens have been created.");
    }

    @PostMapping(value = "/auth/invalidate-token")
    public ResponseEntity<Object> invalidateToken(@RequestBody InvalidateTokenRequest invalidateTokenRequest) {

        log.info("Invalidate refresh token request");
        try {
            refreshTokenService.invalidateToken(invalidateTokenRequest.getToken());
        } catch (IllegalStateException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException ex) {
            log.error("Error during invalidating refresh token: "+ ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        log.debug("Token has been invalidated successfully .");
        return ResponseEntity.status(HttpStatus.OK).body("Token has been invalidated successfully.");

    }
}