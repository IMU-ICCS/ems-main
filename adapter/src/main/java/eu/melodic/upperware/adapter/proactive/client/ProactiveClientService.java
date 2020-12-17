package eu.melodic.upperware.adapter.proactive.client;

import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ProactiveClientService {

    private final PAGateway paGateway;
    private final AdapterProperties adapterProperties;
    private final AtomicInteger connectionState = new AtomicInteger(0);

    @Autowired
    public ProactiveClientService(final AdapterProperties adapterProperties) {
        this.adapterProperties = adapterProperties;
        log.info("ProActive Dev [ProactiveClientService]: Proactive properties: {}", adapterProperties.getPaRest());

        try {
            paGateway = new PAGateway(adapterProperties.getPaRest().getUrl());
            log.info("ProActive Dev [ProactiveClientService]: PAGateway created");
        } catch (RuntimeException e) {
            log.info("Exception caught during creation of Proactive Client (PAGateway) object, error: {}", e.getMessage());
            throw new AdapterException("Exception caught during creation of Proactive Client (PAGateway) object", e);
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
                    adapterProperties.getPaRest().getUrl(),
                    adapterProperties.getPaRest().getLogin(),
                    adapterProperties.getPaRest().getPassword());

            try {
                log.info("ProActive Dev [ProactiveClientService]: decrypted login: {}", ProtectionUtils.decrypt(adapterProperties.getPaRest().getLogin()));
                log.info("ProActive Dev [ProactiveClientService]: decrypted password: {}", ProtectionUtils.decrypt(adapterProperties.getPaRest().getPassword()));
            } catch (RuntimeException e) {
                log.info("Exception caught while decrypting credentials, message: {}", e.getMessage());
                throw e;
            }

            paGateway.connect(ProtectionUtils.decrypt(adapterProperties.getPaRest().getLogin()),
                    ProtectionUtils.decrypt(adapterProperties.getPaRest().getPassword()));
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

    public void createJob(JSONObject job) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            this.paGateway.createJob(job);
        }
    }

    public int addNodes(JSONArray nodes, String jobId) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.addNodes(nodes, jobId);
        }
        return -1;
    }

    public long submitJob(String jobId) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.submitJob(jobId);
        }
        return -1;
    }
}
