package eu.melodic.upperware.activemqtorest.proactive;

import org.activeeon.morphemic.model.Deployment;

import java.util.List;

public interface ProactiveClientServiceForMqHttpAdapter {
    List<Deployment> getAllNodes();
}
