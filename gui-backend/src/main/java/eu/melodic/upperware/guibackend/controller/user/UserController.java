package eu.melodic.upperware.guibackend.controller.user;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.communication.jwt.server.JwtServerClientApi;
import eu.melodic.upperware.guibackend.controller.user.request.ChangePasswordRequest;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;
import eu.melodic.upperware.guibackend.model.user.User;
import eu.melodic.upperware.guibackend.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private JwtServerClientApi jwtServerClientApi;

    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse loginUser(@RequestBody UserRequest loginRequest) {
        log.info("POST login request for user: {}", loginRequest.getUsername());
        return jwtServerClientApi.login(loginRequest);
    }

    @PutMapping("/auth/user/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestHeader(value = "Authorization") String token,
                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        log.info("PUT request for change password for user: {}", changePasswordRequest.getUsername());
        jwtServerClientApi.changePassword(changePasswordRequest, token);
    }

    @GetMapping("/auth/user/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAdminList(@RequestHeader(value = "Authorization") String token,
                                   Authentication authentication) {
        log.info("GET request for admin list from user: {}", authentication.getName());
        return jwtServerClientApi.getUsers(token)
                .stream()
                .filter(user -> UserRole.ADMIN.equals(user.getUserRole()))
                .collect(Collectors.toList());
    }

    @GetMapping("/auth/user/common")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getCommonUserList(@RequestHeader(value = "Authorization") String token) {
        log.info("GET request for common user list");
        return jwtServerClientApi.getUsers(token)
                .stream()
                .filter(user -> UserRole.USER.equals(user.getUserRole()))
                .collect(Collectors.toList());
    }

    @PutMapping("/auth/user/unlock/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlockUserAccount(@RequestHeader(value = "Authorization") String token,
                                  @PathVariable(value = "username") String username) {
        log.info("PUT request for unlock user {} account", username);
        jwtServerClientApi.unlockUserAccount(username, token);
        log.info("Account for user {} successfully unlock", username);
    }

}
