package eu.melodic.upperware.guibackend.controller.user.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String token;
}
