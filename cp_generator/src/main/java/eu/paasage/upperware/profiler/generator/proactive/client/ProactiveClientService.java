package eu.paasage.upperware.profiler.generator.proactive.client;

import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.properties.GeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ProactiveClientService {

    private final PAGateway paGateway;
    private final GeneratorProperties generatorProperties;
    private final AtomicInteger connectionState = new AtomicInteger(0);

    @Autowired
    public ProactiveClientService(final GeneratorProperties generatorProperties) {
        this.generatorProperties = generatorProperties;
        log.info("ProActive Dev [ProactiveClientService]: Proactive properties: {}", generatorProperties.getPaRest());

        try {
            paGateway = new PAGateway(generatorProperties.getPaRest().getUrl());
            log.info("ProActive Dev [ProactiveClientService]: PAGateway created");
        } catch (RuntimeException e) {
            log.info("Exception caught during creation of Proactive Client (PAGateway) object, error: {}", e.getMessage());
            throw new GeneratorException("Exception caught during creation of Proactive Client (PAGateway) object", e);
        }

        connectToProactiveServer();
    }

    private void connectToProactiveServer() {
        if (!this.connectionState.compareAndSet(0, 1)) { // trying to connect (1)
            log.info("ProActive Dev [ProactiveClientService]: current connectionState indicates that connection is established or in progress");
            return;
        }

        try {
            log.info("ProActive Dev [ProactiveClientService]: Trying to connect to Proactive server using: url: {}, encrypted login: {}, encrypted password: {}",
                    generatorProperties.getPaRest().getUrl(),
                    generatorProperties.getPaRest().getLogin(),
                    generatorProperties.getPaRest().getPassword());

            try {
                log.info("ProActive Dev [ProactiveClientService]: decrypted login: {}", ProtectionUtils.decrypt(generatorProperties.getPaRest().getLogin()));
                log.info("ProActive Dev [ProactiveClientService]: decrypted password: {}", ProtectionUtils.decrypt(generatorProperties.getPaRest().getPassword()));
            } catch (RuntimeException e) {
                log.info("Exception caught while decrypting credentials, message: {}", e.getMessage());
                throw e;
            }

            paGateway.connect(ProtectionUtils.decrypt(generatorProperties.getPaRest().getLogin()),
                    ProtectionUtils.decrypt(generatorProperties.getPaRest().getPassword()));
            this.connectionState.set(2); // connected (2)
            log.info("ProActive Dev [ProactiveClientService]: Connected to Proactive server");
        } catch (Exception e) {
            this.connectionState.set(0); // disconnected (0)
            log.info("Exception of type {} caught while connecting to Proactive server, error: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public void disconnectFromProactiveServer() {
        if (!this.connectionState.compareAndSet(2, 3)) { // trying to disconnect (3)
            log.info("ProActive Dev [ProactiveClientService]: current connectionState indicates that connection is not established or disconnecting in progress");
            return;
        }

        try {
            log.info("ProActive Dev [ProactiveClientService]: Trying to disconnect from Proactive server");
            paGateway.disconnect();
            this.connectionState.set(0); // disconnected (0)
            log.info("ProActive Dev [ProactiveClientService]: Disconnected from Proactive server");
        } catch (Exception e) {
            this.connectionState.set(2); // connected (2)
            log.info("Exception of type {} caught while disconnecting from Proactive server, error: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public ProactiveConnectionState getConnectionState() {
        return ProactiveConnectionState.findByCode(this.connectionState.get());
    }

    public List<NodeCandidate> findNodeCandidates(List<Requirement> requirements) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.findNodeCandidates(requirements);
        }
        return Collections.emptyList();
    }
}
