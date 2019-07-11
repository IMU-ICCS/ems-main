package eu.melodic.upperware.guibackend.communication.cloudiator;

import io.github.cloudiator.rest.model.*;

import java.util.List;

public interface CloudiatorApi {
    Integer getDiscoveryStatusTotal();

    List<Hardware> getHardwareList();

    List<Location> getLocationList();

    List<Image> getImageList();

    List<Cloud> getCloudList();

    List<Function> getFunctionList();

    String getSecureVariable(String key);

    void deleteSecureVariable(String key);

    List<Node> getVMFromNodeList();

    List<Node> getFaasFromNodeList();

    void storeSecureVariable(String key, String value);

    List<Node> getNodeList();

    List<CloudiatorProcess> getProcessList();

    List<Queue> getQueueList();

    List<Job> getJobList();

    void deleteNode(String nodeId);

    void deleteCloudiatorProcess(String processId);
}
