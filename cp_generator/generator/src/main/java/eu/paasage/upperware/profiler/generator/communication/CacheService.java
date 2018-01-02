package eu.paasage.upperware.profiler.generator.communication;

import eu.paasage.upperware.profiler.generator.error.GeneratorException;

public interface CacheService {

    void store(String key, Object value) throws GeneratorException;

    Object load(String key, Object value);

}
