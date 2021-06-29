package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;
import org.activeeon.morphemic.model.*;

import java.util.List;

public interface ProactiveClientServiceGUI extends IProactiveClientServiceConnector {

    int getNumberOfCurrentOffers();

    List<PACloud> getAllClouds();

    List<Image> getAllCloudImages(String cloudID);

    ByonNode registerNewByonNode(ByonDefinition byonNodeDefinition, String jobId);

    List<ByonNode> getByonNodeList(String jobId);

    List<Job> getAllJobs();
}
