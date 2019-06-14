package eu.passage.upperware.commons.cloudiator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.cloudiator-client.properties")
public class CloudiatorProperties {

    @Valid
    @NotNull
    private Cloudiator cloudiator;

    @Getter
    @Setter
    public static class Cloudiator {

        @NotBlank
        private String url;

        @NotBlank
        private String apiKey;

        private int httpReadTimeout = 30000;

        private int delayBetweenQueueCheck = 1000;
    }

}
