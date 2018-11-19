package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.*;
import eu.melodic.upperware.adapter.plangenerator.converter.job.DockerInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.converter.job.LanceInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.converter.job.SparkInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class JobConverter implements ModelConverter<DeploymentInstanceModel, AdapterJob> {

    private LanceInterfaceConverter lanceInterfaceConverter;
    private SparkInterfaceConverter sparkInterfaceConverter;
    private DockerInterfaceConverter dockerInterfaceConverter;

    private static final String PORT_PROVIDED = "PortProvided";
    private static final String PORT_REQUIRED = "PortRequired";

    @Override
    public AdapterJob toComparableModel(DeploymentInstanceModel model) {

        return AdapterJob.builder()
                .jobName(ConverterUtils.getJobName(model))
                .tasks(toAdapterTasks(model.getType()))
                .communications(toCommunication(model.getType()))
                .build();
    }

    private List<AdapterTask> toAdapterTasks(DeploymentTypeModel model) {
        return model
                .getSoftwareComponents()
                .stream()
                .filter(Objects::nonNull)
                .map(this::convertToTask)
                .collect(Collectors.toList());
    }

    private AdapterTask convertToTask(SoftwareComponent softwareComponent) {
        return AdapterTask.builder()
                .name(softwareComponent.getName())
                .taskType(AdapterTaskType.SERVICE)
                .interfaces(convertToInterfaces(softwareComponent))
                .ports(convertToPorts(softwareComponent))
                .build();
    }


    private List<AdapterTaskInterface> convertToInterfaces(SoftwareComponent softwareComponent) {
        Configuration configuration = getConfiguration(softwareComponent);

        AdapterTaskInterface result;
        if (isLanceComponent(configuration)) {
            result = lanceInterfaceConverter.convert((ScriptConfiguration) configuration);
        } else if (isDockerComponent(configuration)) {
            result = dockerInterfaceConverter.convert((ServerlessConfiguration) configuration);
        } else if (isSparkComponent(configuration)) {
            result = sparkInterfaceConverter.convert((ClusterConfiguration) configuration);
        } else if (isPlatformComponent(configuration)) {
            result = new AdapterTaskInterface();
        } else {
            throw new IllegalStateException("Unknown Interface");
        }
        return Collections.singletonList(result);
    }

    private boolean isLanceComponent(Configuration configuration) {
        return configuration instanceof ScriptConfiguration;
    }

    private boolean isSparkComponent(Configuration configuration) {
        return configuration instanceof ClusterConfiguration;
    }

    private boolean isDockerComponent(Configuration configuration) {
        return configuration instanceof ServerlessConfiguration;
    }

    private boolean isPlatformComponent(Configuration configuration) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    private List<AdapterPort> convertToPorts(SoftwareComponent softwareComponent) {
        Configuration configuration = getConfiguration(softwareComponent);

        List<AdapterPort> result = new ArrayList<>();
        for (ProvidedCommunication providedCommunication : softwareComponent.getProvidedCommunications()) {
            result.add(AdapterPortProvided.builder()
                    .name(providedCommunication.getName())
                    .type(PORT_PROVIDED)
                    .port(providedCommunication.getPortNumber())
                    .build());
        }

        for (RequiredCommunication requiredCommunication : softwareComponent.getRequiredCommunications()) {
            result.add(AdapterPortRequired.builder()
                    .name(requiredCommunication.getName())
                    .type(PORT_REQUIRED)
                    .isMandatory(requiredCommunication.isIsMandatory())
                    .updateAction(((ScriptConfiguration) configuration).getStartCommand())
                    .build());
        }
        return result;
    }

    private Configuration getConfiguration(@NonNull SoftwareComponent softwareComponent) {
        return CdoTool.getFirstElement(softwareComponent.getConfigurations());
    }

    private List<AdapterCommunication> toCommunication(DeploymentTypeModel model) {
        return model
                .getCommunications()
                .stream()
                .map(this::convertToCommunication)
                .collect(Collectors.toList());
    }

    private AdapterCommunication convertToCommunication(camel.deployment.Communication communication) {
        return AdapterCommunication.builder()
                .portProvided(communication.getProvidedCommunication().getName())
                .portRequired(communication.getRequiredCommunication().getName())
                .build();
    }
}
