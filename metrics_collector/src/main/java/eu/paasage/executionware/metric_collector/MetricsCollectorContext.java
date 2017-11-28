package eu.paasage.executionware.metric_collector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsCollectorContext {

    @Bean(name = "metricCollector")
    public MetricCollector metricCollector() {
        return new MetricCollector();
    }

}
