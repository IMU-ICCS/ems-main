package eu.melodic.dlms.metric.receiver.metricvalue;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
public class MetricValueEventNetworkLatency {
    private String dlmsAgentPublicIp;
    private String dlmsAgentRegion;
    private String dlmsAgentCSP;
    private List<Map<String, BigDecimal>> latencies;
}
