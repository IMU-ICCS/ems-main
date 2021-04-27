package eu.melodic.upperware.adapter.communication.proactive;

import eu.passage.upperware.commons.proactive.client.IProactiveClientServiceConnector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.proactive.scheduler.common.job.JobStatus;

import java.util.List;
import java.util.Optional;

public interface ProactiveClientServiceForAdapter extends IProactiveClientServiceConnector {
    int createJob(JSONObject job);
    int addNodes(JSONArray nodes, String jobId);
    int removeNodes(List<String> nodeNames);
    long submitJob(String jobId);
    int addScaleOutTask(List<String> nodeNames, String jobId, String taskName);
    int addScaleInTask(List<String> nodeNames, String jobId, String taskName);
    Optional<JobStatus> getJobStatus(String jobId);
    int addMonitors(List<String> nodeNames, String authorizationBearer);
}
