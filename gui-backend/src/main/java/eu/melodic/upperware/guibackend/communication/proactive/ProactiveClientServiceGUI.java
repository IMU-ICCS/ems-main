package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;
import org.activeeon.morphemic.model.Image;
import org.activeeon.morphemic.model.PACloud;

import java.util.List;

public interface ProactiveClientServiceGUI extends IProactiveClientServiceConnector {

    int getNumberOfCurrentOffers();

    List<PACloud> getAllClouds();

    List<Image> getAllCloudImages(String cloudID);
}
