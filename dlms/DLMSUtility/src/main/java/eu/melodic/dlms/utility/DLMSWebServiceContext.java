package eu.melodic.dlms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class DLMSWebServiceContext {
    @Bean
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }
}
