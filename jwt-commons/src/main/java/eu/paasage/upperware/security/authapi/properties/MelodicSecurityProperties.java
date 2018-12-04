package eu.paasage.upperware.security.authapi.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by pszkup on 04.01.18.
 */

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.security.properties")
public class MelodicSecurityProperties {

    @Valid
    @NotNull
    private Jwt jwt;

    @Getter
    @Setter
    public static class Jwt {
        @NotBlank
        private String secret;

        @NotNull
        private Long expirationTime;
    }
}