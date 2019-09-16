package eu.melodic.upperware.guibackend.controller.user.response;

import eu.melodic.upperware.guibackend.model.user.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends UserResponse {
    private String token;
    private String refreshToken;

    @Builder
    public LoginResponse(String username, UserRole userRole, String token, String refreshToken) {
        super(username, userRole);
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
