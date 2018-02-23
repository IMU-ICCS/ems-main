package eu.melodic.cache;

import eu.melodic.cache.exception.CacheException;

/**
 * Created by pszkup on 04.01.18.
 */
public interface CacheService <T>{

    void store(String key, T value) throws CacheException;

    T load(String key);

}
