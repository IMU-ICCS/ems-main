package eu.melodic.cache.impl;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import eu.melodic.cache.properties.CacheProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.TRUE;

/**
 * Created by pszkup on 04.01.18.
 */

@Slf4j
@Primary
@Qualifier("memcacheService")
@Service
@AllArgsConstructor
public class MemcacheServiceImpl implements CacheService<NodeCandidates> {

    private CacheProperties cacheProperties;
    private MemcachedClient memcachedClient;

    @Override
    public void store(String key, NodeCandidates value) throws CacheException {
        Integer storeExp = cacheProperties.getCache().getTtl();
        // adding a new key
        OperationFuture<Boolean> result = memcachedClient.add(key, storeExp, value);

        Boolean boolResult;
        try {
            boolResult = result.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Problem during storing value under key: {} ", key);
            throw new CacheException(String.format("Problem during storing value under key: %s ", key), e);
        }

        if (!TRUE.equals(boolResult)){
            log.error("Problem during storing value under key: {} ", key);
        } else {
            log.info("Successfully stored value under key: {} ", key);
        }
    }

    @Override
    public NodeCandidates load(String key) {
        int currentTryCount = 1;
        int maxTryCount = 4;
        int timeToWait=2;

        NodeCandidates nodeCandidates = null;
        while (currentTryCount < maxTryCount) {
            try {
                nodeCandidates = (NodeCandidates) memcachedClient.get(key);
                currentTryCount = maxTryCount;
            } catch(CancellationException e){
                log.warn("Attempt {} of {} failed. Next attempt after {} second", currentTryCount, maxTryCount-1, timeToWait, e);
                currentTryCount++;
                if (currentTryCount == maxTryCount) {
                    throw e;
                }
                try {
                    Thread.sleep(timeToWait);
                } catch (InterruptedException ie) {
                    //nothing to do
                }
            }
        }
        return nodeCandidates;
    }

}
