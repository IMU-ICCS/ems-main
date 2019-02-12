package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import camel.type.StringValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import eu.melodic.models.services.adapter.*;
import eu.melodic.models.services.adapter.Monitor;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.plangenerator.converter.job.DockerInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.converter.job.LanceInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class MonitorConverter implements ModelConverter<DeploymentInstanceModel, List<AdapterMonitor>> {

    private LanceInterfaceConverter lanceInterfaceConverter;
    private DockerInterfaceConverter dockerInterfaceConverter;

    @Override
    public List<AdapterMonitor> toComparableModel(DeploymentInstanceModel model) {
        log.info("Looking for monitor attribute in DeploymentInstanceModel: {}, it has {} attributes", model.getName(), model.getAttributes().size());

        String jobName = ConverterUtils.getJobName(model);
        List<Monitor> monitors = getMonitors(model);

        return model.getSoftwareComponentInstances()
                .stream()
                .filter(softwareComponentInstance ->
                    lanceInterfaceConverter.isInstance(CdoTool.getFirstElement(softwareComponentInstance.getType().getConfigurations())) ||
                    dockerInterfaceConverter.isInstance(CdoTool.getFirstElement(softwareComponentInstance.getType().getConfigurations())))
                .map(softwareComponentInstance -> toMonitors(softwareComponentInstance, jobName, monitors))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Monitor> getMonitors(DeploymentInstanceModel model) {
        boolean isMonitorAttributeExists = model.getAttributes()
                .stream()
                .anyMatch(attribute -> "monitors".equals(attribute.getName()));

        log.info("Attribute monitors is {}", isMonitorAttributeExists ? "FOUND":"NOT_FOUND");

        List<Monitor> monitors = model.getAttributes()
                .stream()
                .filter(attribute -> "monitors".equals(attribute.getName()))
                .findFirst()
                .map(attribute -> ((StringValue) attribute.getValue()).getValue())
                .map(this::fromJson).orElse(Collections.emptyList());


        updateConfig(monitors);

        return monitors;
    }

    //TODO - to remove
    private void updateConfig(List<Monitor> monitors) {

        KeyValuePair jmsBroker = new KeyValuePairImpl();
        jmsBroker.setKey("jms.broker");
        jmsBroker.setValue("failover:(tcp://localhost:61616)?initialReconnectDelay=1000&warnAfterReconnectAttempts=10");

        KeyValuePair jmsTopicSelector = new KeyValuePairImpl();
        jmsTopicSelector.setKey("jms.topic.selector");
        jmsTopicSelector.setValue("de.uniulm.omi.cloudiator.visor.reporting.jms.MetricNameTopicSelector");

        KeyValuePair jmsMessageFormat = new KeyValuePairImpl();
        jmsMessageFormat.setKey("jms.message.format");
        jmsMessageFormat.setValue("de.uniulm.omi.cloudiator.visor.reporting.jms.MelodicJsonEncoding");

        List<KeyValuePair> newConfig = Arrays.asList(jmsBroker, jmsTopicSelector, jmsMessageFormat);

        monitors.stream()
                .map(Monitor::getSinks)
                .flatMap(Collection::stream)
                .filter(sink -> Sink.TypeType.JMS.equals(sink.getType()))
                .forEach(sink -> sink.setConfiguration(newConfig));
    }

    private List<AdapterMonitor> toMonitors(SoftwareComponentInstance softwareComponentInstance, String jobName, List<Monitor> monitors) {
        String componentName = softwareComponentInstance.getType().getName();

        return monitors.stream()
                .filter(monitor -> monitor.getComponent().equals(componentName))
                .map(monitor -> toMonitor(monitor, jobName, softwareComponentInstance.getName()))
                .collect(Collectors.toList());
    }

    private AdapterMonitor toMonitor(Monitor monitor, String jobName, String nodeName) {

        return AdapterMonitor.builder()
                .jobName(jobName)
                .nodeName(nodeName)
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
        checkConfiguration(sink);

        return AdapterSink.builder()
                .sinkType(AdapterSinkType.valueOf(sink.getType().name()))
                .configuration(toConfiguration(sink.getConfiguration()))
                .build();
    }

    private void checkConfiguration(Sink sink) {
        switch (sink.getType()) {
            case INFLUX:
                checkOrThrow(sink, Arrays.asList("influx.url", "influx.username", "influx.password", "influx.database"));
                break;
            case JMS:
                checkOrThrow(sink, Arrays.asList("jms.broker", "jms.topic.selector", "jms.message.format"));
                break;
            case KAIROSDB:
                checkOrThrow(sink, Arrays.asList("kairos.port", "kairos.host"));
                break;
        }
    }

    private void checkOrThrow(Sink sink, List<String> reqProperties){
        if (!hasConfiguration(sink.getConfiguration(), reqProperties)) {
            throw new AdapterException(format("Could not find all required elements %s for %s Sink configuration %s", reqProperties, sink.getType(), getConfigurationAsString(sink.getConfiguration())));
        }
    }

    private String getConfigurationAsString(List<KeyValuePair> configuration) {
        return CollectionUtils.emptyIfNull(configuration)
                .stream()
                .map(keyValuePair -> "(" + keyValuePair.getKey() + ": " + keyValuePair.getValue() + ")")
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private boolean hasConfiguration(List<KeyValuePair> configuration, List<String> reqProperties) {
        return CollectionUtils.emptyIfNull(configuration)
                .stream()
                .map(KeyValuePair::getKey)
                .collect(Collectors.toList()).containsAll(reqProperties);
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

    private List<Monitor> fromJson(String value) {
        log.info("Trying to deseriase monitors in JSON {}", value);

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

}
