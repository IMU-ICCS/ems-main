package eu.melodic.upperware.adapter.communication.proactive;

import cloud.morphemic.connectors.proactive.ProactiveClientServiceConnector;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.proactive.scheduler.common.job.JobState;
import org.ow2.proactive.scheduler.common.job.JobStatus;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ProactiveClientServiceForAdapterImpl extends ProactiveClientServiceConnector implements ProactiveClientServiceForAdapter {

    public ProactiveClientServiceForAdapterImpl(String restUrl, String login, String password, String encryptorPassword) {
        super(restUrl, login, password, encryptorPassword);
    }

    @Override
    public int createJob(JSONObject job) {
        return getPAGateway().map(paGateway -> {
            paGateway.createJob(job);
            return 0;
        }).orElse(-1);
    }

    @Override
    public int addNodes(JSONArray nodes, String jobId) {
        return getPAGateway().map(paGateway -> paGateway.addNodes(nodes, jobId)).orElse(-1);
    }

    @Override
    public int removeNodes(List<String> nodeNames) {
        return getPAGateway().map(paGateway -> {
            paGateway.removeNodes(nodeNames, true);
            return 0;
        }).orElse(-1);
    }

    @Override
    public long submitJob(String jobId) {
        return getPAGateway().map(paGateway -> paGateway.submitJob(jobId)).orElse(-1L);
    }

    @Override
    public int addScaleOutTask(List<String> nodeNames, String jobId, String taskName) {
        return getPAGateway().map(paGateway -> paGateway.addScaleOutTask(nodeNames, jobId, taskName)).orElse(-1);
    }

    @Override
    public int addScaleInTask(List<String> nodeNames, String jobId, String taskName) {
        return getPAGateway().map(paGateway -> paGateway.addScaleInTask(nodeNames, jobId, taskName)).orElse(-1);
    }

    @Override
    public Optional<JobStatus> getJobStatus(String jobId) {
        return getPAGateway().map(paGateway -> {
            try {
                Optional<JobState> jobState = Optional.ofNullable(paGateway.getJobState(jobId));
                if(jobState.isPresent()) {
                    return Optional.of(jobState.get().getJobInfo().getStatus());
                }
                log.error("ProactiveClientService->getJobStatus: ProActive client has not returned JobState for jobId={}", jobId);
            } catch (Exception e) {
                log.error("ProactiveClientService->getJobStatus: Exception has been caught while retrieving JobState for jobId={} from ProActive Scheduler, message: {}", jobId, e.getMessage());
            }
            return Optional.<JobStatus>empty();
        }).orElse(Optional.empty());
    }

    @Override
    public int addMonitors(List<String> nodeNames, String authorizationBearer) {
        return getPAGateway().map(paGateway -> paGateway.addEmsDeployment(nodeNames, authorizationBearer)).orElse(-1);
    }
}
