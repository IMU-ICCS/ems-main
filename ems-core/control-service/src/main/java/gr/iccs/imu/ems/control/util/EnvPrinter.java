package gr.iccs.imu.ems.control.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvPrinter implements InitializingBean {
    private final ConfigurableEnvironment env;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("EnvPrinter initialized: {}", env);
        printAllProperties();
    }

    public void printAllProperties() {
        log.debug("printAllProperties: START");
        log.debug("--------------------------------------------------------");
        log.debug(" Properties per source");
        env.getPropertySources().forEach(ps -> {
            if (ps instanceof org.springframework.core.env.EnumerablePropertySource<?> eps) {
                log.debug("--------------------------------------------------------");
                log.debug("  Source: {}", eps.getName());
                for (String key : eps.getPropertyNames()) {
                    Object value = eps.getProperty(key);
                    System.out.println(key + " = " + value);
                    log.debug("    {}: {}", key, value);
                }
            }
        });
        log.debug("--------------------------------------------------------");

        log.debug("--------------------------------------------------------");
        log.debug(" Properties (merged)");
        log.debug("--------------------------------------------------------");
        TreeMap<String, Object> finalProps = new TreeMap<>();
        for (PropertySource<?> ps : env.getPropertySources()) {
            if (ps instanceof EnumerablePropertySource<?> eps) {
                for (String key : eps.getPropertyNames()) {
                    finalProps.put(key, env.getProperty(key)); // resolved value
                }
            }
        }
        finalProps.forEach((k,v) -> log.debug("  - {}: {}", k, v));
        log.debug("--------------------------------------------------------");
        log.debug("printAllProperties: END");
    }
}