package eu.melodic.upperware.guibackend.communication.jwt.server;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;

public interface JwtServerApi {

    LoginResponse login(UserRequest userRequest);
}
