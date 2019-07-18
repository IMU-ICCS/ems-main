package eu.melodic.upperware.guibackend.communication.jwt.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtExceptionResponse {
    private String message;
}
