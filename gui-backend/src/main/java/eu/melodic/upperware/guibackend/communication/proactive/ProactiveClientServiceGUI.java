package eu.melodic.upperware.guibackend.communication.proactive;

import org.ow2.proactive.sal.model.*;

import java.util.List;

public interface ProactiveClientServiceGUI {

    int getNumberOfCurrentOffers();

    List<PACloud> getAllClouds();

    List<Image> getAllCloudImages(String cloudID);

    List<Hardware> getAllHardware();

    List<Location> getAllLocation();

    EdgeNode registerNewEdgeNode(EdgeDefinition edgeDefinition, String jobId);

    ByonNode registerNewByonNode(ByonDefinition byonNodeDefinition, String jobId, boolean automate);

    List<EdgeNode> getEdgeNodeList(String jobId);

    List<ByonNode> getByonNodeList(String jobId);

    List<Job> getAllJobs();

    List<Deployment> getAllNodes();

    List<EmsDeploymentRequest> getAllMonitors();

    boolean deleteByonNode(String byonId);
}
