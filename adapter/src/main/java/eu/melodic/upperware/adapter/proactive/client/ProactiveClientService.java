package eu.melodic.upperware.adapter.proactive.client;

import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.proactive.scheduler.common.job.JobState;
import org.ow2.proactive.scheduler.common.job.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

        try {
            paGateway = new PAGateway(adapterProperties.getPaConfig().getRestUrl());
            log.info("ProactiveClientService->constructor: PAGateway created");
        } catch (RuntimeException e) {
            log.error("ProactiveClientService->constructor: Exception caught during creation of Proactive Client (PAGateway) object, error: {}", e.getMessage());
            throw new AdapterException("Exception caught during creation of Proactive Client (PAGateway) object", e);
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
                    adapterProperties.getPaConfig().getRestUrl(),
                    adapterProperties.getPaConfig().getLogin(),
                    adapterProperties.getPaConfig().getPassword());

            try {
                log.debug("ProactiveClientService->connectToProactiveServer: decrypted login: {}", ProtectionUtils.decrypt(adapterProperties.getPaConfig().getLogin()));
                log.debug("ProactiveClientService->connectToProactiveServer: decrypted password: {}", ProtectionUtils.decrypt(adapterProperties.getPaConfig().getPassword()));
            } catch (RuntimeException e) {
                log.debug("ProactiveClientService->connectToProactiveServer: Exception caught while decrypting Proactive server credentials, message: {}", e.getMessage());
                throw e;
            }

            paGateway.connect(ProtectionUtils.decrypt(adapterProperties.getPaConfig().getLogin()),
                    ProtectionUtils.decrypt(adapterProperties.getPaConfig().getPassword()));
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
            log.error("ProactiveClientService->disconnectFromProactiveServer: Exception of type {} caught while disconnecting from Proactive server, error: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public ProactiveConnectionState getConnectionState() {
        return ProactiveConnectionState.findByCode(this.connectionState.get());
    }

    public int createJob(JSONObject job) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            this.paGateway.createJob(job);
            return 0;
        }
        return -1;
    }

    public int addNodes(JSONArray nodes, String jobId) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.addNodes(nodes, jobId);
        }
        return -1;
    }

    public int removeNodes(List<String> nodeNames) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            this.paGateway.removeNodes(nodeNames, true);
            return 0;
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

    public int addScaleOutTask(List<String> nodeNames, String jobId, String taskName) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.addScaleOutTask(nodeNames, jobId, taskName);
        }
        return -1;
    }

    public int addScaleInTask(List<String> nodeNames, String jobId, String taskName) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.addScaleInTask(nodeNames, jobId, taskName);
        }
        return -1;
    }

    public Optional<JobStatus> getJobStatus(String jobId) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            try {
                Optional<JobState> jobState = Optional.ofNullable(this.paGateway.getJobState(jobId));
                if(jobState.isPresent()) {
                    return Optional.of(jobState.get().getJobInfo().getStatus());
                }
                log.error("ProactiveClientService->getJobStatus: ProActive client has not returned JobState for jobId={}", jobId);
            } catch (Exception e) {
                log.error("ProactiveClientService->getJobStatus: Exception has been caught while retrieving JobState for jobId={} from ProActive Scheduler, message: {}", jobId, e.getMessage());
            }
        }
        return Optional.empty();
    }

    public int addMonitors(List<String> nodeNames, String authorizationBearer) {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return this.paGateway.addEmsDeployment(nodeNames, authorizationBearer);
        }
        return -1;
    }
}
