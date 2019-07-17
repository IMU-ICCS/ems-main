package eu.paasage.upperware.security.server.controller;

import eu.melodic.models.interfaces.security.InvalidateTokenRequest;
import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.server.controller.response.UserLoginResponse;
import eu.paasage.upperware.security.server.data.repository.RefreshToken;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private UserService userService;
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserRequest userRequest, HttpServletResponse response)
            throws UserNotFoundException {
        String username = userRequest.getUsername();
        log.info("Login request for user with username: {}", username);
        userService.authenticate(username, userRequest.getPassword());

        String token = userService.createToken(username);
        String refreshToken = refreshTokenService.createToken(username);

        response.setHeader(SecurityConstants.HEADER_STRING, token);
        response.setHeader(SecurityConstants.REFRESH_HEADER_STRING, refreshToken);
        return new UserLoginResponse(username);
    }

    @PostMapping("/users/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody UserRequest userRequest) {
        log.info("Sign-up request for username: {}", userRequest.getUsername());
        if (!userService.exists(userRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is used, please try with another username");
        }
        userService.create(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
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
        return ResponseEntity.status(HttpStatus.CREATED).body("New tokens created.");
    }

    @PostMapping(value = "/invalidate-token")
    public ResponseEntity<Object> invalidateToken(@RequestBody InvalidateTokenRequest invalidateTokenRequest) {

        try {
            refreshTokenService.invalidateToken(invalidateTokenRequest.getToken());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Token has been invalidated");

    }
}