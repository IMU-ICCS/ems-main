package eu.paasage.upperware.security.server.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginInResponse {
    private String username;
    private String token;
}
