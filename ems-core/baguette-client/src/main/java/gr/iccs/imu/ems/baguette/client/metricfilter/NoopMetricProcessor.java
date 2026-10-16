package gr.iccs.imu.ems.baguette.client.metricfilter;

import gr.iccs.imu.ems.api.client.IMetricsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoopMetricProcessor implements IMetricsFilter, InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.trace("NoopMetricProcessor.afterPropertiesSet: Initialized");
    }

    @Override
    public Context prePublishMetric(String metricName, double metricValue, String nodeAddress) {
        log.trace("NoopMetricProcessor.prePublishMetric: {}, {}, {}", metricName, metricValue, nodeAddress);
        return new Context(true, this);
    }

    @Override
    public void postPublishMetric(String metricName, double metricValue, String nodeAddress, Context context) {
        log.trace("NoopMetricProcessor.postPublishMetric: {}, {}, {}, {}", metricName, metricValue, nodeAddress, context);
    }

    @Override
    public void notPublishedMetric(String metricName, double metricValue, String nodeAddress, Context context) {
        log.trace("NoopMetricProcessor.notPublishedMetric: {}, {}, {}, {}", metricName, metricValue, nodeAddress, context);
    }
}
