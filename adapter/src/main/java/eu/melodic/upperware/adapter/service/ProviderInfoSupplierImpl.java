package eu.melodic.upperware.adapter.service;

import camel.core.Feature;
import com.google.gson.Gson;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProviderInfoSupplierImpl implements ProviderInfoSupplier {

    private Gson gson;

    @Override
    public NodeCandidate getNodeCandidate(Feature feature) {
        String nodeCandidateStr = getAttribute("nodeCandidate", feature);
        return gson.fromJson(nodeCandidateStr, NodeCandidate.class);
    }
}
