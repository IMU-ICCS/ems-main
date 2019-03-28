package eu.melodic.upperware.guibackend;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationContext {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
