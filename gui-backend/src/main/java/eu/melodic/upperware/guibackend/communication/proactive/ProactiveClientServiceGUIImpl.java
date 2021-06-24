package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.ProactiveClientServiceConnector;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.activeeon.morphemic.model.Image;
import org.activeeon.morphemic.model.PACloud;

import java.util.Collections;
import java.util.List;

@Slf4j
public class ProactiveClientServiceGUIImpl extends ProactiveClientServiceConnector implements ProactiveClientServiceGUI {

    public ProactiveClientServiceGUIImpl(String restUrl, String login, String password, String encryptorPassword) {
        super(restUrl, login, password, encryptorPassword);
    }

    @Override
    public int getNumberOfCurrentOffers() {
        return getPAGateway().map(PAGateway::getLengthOfNodeCandidates).orElse(0);
    }

    @Override
    public List<PACloud> getAllClouds() {
        return getPAGateway().map(PAGateway::getAllClouds).orElse(Collections.emptyList());
    }

    @Override
    public List<Image> getAllCloudImages(String cloudID) {
        return getPAGateway().map(paGateway -> paGateway.getAllCloudImages(cloudID)).orElse(Collections.emptyList());
    }
}
