package eu.melodic.upperware.guibackend.controller.user;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.communication.jwt.server.JwtServerClientApi;
import eu.melodic.upperware.guibackend.controller.user.request.ChangePasswordRequest;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private JwtServerClientApi jwtServerClientApi;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse loginUser(@RequestBody UserRequest loginRequest) {
        log.info("POST login request for user: {}", loginRequest.getUsername());
        return jwtServerClientApi.login(loginRequest);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestHeader(value = "Authorization") String token,
                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        log.info("PUT request for change password for user: {}", changePasswordRequest.getUsername());
        jwtServerClientApi.changePassword(changePasswordRequest, token);
    }

}
