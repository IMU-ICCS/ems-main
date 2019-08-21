package eu.melodic.upperware.guibackend.controller.user.response;

import eu.melodic.upperware.guibackend.model.user.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends UserResponse {
    private String token;

    @Builder
    public LoginResponse(String username, UserRole userRole, String token) {
        super(username, userRole);
        this.token = token;
    }
}
