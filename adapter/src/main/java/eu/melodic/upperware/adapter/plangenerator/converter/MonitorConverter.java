package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.type.StringValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import eu.melodic.models.services.adapter.*;
import eu.melodic.models.services.adapter.Monitor;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
public class MonitorConverter implements ModelConverter<DeploymentInstanceModel, List<AdapterMonitor>> {

    @Override
    public List<AdapterMonitor> toComparableModel(DeploymentInstanceModel model) {
        List<Monitor> monitors = model.getAttributes()
                .stream()
                .filter(attribute -> "monitors".equals(attribute.getName()))
                .findFirst()
                .map(attribute -> ((StringValue) attribute.getValue()).getValue())
                .map(this::fromJson).orElse(Collections.emptyList());

        return monitors
                .stream()
                .map(this::toMonitor)
                .collect(Collectors.toList());
    }

    private List<Monitor> fromJson(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        //I am not sure if it really works...
        objectMapper.configOverride(Collections.class)
                .setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));

        CollectionType typeReference =
                TypeFactory.defaultInstance().constructCollectionType(List.class, Monitor.class);
        try {
            return objectMapper.readValue(value, typeReference);
        } catch (IOException e) {
            throw new AdapterException(format("Could not deserialise %s as a List<Monitor>", value));
        }
    }

    private AdapterMonitor toMonitor(Monitor monitor) {

        return AdapterMonitor.builder()
                .taskName(monitor.getComponent())
                .metricName(monitor.getMetric())
                .sensor(toSensor(monitor.getSensor()))
                .sinks(toSinks(monitor.getSinks()))
                .tags(toTags(monitor.getTags()))
                .build();
    }

    private List<Pair<String, String>> toTags(List<KeyValuePair> tags) {
        return CollectionUtils.emptyIfNull(tags).stream()
                .map(keyValuePair -> Pair.of(keyValuePair.getKey(), keyValuePair.getValue()))
                .collect(Collectors.toList());
    }

    private List<AdapterSink> toSinks(List<Sink> sinks) {
        return CollectionUtils.emptyIfNull(sinks).stream()
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
        throw new AdapterException(format("Exception during converting Sensor. Only PushSensor and PullSensor are supported. Current value: %s", sensor.getClass().getSimpleName()));
    }

    private AdapterInterval toInterval(Interval pullSensor) {
        return AdapterInterval.builder()
                .period(pullSensor.getPeriod())
                .unit(AdapterUnit.valueOf(pullSensor.getUnit().name()))
                .build();
    }

    private List<Pair<String, String>> toConfiguration(List<KeyValuePair> configuration) {
        return CollectionUtils.emptyIfNull(configuration).stream()
                .map(keyValuePair -> Pair.of(keyValuePair.getKey(), keyValuePair.getValue()))
                .collect(Collectors.toList());
    }

}
