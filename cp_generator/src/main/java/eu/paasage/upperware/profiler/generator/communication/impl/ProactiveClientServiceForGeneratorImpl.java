package eu.paasage.upperware.profiler.generator.communication.impl;

import eu.paasage.upperware.profiler.generator.communication.ProactiveClientServiceForGenerator;
import eu.passage.upperware.commons.proactive.client.ProactiveClientServiceConnector;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProactiveClientServiceForGeneratorImpl extends ProactiveClientServiceConnector implements ProactiveClientServiceForGenerator {

    public ProactiveClientServiceForGeneratorImpl(String restUrl, String login, String password, String encryptorPassword) {
        super(restUrl, login, password, encryptorPassword);
    }

    @Override
    public List<NodeCandidate> findNodeCandidates(List<Requirement> requirements) {
        Optional<PAGateway> paGatewayOptional = getPAGateway();
        if(paGatewayOptional.isPresent()) {
            return paGatewayOptional.get().findNodeCandidates(requirements);
        }
        return Collections.emptyList();
    }
}
