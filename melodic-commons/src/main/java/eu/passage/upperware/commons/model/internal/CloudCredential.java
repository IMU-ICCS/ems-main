package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
public class CloudCredential implements Serializable {
    @JsonProperty("user")
    private String user;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("domain")
    private String domain;
}
