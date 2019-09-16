package eu.paasage.upperware.security.server.controller.response;

import eu.paasage.upperware.security.server.data.repository.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String username;

    private UserRole userRole;
}
