package eu.melodic.upperware.guibackend.communication.jwt.server;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.controller.user.request.ChangePasswordRequest;
import eu.melodic.upperware.guibackend.controller.user.request.NewUserRequest;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;
import eu.melodic.upperware.guibackend.controller.user.response.UserResponse;
import eu.melodic.upperware.guibackend.model.user.User;

import java.util.List;

public interface JwtServerApi {

    LoginResponse login(UserRequest userRequest);

    UserResponse createNewUser(NewUserRequest newUserRequest, String token);

    void changePassword(ChangePasswordRequest changePasswordRequest, String token);

    List<User> getUsers(String token);

    void unlockUserAccount(String username, String token);
}
