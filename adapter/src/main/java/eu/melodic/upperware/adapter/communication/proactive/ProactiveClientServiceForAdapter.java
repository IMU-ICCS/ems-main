package eu.melodic.upperware.adapter.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;
import org.activeeon.morphemic.model.ByonNode;
import org.activeeon.morphemic.model.EdgeNode;
import org.activeeon.morphemic.model.SubmittedJobType;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.proactive.scheduler.common.job.JobStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProactiveClientServiceForAdapter extends IProactiveClientServiceConnector {
    int createJob(JSONObject job);
    int addNodes(JSONArray nodes, String jobId);
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
    int addEdgeNodes(Map<String, String> edgeIdPerComponent, String jobId);

}
