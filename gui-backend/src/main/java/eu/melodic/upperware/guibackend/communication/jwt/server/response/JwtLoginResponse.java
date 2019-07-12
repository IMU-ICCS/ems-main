package eu.melodic.upperware.guibackend.communication.jwt.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginResponse {
    private String username;
}
