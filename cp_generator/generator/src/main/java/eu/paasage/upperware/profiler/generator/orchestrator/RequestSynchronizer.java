package eu.paasage.upperware.profiler.generator.orchestrator;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Component
public class RequestSynchronizer {

    private final Map<String, Object> LOCKS = Maps.newConcurrentMap();

    public void acquireLock(String resourceName) {
        synchronized (LOCKS) {
            log.info("Acquiring lock for application {}", resourceName);
            Object obj = LOCKS.get(resourceName);
            if (obj != null) {
                throw new IllegalStateException(format("(Re)configuration process for %s is running. " +
                        "Wait until it is completed and repeat request.", resourceName));
            }
            LOCKS.put(resourceName, Boolean.TRUE);
        }
    }

    public void releaseLock(String resourceName) {
        log.info("Releasing lock for application {}", resourceName);
        LOCKS.remove(resourceName);
    }

}
