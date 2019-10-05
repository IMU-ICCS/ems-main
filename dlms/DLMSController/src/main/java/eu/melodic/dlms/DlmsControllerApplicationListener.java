package eu.melodic.dlms;

import eu.melodic.dlms.metric.receiver.metricvalue.MetricValueMonitorBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

/**
 * Class to receive metrics.
 */
@Component
@Slf4j
public class DlmsControllerApplicationListener implements ApplicationListener<ApplicationEvent> {

	private MetricValueMonitorBean metricValueMonitorBean;

	public DlmsControllerApplicationListener(MetricValueMonitorBean metricValueMonitorBean) throws JMSException {
		this.metricValueMonitorBean = metricValueMonitorBean;
		this.metricValueMonitorBean.subscribe();
	}

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
		log.trace("** Application Event Received : "+event.getClass().getName());
		if (event instanceof org.springframework.context.event.ContextClosedEvent) {
			log.debug("** Application Event Received : Context Closed");
			metricValueMonitorBean.unsubscribe();
		} else{
			log.trace("** Application Event Received : Other...");
		}
    }

}
