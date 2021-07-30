package eu.melodic.upperware.activemqtorest.proactive;

import cloud.morphemic.connectors.proactive.ProactiveClientServiceConnector;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.activeeon.morphemic.model.Deployment;

import java.util.Collections;
import java.util.List;

@Slf4j
public class ProactiveClientServiceForMqHttpAdapterImpl extends ProactiveClientServiceConnector implements ProactiveClientServiceForMqHttpAdapter {

    public ProactiveClientServiceForMqHttpAdapterImpl(String restUrl, String login, String password, String encryptorPassword) {
        super(restUrl, login, password, encryptorPassword);
    }

    @Override
    public List<Deployment> getAllNodes() {
        return getPAGateway().map(PAGateway::getAllNodes).orElse(Collections.emptyList());
    }
}
