package eu.melodic.upperware.adapter.communication.proactive;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;
import eu.melodic.upperware.adapter.exception.AdapterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.ByonNode;
import org.activeeon.morphemic.model.EdgeNode;
import org.activeeon.morphemic.model.Deployment;
import org.activeeon.morphemic.model.SubmittedJobType;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.proactive.scheduler.common.job.JobState;
import org.ow2.proactive.scheduler.common.job.JobStatus;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceForAdapterImpl implements ProactiveClientServiceForAdapter {

    private final long TIMEOUT_SECONDS = 5;

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public int createJob(JSONObject job) {
        try {
            proactiveClientConnectorService.createJob(job);
            return 0;
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addNodes(JSONArray nodes, String jobId) {
        try {
            return BooleanUtils.toInteger(proactiveClientConnectorService.addNodes(nodes, jobId));
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int removeNodes(List<String> nodeNames) {
        try {
            proactiveClientConnectorService.removeNodes(nodeNames, true);
            return 0;
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long submitJob(String jobId) {
        try {
            return proactiveClientConnectorService.submitJob(jobId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addScaleOutTask(List<String> nodeNames, String jobId, String taskName) {
        try {
            return BooleanUtils.toInteger(proactiveClientConnectorService.addScaleOutTask(jobId, taskName, nodeNames));
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addScaleInTask(List<String> nodeNames, String jobId, String taskName) {
        try {
            return BooleanUtils.toInteger(proactiveClientConnectorService.addScaleInTask(jobId, taskName, nodeNames));
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<Pair<SubmittedJobType, JobStatus>> getJobStatus(String jobId) {
        try {
            Optional<Pair<SubmittedJobType, JobState>> jobState = Optional.ofNullable(proactiveClientConnectorService.getJobState(jobId));
            if (jobState.isPresent()) {
                return Optional.of(Pair.of(jobState.get().getLeft(), jobState.get().getRight().getJobInfo().getStatus()));
            }
            log.error("ProactiveClientServiceForAdapterImpl->getJobStatus: ProActive client has not returned JobState for jobId={}", jobId);
        } catch (ProactiveClientException e) {
            log.error("ProactiveClientServiceForAdapterImpl->getJobStatus: Exception has been caught while retrieving JobState for jobId={} from ProActive Scheduler, message: {}", jobId, e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }


    @Override
    public int addMonitors(List<String> nodeNames, String authorizationBearer) {
        try {
            return proactiveClientConnectorService.addEmsDeployment(nodeNames, authorizationBearer);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void waitForJobFinish(@NonNull String jobId, @NonNull SubmittedJobType expectedJobType) {
        log.info("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}]: starting waiting for job with expected type: {} to finish",
                jobId,
                expectedJobType);
        int loops = 0;
        Optional<Pair<SubmittedJobType, JobStatus>> jobStatusAndType = this.getJobStatus(jobId);

        while(jobStatusAndType.isPresent() && Objects.nonNull(jobStatusAndType.get().getRight()) && jobStatusAndType.get().getRight().isJobAlive()) {
            try {
                if (!jobStatusAndType.get().getLeft().equals(expectedJobType)) {
                    log.error("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}] Error occurred in deployment: the expected JobType is not equal to returned JobType - expected: {}, returned: {}", jobId, expectedJobType.name(), jobStatusAndType.get().getLeft().name());
                    throw new AdapterException(String.format("Error occurred in deployment for application id: %s - the expected JobType is not equal to returned JobType - expected: %s, returned: %s", jobId, expectedJobType.name(), jobStatusAndType.get().getLeft().name()));
                }
                if (loops % 6 == 0) {
                    log.info("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}]: job is alive and jobStatusAndType: {} - waiting (waited for {} seconds so far)", jobId
                            , jobStatusAndType.get(), loops * TIMEOUT_SECONDS);
                }
                TimeUnit.SECONDS.sleep(TIMEOUT_SECONDS);
            } catch (InterruptedException e) {
                log.error("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}, loops: {}, TIMEOUT_SECONDS: {}] job got interrupted while sleeping: {}", jobId, loops, TIMEOUT_SECONDS, e.getMessage());
            }
            loops++;
            jobStatusAndType = this.getJobStatus(jobId);
        }

        log.info("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}]: final jobStatusAndType: {} - waited for a total of {} seconds", jobId
                , jobStatusAndType, loops * TIMEOUT_SECONDS);

        if(jobStatusAndType.isPresent() && Objects.nonNull(jobStatusAndType.get().getRight())) {
            if(isJobCompletedSuccessfully(jobStatusAndType.get().getRight())) {
                log.info("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}] job status indicates that it finished successfully: {}", jobId, jobStatusAndType.get());
            } else {
                log.error("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}] job status indicates that it didn't finish successfully: {}", jobId, jobStatusAndType.get());
                throw new AdapterException(String.format("Job status indicates that it didn't finish successfully: %s [application id: %s]", jobStatusAndType.get(), jobId));
            }
        } else {
            log.error("ProactiveClientServiceForAdapterImpl->waitForJobFinish [application id: {}] job status is not present - could not get job status from ProActive - jobStatusAndType={}", jobId, jobStatusAndType);
            throw new AdapterException(String.format("Job status is not present - could not get job status from ProActive [application id: %s] - jobStatusAndType=%s", jobId, jobStatusAndType.toString()));
        }
    }

    private boolean isJobCompletedSuccessfully(@NonNull JobStatus jobStatus) {
        switch (jobStatus) {
            case FINISHED: {
                return true;
            }
            case CANCELED:
            case FAILED:
            case KILLED: {
                return false;
            }
            default: {
                log.error("ProactiveClientServiceForAdapterImpl->isJobCompletedSuccessfully: unknown final status from ProActive - returning false");
                return false;
            }
        }
    }

    @Override
    public int addByonNodes(Map<String, Pair<String, String>> byonIdPerComponent, String jobId) {
        try {
            return BooleanUtils.toInteger(proactiveClientConnectorService.addByonNodes(byonIdPerComponent, jobId));
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ByonNode> getByonNodeList(String jobId) {
        try {
            return proactiveClientConnectorService.fetchByonNodes(jobId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<EdgeNode> getEdgeNodeList(String jobId) {
        try {
            return proactiveClientConnectorService.fetchEdgeNodes(jobId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public int addEdgeNodes(Map<String, Pair<String, String>> edgeIdPerComponent, String jobId) {
        try {
            return BooleanUtils.toInteger(proactiveClientConnectorService.addEdgeNodes(edgeIdPerComponent, jobId));
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Deployment> getAllNodes() {
        try {
            return proactiveClientConnectorService.fetchNodes();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
