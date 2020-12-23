package eu.paasage.upperware.profiler.generator.properties;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.generator.properties")
public class GeneratorProperties {

    @Valid
    @NotNull
    private Esb esb;

    @Valid
    @NotNull
    private CloudiatorV2 cloudiatorV2;

    @Valid
    @NotNull
    private ProActive paRest;

    @Getter
    @Setter
    public static class Esb {

        @NotBlank
        private String url;
    }

    @Getter
    @Setter
    public static class CloudiatorV2 {

        @NotBlank
        private String url;

        @NotBlank
        private String apiKey;

        private int httpReadTimeout = 30000;
    }

    @Getter
    @Setter
    @ToString
    public static class ProActive {
        @NotNull
        private String url;
        @NotNull
        private String login;
        @NotNull
        private String password;
    }
}
