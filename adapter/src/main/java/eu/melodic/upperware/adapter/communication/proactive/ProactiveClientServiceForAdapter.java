package eu.melodic.upperware.adapter.communication.proactive;

import org.apache.commons.lang3.tuple.Pair;
import org.ow2.proactive.sal.model.*;
import org.ow2.proactive.scheduler.common.job.JobStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProactiveClientServiceForAdapter {
    int createJob(JobDefinition job);
    int addNodes(List<IaasDefinition> iaasDefinitions, String jobId);
    int removeNodes(List<String> nodeNames);
    long submitJob(String jobId);
    int addScaleOutTask(List<String> nodeNames, String jobId, String taskName);
    int addScaleInTask(List<String> nodeNames, String jobId, String taskName);
    Optional<Pair<SubmittedJobType, JobStatus>> getJobStatus(String jobId);
    int addMonitors(List<String> nodeNames, String authorizationBearer);
    int addByonNodes(Map<String, String> byonIdPerComponent, String jobId);
    void waitForJobFinish(String jobId, SubmittedJobType expectedJobType);
    List<ByonNode> getByonNodeList(String jobId);
    List<EdgeNode> getEdgeNodeList(String jobId);
    List<Deployment> getAllNodes();
    int addEdgeNodes(Map<String, String> edgeIdPerComponent, String jobId);

}
