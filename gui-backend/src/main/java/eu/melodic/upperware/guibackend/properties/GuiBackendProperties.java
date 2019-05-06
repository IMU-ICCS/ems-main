package eu.melodic.upperware.guibackend.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.guiBackend.properties")
public class GuiBackendProperties {

    @Valid
    @NotNull
    private Cloudiator cloudiator;

    @Valid
    @NotNull
    private Mule esb;

    @Valid
    @NotNull
    private Camunda camunda;

    @Getter
    @Setter
    public static class Cloudiator {

        @NotBlank
        private String url;

        @NotBlank
        private String apiKey;
    }

    @Getter
    @Setter
    public static class Mule {

        @NotBlank
        private String url;

        @NotNull
        private boolean sslVerificationEnabled;
    }

    @Getter
    @Setter
    public static class Camunda {

        @NotBlank
        private String host;

        @NotBlank
        private String port;
    }
}
