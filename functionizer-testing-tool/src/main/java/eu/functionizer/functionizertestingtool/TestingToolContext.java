package eu.functionizer.functionizertestingtool;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestingToolContext {

    @Bean
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }
}
