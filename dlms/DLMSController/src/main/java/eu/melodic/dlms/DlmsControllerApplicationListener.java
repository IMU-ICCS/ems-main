package eu.melodic.dlms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import eu.melodic.dlms.metric.receiver.metricvalue.MetricValueMonitorBean;
import eu.melodic.dlms.metric.receiver.properties.DlmsMetricProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to receive metrics.
 */
@Component
@Slf4j
public class DlmsControllerApplicationListener implements ApplicationListener<ApplicationEvent>, ApplicationContextAware  {

	private MetricValueMonitorBean metricValueMonitorBean;
    private DlmsMetricProperties properties;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
		this.metricValueMonitorBean = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);
		this.metricValueMonitorBean.subscribe();
		this.properties = (DlmsMetricProperties) applicationContext.getBean(DlmsMetricProperties.class);
    }
    
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
		log.trace("** Application Event Received : "+event.getClass().getName());
		if (event instanceof org.springframework.context.event.ContextClosedEvent) {
			log.debug("** Application Event Received : Context Closed");
			metricValueMonitorBean.unsubscribe();
		} else
		{
			log.trace("** Application Event Received : Other...");
		}
    }

}
