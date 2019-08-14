package eu.paasage.upperware.security.server.controller.request;


import eu.paasage.upperware.security.server.data.validator.FieldMatch;
import eu.paasage.upperware.security.server.data.validator.ValidPassword;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(message = "New password and new password confirmation are not equal", first = "newPassword", second = "newPasswordConfirmation")
public class ChangePasswordRequest {
    private String username;
    private String oldPassword;

    @ValidPassword()
    private String newPassword;

    private String newPasswordConfirmation;
}
