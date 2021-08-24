package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.ProactiveClientServiceConnector;

public class ProactiveClientServiceImpl extends ProactiveClientServiceConnector implements ProactiveClientService {

    protected ProactiveClientServiceImpl(String restUrl, String login, String password, String encryptorPassword) {
        super(restUrl, login, password, encryptorPassword);
    }

    @Override
    public Long stopJob(String jobId) {
         return getPAGateway().map(paGateway -> paGateway.stopJob(jobId)).orElse(0L);
    }
}
