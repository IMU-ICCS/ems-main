package eu.paasage.upperware.security.server.controller.response;

import eu.paasage.upperware.security.server.data.repository.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserLoginResponse {
    private String username;
    private UserRole userRole;
}
