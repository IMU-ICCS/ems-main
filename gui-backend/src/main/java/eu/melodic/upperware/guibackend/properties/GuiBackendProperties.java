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
    private Mule mule;

    @Valid
    private ProviderProperties aws;

    @Valid
    private ProviderProperties openStack;


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
        private String host;

        @NotBlank
        private String port;
    }

    @Getter
    @Setter
    public static class ProviderProperties {

        private String endpoint;

        @NotBlank
        private String user;

        @NotBlank
        private String secret;

        @NotBlank
        private String nodeGroup;

    }
}
