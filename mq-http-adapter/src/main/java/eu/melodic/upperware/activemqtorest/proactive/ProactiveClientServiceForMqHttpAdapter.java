package eu.melodic.upperware.activemqtorest.proactive;

import org.ow2.proactive.sal.model.Deployment;

import java.util.List;

public interface ProactiveClientServiceForMqHttpAdapter {
    List<Deployment> getAllNodes();
}
