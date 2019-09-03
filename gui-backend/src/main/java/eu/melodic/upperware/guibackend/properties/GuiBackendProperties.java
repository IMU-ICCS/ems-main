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
    private Mule esb;

    @Valid
    @NotNull
    private ExternalService camunda;

    @Valid
    @NotNull
    private CdoUploader cdoUploader;

    @Valid
    @NotNull
    private ExternalService jwtServer;

    @Valid
    @NotNull
    private ExternalService adapter;

    @Getter
    @Setter
    public static class Mule extends ExternalService {

        @NotNull
        private boolean sslVerificationEnabled;
    }

    @Getter
    @Setter
    public static class CdoUploader {

        private boolean validationEnabled;
    }

    @Getter
    @Setter
    public static class ExternalService {

        @NotBlank
        private String url;
    }
}
