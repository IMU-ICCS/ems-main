package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;
import org.activeeon.morphemic.model.*;

import java.util.List;

public interface ProactiveClientServiceGUI extends IProactiveClientServiceConnector {

    int getNumberOfCurrentOffers();

    List<PACloud> getAllClouds();

    List<Image> getAllCloudImages(String cloudID);

    List<Hardware> getAllHardware();

    List<Location> getAllLocation();

    ByonNode registerNewByonNode(ByonDefinition byonNodeDefinition, String jobId, boolean automate);

    List<ByonNode> getByonNodeList(String jobId);

    List<Job> getAllJobs();

    List<Deployment> getAllNodes();

    List<EmsDeploymentRequest> getAllMonitors();

    boolean deleteByonNode(String byonId);
}
