package eu.paasage.upperware.security.server.controller.request;

import eu.paasage.upperware.security.server.data.validator.UniqueUsername;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExistingUserRequest {

    @UniqueUsername()
    private String username;

    private String fullName;

    private String mail;

}
