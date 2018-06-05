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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
        OperationFuture<Boolean> result = memcachedClient.set(key, storeExp, value);

        while (!result.isDone()){
            log.info("Waiting for result of storing value under key: {} ", key);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            Boolean boolResult = result.get();
            if (TRUE.equals(boolResult)) {
                log.info("Successfully stored value under key: {} ", key);
            } else {
                log.error("Problem during storing value under key: {} ", key);
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            log.error("Problem during storing value under key: {} ", key);
            throw new CacheException(String.format("Problem during storing value under key: %s ", key), e);
        }
    }

    @Override
    public NodeCandidates load(String key) {
        // Try to get a value, for up to 5 seconds, and cancel if it
        // doesn't return
        int currentTryCount = 0;
        int maxTryCount = cacheProperties.getCache().getNumberOfLoadAttempts();
        int timeToWait = cacheProperties.getCache().getTimeBetweenLoadAttempts();

        NodeCandidates myObj = null;
        while(currentTryCount < maxTryCount) {
            try {
                Future<Object> f = memcachedClient.asyncGet(key);
                myObj = (NodeCandidates) f.get(5, TimeUnit.SECONDS);
                currentTryCount = maxTryCount;
                f.cancel(true);

                // throws expecting InterruptedException, ExecutionException
                // or TimeoutException
            } catch (Exception e) {
                currentTryCount++;
                log.warn("Attempt {} of {} failed for loading value for key {}", currentTryCount, maxTryCount, key);
                if(currentTryCount == maxTryCount){
                    throw new CancellationException();
                }
                try{
                    Thread.sleep(timeToWait * 1000);
                }catch (InterruptedException ex){
                    //nothing to do
                }
                // Since we don't need this, go ahead and cancel the operation.
                // This is not strictly necessary, but it'll save some work on
                // the server.  It is okay to cancel it if running.
            }
        }
        return myObj;
    }

}
