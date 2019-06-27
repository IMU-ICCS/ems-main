package eu.melodic.upperware.guibackend.controller.user;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;
import eu.melodic.upperware.guibackend.service.user.UserService;
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

    private UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse loginUser(@RequestBody UserRequest loginRequest) {
        log.info("POST login request for user: {}", loginRequest.getUsername());
        return userService.login(loginRequest);
    }
}
