package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.type.StringValue;
import com.google.gson.Gson;
import eu.melodic.models.interfaces.ems.Monitor;
import eu.melodic.models.interfaces.ems.*;
import eu.melodic.upperware.adapter.communication.ems.MonitorList;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
public class MonitorConverter implements ModelConverter<DeploymentInstanceModel, List<AdapterMonitor>>{

    private Gson gson = new Gson();

    @Override
    public List<AdapterMonitor> toComparableModel(DeploymentInstanceModel model) {
        return model.getAttributes()
                .stream()
                .filter(attribute -> "monitors".equals(attribute.getName()))
                .findFirst()
                .map(attribute -> ((StringValue) attribute.getValue()).getValue())
                .map(value -> gson.fromJson(value, MonitorList.class)).map(monitorList -> monitorList
                        .getMonitors()
                        .stream()
                        .map(this::toMonitor)
                        .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    private AdapterMonitor toMonitor(Monitor monitor) {

        return AdapterMonitor.builder()
                .taskName(monitor.getComponent())
                .metricName(monitor.getMetric())
                .sensor(toSensor(monitor.getSensor()))
                .sinks(toSinks(monitor.getSinks()))
                .tags(null)
                .build();
    }

    private List<AdapterSink> toSinks(List<Sink> sinks) {
        return sinks.stream()
                .map(this::toSink)
                .collect(Collectors.toList());
    }

    private AdapterSink toSink(Sink sink) {
        return AdapterSink.builder()
                .sinkType(AdapterSinkType.valueOf(sink.getType().name()))
                .configuration(toConfiguration(sink.getConfiguration()))
                .build();
    }

    private AdapterSensor toSensor(@NonNull Sensor sensor) {
        if (sensor.isPushSensor()) {
            PushSensor pushSensor = sensor.getPushSensor();

            return AdapterPushSensor.builder()
                    .port(pushSensor.getPort())
                    .build();

        } else if (sensor.isPullSensor()) {
            PullSensor pullSensor = sensor.getPullSensor();

            return AdapterPullSensor.builder()
                    .className(pullSensor.getClassName())
                    .configuration(toConfiguration(pullSensor.getConfiguration()))
                    .interval(toInterval(pullSensor.getInterval()))
                    .build();
        }
        throw new RuntimeException(format("Exception during converting Sensor. Only PushSensor and PullSensor are supported. Current value: %s", sensor.getClass().getSimpleName()));
    }

    private AdapterInterval toInterval(Interval pullSensor) {
        return AdapterInterval.builder()
                .period(pullSensor.getPeriod())
                .unit(AdapterUnit.valueOf(pullSensor.getUnit().name()))
                .build();
    }

    private List<Pair<String, String>> toConfiguration(List<KeyValuePair> configuration) {
        return configuration.stream()
                .map(keyValuePair -> Pair.of(keyValuePair.getKey(), keyValuePair.getValue()))
                .collect(Collectors.toList());
    }

}
