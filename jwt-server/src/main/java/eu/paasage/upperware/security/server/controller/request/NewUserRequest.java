package eu.paasage.upperware.security.server.controller.request;

import eu.paasage.upperware.security.server.data.repository.UserRole;
import eu.paasage.upperware.security.server.data.validator.FieldMatch;
import eu.paasage.upperware.security.server.data.validator.UniqueUsername;
import eu.paasage.upperware.security.server.data.validator.ValidPassword;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(message = "Password and password confirmation are not equal", first = "password", second = "passwordConfirmation")
public class NewUserRequest {

    @UniqueUsername()
    private String username;

    @ValidPassword()
    private String password;

    private String passwordConfirmation;

    private UserRole userRole;
}
