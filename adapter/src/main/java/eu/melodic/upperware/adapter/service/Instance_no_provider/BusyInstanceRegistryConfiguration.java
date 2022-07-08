package eu.melodic.upperware.adapter.service.Instance_no_provider;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BusyInstanceRegistryConfiguration {

    @Bean
    public ConcurrentHashMap<String, List<Integer>> busyInstancesByComponentName() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentHashMap<String, List<Integer>> idleInstancesByComponentName() {
        return new ConcurrentHashMap<>();
    }
}
