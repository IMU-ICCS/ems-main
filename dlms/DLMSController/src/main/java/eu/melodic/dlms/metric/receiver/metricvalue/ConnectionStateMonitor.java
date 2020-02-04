package eu.melodic.dlms.metric.receiver.metricvalue;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.transport.TransportListener;

import java.io.IOException;

@Slf4j
class ConnectionStateMonitor implements TransportListener {
    @Override
    public void onCommand(Object command)
    {
        log.debug("Command detected: '{}'", command);
    }

    @Override
    public void onException(IOException exception)
    {
        log.error("Exception detected: '{}'", exception.getMessage());
    }

    @Override
    public void transportInterupted()
    {
        log.error("Transport interruption detected.");
    }

    @Override
    public void transportResumed()
    {
        log.info("Transport resumption detected.");
    }
}
