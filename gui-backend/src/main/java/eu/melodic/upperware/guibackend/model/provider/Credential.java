package eu.melodic.upperware.guibackend.model.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credential {

    private long id;

    private String user;

    private String secret;
}
