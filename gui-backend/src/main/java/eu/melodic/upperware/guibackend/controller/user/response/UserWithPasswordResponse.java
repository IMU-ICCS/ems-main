package eu.melodic.upperware.guibackend.controller.user.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithPasswordResponse extends UserResponse {
    private String password;

    @Builder
    public UserWithPasswordResponse(UserResponse userResponse, String password) {
        super(userResponse.getUsername(), userResponse.getUserRole());
        this.password = password;
    }
}
