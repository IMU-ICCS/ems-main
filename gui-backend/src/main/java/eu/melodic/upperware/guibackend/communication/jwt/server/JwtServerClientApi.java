package eu.melodic.upperware.guibackend.communication.jwt.server;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.melodic.upperware.guibackend.communication.commons.RestCommunicationService;
import eu.melodic.upperware.guibackend.communication.commons.ServiceName;
import eu.melodic.upperware.guibackend.communication.jwt.server.response.JwtLoginResponse;
import eu.melodic.upperware.guibackend.controller.user.request.ChangePasswordRequest;
import eu.melodic.upperware.guibackend.controller.user.request.NewUserRequest;
import eu.melodic.upperware.guibackend.controller.user.response.LoginResponse;
import eu.melodic.upperware.guibackend.controller.user.response.UserResponse;
import eu.melodic.upperware.guibackend.model.user.User;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class JwtServerClientApi extends RestCommunicationService implements JwtServerApi {

    private GuiBackendProperties guiBackendProperties;
    private final static String REFRESH_HEADER = "Refresh";

    public JwtServerClientApi(RestTemplate restTemplate, GuiBackendProperties guiBackendProperties) {
        super(restTemplate);
        this.guiBackendProperties = guiBackendProperties;
    }

    @Override
    public LoginResponse login(UserRequest userRequest) {
        String requestUrl = "http://" + guiBackendProperties.getJwtServer().getUrl() + "/user/login";
        ParameterizedTypeReference<JwtLoginResponse> responseType = new ParameterizedTypeReference<JwtLoginResponse>() {
        };
        HttpEntity<UserRequest> requestHttpEntity = new HttpEntity<>(userRequest);
        ResponseEntity<JwtLoginResponse> response = getResponse(requestUrl, responseType, requestHttpEntity, ServiceName.JWT_SERVER.name, HttpMethod.POST);
        List<String> authorizationHeader = response.getHeaders().get(HttpHeaders.AUTHORIZATION);
        List<String> refreshTokenHeader = response.getHeaders().get(REFRESH_HEADER);
        String authorizationToken, username, refreshToken;
        if (CollectionUtils.isNotEmpty(authorizationHeader)
                && CollectionUtils.isNotEmpty(refreshTokenHeader)
                && response.getBody() != null) {
            authorizationToken = authorizationHeader.get(0);
            refreshToken = refreshTokenHeader.get(0);
            username = response.getBody().getUsername();
            log.info("Successful login for user: {}", username);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed authorization");

        }
        return LoginResponse.builder()
                .username(username)
                .token(authorizationToken)
                .refreshToken(refreshToken)
                .userRole(response.getBody().getUserRole())
                .build();
    }

    @Override
    public UserResponse createNewUser(NewUserRequest newUserRequest, String token) {
        String requestUrl = "http://" + guiBackendProperties.getJwtServer().getUrl() + "/auth/user";
        ParameterizedTypeReference<UserResponse> responseType = new ParameterizedTypeReference<UserResponse>() {
        };
        HttpEntity<NewUserRequest> requestHttpEntity = createHttpEntityWithAuthorizationHeader(newUserRequest, token);
        ResponseEntity<UserResponse> response = getResponse(requestUrl, responseType, requestHttpEntity, ServiceName.JWT_SERVER.name, HttpMethod.POST);
        return response.getBody();
    }

    @Override
    public void deleteUser(String username, String token) {
        String requestUrl = "http://" + guiBackendProperties.getJwtServer().getUrl() + "/auth/user/" + username;
        ParameterizedTypeReference<Void> responseType = new ParameterizedTypeReference<Void>() {
        };
        HttpEntity<Void> requestHttpEntity = createEmptyHttpEntityWithAuthorizationHeader(token);
        getResponse(requestUrl, responseType, requestHttpEntity, ServiceName.JWT_SERVER.name, HttpMethod.DELETE);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, String token) {
        String requestUrl = "http://" + guiBackendProperties.getJwtServer().getUrl() + "/auth/user/password";
        ParameterizedTypeReference<Void> responseType = new ParameterizedTypeReference<Void>() {
        };
        HttpEntity<ChangePasswordRequest> requestHttpEntity = createHttpEntityWithAuthorizationHeader(changePasswordRequest, token);
        getResponse(requestUrl, responseType, requestHttpEntity, ServiceName.JWT_SERVER.name, HttpMethod.PUT);
    }

    private <T> HttpEntity<T> createHttpEntityWithAuthorizationHeader(T request, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        return new HttpEntity<>(request, headers);
    }

    @Override
    public List<User> getUsers(String token) {
        String requestUrl = "http://" + guiBackendProperties.getJwtServer().getUrl() + "/auth/user";
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {
        };
        HttpEntity<Void> requestHttpEntity = createEmptyHttpEntityWithAuthorizationHeader(token);
        return getResponse(requestUrl, responseType, requestHttpEntity, ServiceName.JWT_SERVER.name, HttpMethod.GET)
                .getBody();
    }

    @Override
    public void unlockUserAccount(String username, String token) {
        String requestUrl = "http://" + guiBackendProperties.getJwtServer().getUrl() + "/auth/user/unlock/" + username;
        ParameterizedTypeReference<Void> responseType = new ParameterizedTypeReference<Void>() {
        };
        HttpEntity<Void> requestHttpEntity = createEmptyHttpEntityWithAuthorizationHeader(token);
        getResponse(requestUrl, responseType, requestHttpEntity, ServiceName.JWT_SERVER.name, HttpMethod.PUT);
    }

    private HttpEntity<Void> createEmptyHttpEntityWithAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
