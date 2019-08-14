package eu.melodic.upperware.guibackend.controller.user.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
