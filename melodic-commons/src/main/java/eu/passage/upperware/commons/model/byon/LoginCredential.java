package eu.passage.upperware.commons.model.byon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredential extends io.github.cloudiator.rest.model.LoginCredential {
    private long id;
}
