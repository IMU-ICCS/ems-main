package eu.melodic.upperware.activemqtorest.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;
import org.activeeon.morphemic.model.Deployment;

import java.util.List;

public interface ProactiveClientServiceForMqHttpAdapter extends IProactiveClientServiceConnector {
    List<Deployment> getAllNodes();
}
