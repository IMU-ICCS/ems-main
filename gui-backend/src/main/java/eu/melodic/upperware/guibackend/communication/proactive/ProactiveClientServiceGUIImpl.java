package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.ProactiveClientServiceConnector;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;

@Slf4j
public class ProactiveClientServiceGUIImpl extends ProactiveClientServiceConnector implements ProactiveClientServiceGUI {

    public ProactiveClientServiceGUIImpl(String restUrl, String login, String password, String encryptorPassword) {
        super(restUrl, login, password, encryptorPassword);
    }

    @Override
    public int getNumberOfCurrentOffers() {
        return getPAGateway().map(PAGateway::getLengthOfNodeCandidates).orElse(0);
    }
}
