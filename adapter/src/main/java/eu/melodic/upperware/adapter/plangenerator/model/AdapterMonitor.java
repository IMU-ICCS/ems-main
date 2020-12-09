package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.BiPredicate;

@Getter
@Builder
@ToString(callSuper = true)
public class AdapterMonitor implements Data {

    public static final BiPredicate<AdapterMonitor, AdapterMonitor> MONITOR_BI_PREDICATE = (newReq, oldReq) ->
            newReq.getMetricName().equals(oldReq.getMetricName()) &&
                    newReq.getNodeName().equals(oldReq.getNodeName());


    private String jobName;
    private String nodeName;
    private String metricName;
    private String taskName;
    private AdapterSensor sensor;
    private List<AdapterSink> sinks;
    private List<Pair<String, String>> tags;

    @Override
    public String getName() {
        return String.format("AdapterMonitor_%s_%s_%s", metricName, taskName, nodeName);
    }
}
