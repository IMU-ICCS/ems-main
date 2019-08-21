package eu.melodic.upperware.guibackend.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    @JsonIgnore
    private String password;

    private UserRole userRole;

    private boolean lockedAccount;
}
