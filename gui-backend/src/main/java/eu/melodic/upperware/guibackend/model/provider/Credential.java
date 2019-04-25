package eu.melodic.upperware.guibackend.model.provider;

import lombok.Value;

@Value
public class Credential {

    private Long id;

    private String user;

    private String secret;
}
