package eu.melodic.cache.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * Created by pszkup on 04.01.18.
 */

@Getter
@Setter
@Validated
@Configuration
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.cache.properties")
public class CacheProperties {

    private Cache cache;

    @Getter
    @Setter
    public static class Cache {
        private String host;
        private Integer port;
        private Integer ttl;
    }
}
