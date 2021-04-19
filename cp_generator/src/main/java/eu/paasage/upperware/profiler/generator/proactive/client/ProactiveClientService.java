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
    private final ProtectionUtils protectionUtils;

    @Autowired
    public ProactiveClientService(final GeneratorProperties generatorProperties, final ProtectionUtils protectionUtils) {
        this.generatorProperties = generatorProperties;
        this.protectionUtils = protectionUtils;

        try {
            paGateway = new PAGateway(generatorProperties.getPaConfig().getRestUrl());
            log.info("ProactiveClientService->constructor: PAGateway created");
        } catch (RuntimeException e) {
            log.error("ProactiveClientService->constructor: Exception caught during creation of Proactive Client (PAGateway) object, error: {}", e.getMessage());
            throw new GeneratorException("Exception caught during creation of Proactive Client (PAGateway) object", e);
        }

        connectToProactiveServer();
    }

    private void connectToProactiveServer() {
        if (!this.connectionState.compareAndSet(0, 1)) { // trying to connect (1)
            log.debug("ProactiveClientService->connectToProactiveServer: current connectionState= {}", getConnectionState());
            return;
        }

        try {
            log.info("ProactiveClientService->connectToProactiveServer: Trying to connect to Proactive server using: url: {}, encrypted login: {}, encrypted password: {}",
                    generatorProperties.getPaConfig().getRestUrl(),
                    generatorProperties.getPaConfig().getLogin(),
                    generatorProperties.getPaConfig().getPassword());

            try {
                log.debug("ProactiveClientService->connectToProactiveServer: decrypted login: {}", protectionUtils.decrypt(generatorProperties.getPaConfig().getLogin()));
                log.debug("ProactiveClientService->connectToProactiveServer: decrypted password: {}", protectionUtils.decrypt(generatorProperties.getPaConfig().getPassword()));
            } catch (RuntimeException e) {
                log.debug("ProactiveClientService->connectToProactiveServer: Exception caught while decrypting Proactive server credentials, message: {}", e.getMessage());
                throw e;
            }

            paGateway.connect(protectionUtils.decrypt(generatorProperties.getPaConfig().getLogin()),
                    protectionUtils.decrypt(generatorProperties.getPaConfig().getPassword()));
            this.connectionState.set(2); // connected (2)
            log.info("ProactiveClientService->connectToProactiveServer: Connected to Proactive server");
        } catch (Exception e) {
            this.connectionState.set(0); // disconnected (0)
            log.error("ProactiveClientService->connectToProactiveServer: Exception of type {} caught while connecting to Proactive server, error: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public void disconnectFromProactiveServer() {
        if (!this.connectionState.compareAndSet(2, 3)) { // trying to disconnect (3)
            log.debug("ProactiveClientService->disconnectFromProactiveServer: current connectionState= {}", getConnectionState());
            return;
        }

        try {
            log.info("ProactiveClientService->disconnectFromProactiveServer: Trying to disconnect from Proactive server");
            paGateway.disconnect();
            this.connectionState.set(0); // disconnected (0)
            log.info("ProactiveClientService->disconnectFromProactiveServer: Disconnected from Proactive server");
        } catch (Exception e) {
            this.connectionState.set(2); // connected (2)
            log.info("ProactiveClientService->disconnectFromProactiveServer: Exception of type {} caught while disconnecting from Proactive server, error: {}", e.getClass().getSimpleName(), e.getMessage());
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
