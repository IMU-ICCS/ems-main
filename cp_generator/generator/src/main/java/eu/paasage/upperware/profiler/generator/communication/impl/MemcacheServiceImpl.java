package eu.paasage.upperware.profiler.generator.communication.impl;

import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.properties.GeneratorProperties;
import eu.paasage.upperware.profiler.generator.communication.CacheService;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.TRUE;

@Slf4j
@Service
public class MemcacheServiceImpl implements CacheService {

    private GeneratorProperties generatorProperties;
    private MemcachedClient memcachedClient = null;

    public MemcacheServiceImpl(@Autowired GeneratorProperties generatorProperties) throws GeneratorException {
        this.generatorProperties = generatorProperties;

        String host = generatorProperties.getMemcache().getHost();
        Integer port = generatorProperties.getMemcache().getPort();

        log.info("Connecting to memcache under: {}:{}", host, port);
        try {
            memcachedClient = new MemcachedClient(new InetSocketAddress(host, port));
        } catch (IOException e) {
            log.error("Problem during connecting to memcache: {}:{}", host, port, e);
            throw new GeneratorException(String.format("Problem during connecting to memcache: %s:%s", host, port), e);
        }
    }


    @Override
    public void store(String key, Object value) throws GeneratorException {
        Integer storeExp = generatorProperties.getMemcache().getTtl();
        // adding a new key
        OperationFuture<Boolean> result = memcachedClient.add(key, storeExp, value);

        Boolean boolResult;
        try {
            boolResult = result.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Problem during storing value under key: {} ", key);
            throw new GeneratorException(String.format("Problem during storing value under key: %s ", key), e);
        }

        if (!TRUE.equals(boolResult)){
            log.error("Problem during storing value under key: {} ", key);
        } else {
            log.info("Successfully stored value under key: {} ", key);
        }
    }

    @Override
    public Object load(String key, Object value) {
        return memcachedClient.get(key);
    }
}
