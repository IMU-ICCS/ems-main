package eu.melodic.upperware.guibackend.service.user;


import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.communication.jwt.server.JwtServerClientApi;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private JwtServerClientApi jwtServerClientApi;

    public LoginResponse login(UserRequest loginRequest) {
        return jwtServerClientApi.login(loginRequest);
    }
}
