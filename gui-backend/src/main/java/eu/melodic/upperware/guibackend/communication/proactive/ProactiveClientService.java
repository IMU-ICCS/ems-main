package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;

public interface ProactiveClientService extends IProactiveClientServiceConnector {
    Long stopJob(String jobId);

}
