package eu.melodic.upperware.guibackend.controller.user.response;

import eu.melodic.upperware.guibackend.model.user.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String token;
    private UserRole userRole;
}
