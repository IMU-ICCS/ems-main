package eu.melodic.upperware.guibackend.controller.user.response;


import eu.melodic.upperware.guibackend.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private UserRole userRole;
    private String mail;
    private String fullName;

    public UserResponse(String username, UserRole userRole) {
        this.username = username;
        this.userRole = userRole;
    }
}
