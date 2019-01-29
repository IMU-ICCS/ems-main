package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Getter
@Builder
public class AdapterMonitor implements Data {

    private String metricName;
    private String taskName;
    private AdapterSensor sensor;
    private List<AdapterSink> sinks;
    private List<Pair<String, String>> tags;

    @Override
    public String getName() {
        return "AdapterMonitor_" + metricName + "_" + taskName;
    }
}
