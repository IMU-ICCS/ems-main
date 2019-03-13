package eu.melodic.upperware.solvertodeployment.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.solverToDeployment.properties")
public class SolverToDeploymentProperties {

    @Valid
    @NotNull
    private Esb esb;

    @Valid
    @NotNull
    private Server server;

    @Getter
    @Setter
    public static class Esb {
        @NotBlank
        private String url;
    }

    @Getter
    @Setter
    public static class Server {
        @NotBlank
        private String port;
    }

}