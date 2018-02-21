package eu.melodic.cache.impl;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.TRUE;

/**
 * Created by pszkup on 04.01.18.
 */

@Slf4j
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
        return (NodeCandidates) memcachedClient.get(key);
    }

    @Override
    public void storeToFile(String key, NodeCandidates nc) throws CacheException {

        try{
            File fileOne=new File(key);
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(nc.get());
            oos.flush();
            oos.close();
            fos.close();
        }
        catch (IOException e) {
            log.error("Problem during saving value in: {} ", key);
            throw new CacheException(String.format("Problem during saving value in: %s ", key), e);
        }
    }

    @Override
    public NodeCandidates loadFromFile(String key) {
        Map<String, Map<Integer, List<NodeCandidate>>> candidates;
        try{
            File toRead=new File(key);
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);

            candidates =(HashMap<String, Map<Integer, List<NodeCandidate>>>)ois.readObject();

            ois.close();
            fis.close();
        }
        catch(IOException | ClassNotFoundException e){
            log.error("Problem during loading Node Candidates from file {}", key);
            throw new CacheException(String.format("Problem during loading Node Candidates from file %s", key));
        }

        return NodeCandidates.of(candidates);
    }
}
