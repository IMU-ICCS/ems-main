package eu.melodic.cache.impl;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.exception.CacheException;
import org.activeeon.morphemic.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Qualifier("filecacheService")
@Service
@AllArgsConstructor
public class FilecacheService implements CacheService<NodeCandidates> {


    @Override
    public void store(String key, NodeCandidates nc) throws CacheException {

        try (ObjectOutputStream oos = new ObjectOutputStream(FileUtils.openOutputStream(new File (key)))) {
            oos.writeObject(nc.get());
        } catch (IOException e) {
            log.error("Problem during saving value in: {} ", key);
            throw new CacheException(String.format("Problem during saving value in: %s ", key), e);
        }
    }

    @Override
    public NodeCandidates load(String key) {
        Map<String, Map<Integer, List<NodeCandidate>>> candidates;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(key)))){
            candidates = (HashMap<String, Map<Integer, List<NodeCandidate>>>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            log.error("Problem during loading Node Candidates from file {}", key);
            throw new CacheException(String.format("Problem during loading Node Candidates from file %s", key));
        }

        return NodeCandidates.of(candidates);
    }
}
