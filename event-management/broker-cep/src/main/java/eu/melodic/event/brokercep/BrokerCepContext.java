package eu.melodic.event.brokercep;

import eu.passage.upperware.commons.passwords.IdentityPasswordEncoder;
import eu.passage.upperware.commons.passwords.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerCepContext {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new IdentityPasswordEncoder();
    }

}
