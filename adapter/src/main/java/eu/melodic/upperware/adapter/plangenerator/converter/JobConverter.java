package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.*;
import camel.deployment.Communication;
import eu.melodic.upperware.adapter.plangenerator.converter.job.DockerInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.converter.job.FaasInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.converter.job.LanceInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.converter.job.SparkInterfaceConverter;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static eu.passage.upperware.commons.extensions.OptionalUtils.peek;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class JobConverter implements ModelConverter<DeploymentInstanceModel, AdapterJob> {

    private LanceInterfaceConverter lanceInterfaceConverter;
    private SparkInterfaceConverter sparkInterfaceConverter;
    private DockerInterfaceConverter dockerInterfaceConverter;
    private FaasInterfaceConverter faasInterfaceConverter;

    private static final String PORT_PROVIDED = "PortProvided";
    private static final String PORT_REQUIRED = "PortRequired";

    private static final String DOCKER_TAG = "docker";

    @Override
    public AdapterJob toComparableModel(DeploymentInstanceModel model) {

        return AdapterJob.builder()
                .jobName(ConverterUtils.getJobName(model))
                .tasks(toAdapterTasks(model.getType()))
                .communications(toCommunication(model.getType()))
                .build();
    }

    private List<AdapterTask> toAdapterTasks(DeploymentTypeModel model) {
        List<Communication> communications = model.getCommunications();
        return model
                .getSoftwareComponents()
                .stream()
                .filter(Objects::nonNull)
                .map(softwareComponent -> convertToTask(softwareComponent, communications))
                .collect(Collectors.toList());
    }

    private AdapterTask convertToTask(SoftwareComponent softwareComponent, List<Communication> communications) {
        return AdapterTask.builder()
                .name(softwareComponent.getName())
                .taskType(chooseTaskType(softwareComponent))
                .interfaces(convertToInterfaces(softwareComponent))
                .ports(convertToPorts(softwareComponent, communications))
                .build();
    }

    private AdapterTaskType chooseTaskType(SoftwareComponent softwareComponent) {
        return isFaasComponent(getConfiguration(softwareComponent)) ? AdapterTaskType.BATCH : AdapterTaskType.SERVICE;
    }


    private List<AdapterTaskInterface> convertToInterfaces(SoftwareComponent softwareComponent) {
        Configuration configuration = getConfiguration(softwareComponent);

        AdapterTaskInterface result;
        if (isLanceComponent(configuration)) {
            result = lanceInterfaceConverter.convert((ScriptConfiguration) configuration);
        } else if (isDockerComponent(configuration)) {
            result = dockerInterfaceConverter.convert((ScriptConfiguration) configuration);
        } else if (isSparkComponent(configuration)) {
            result = sparkInterfaceConverter.convert((ClusterConfiguration) configuration);
        } else if (isFaasComponent(configuration)) {
            result = faasInterfaceConverter.convert((ServerlessConfiguration) configuration);
            result = faasInterfaceConverter.addInformationFromSoftwareComponent(result, softwareComponent);
        } else if (isPlatformComponent(configuration)) {
            result = new AdapterTaskInterface();
        } else {
            throw new IllegalStateException("Unknown Interface");
        }
        log.info("Configuration {} for {} converted to: {}", configuration.getName(), softwareComponent.getName(), result);
        return Collections.singletonList(result);
    }


    private boolean isLanceComponent(Configuration configuration) {
        if (configuration instanceof ScriptConfiguration) {
            ScriptConfiguration scriptConfiguration = (ScriptConfiguration) configuration;
            return !DOCKER_TAG.equals(scriptConfiguration.getDevopsTool());
        }
        return false;
    }

    private boolean isSparkComponent(Configuration configuration) {
        return configuration instanceof ClusterConfiguration;
    }

    private boolean isDockerComponent(Configuration configuration) {
        if (configuration instanceof ScriptConfiguration) {
            ScriptConfiguration scriptConfiguration = (ScriptConfiguration) configuration;
            return DOCKER_TAG.equals(scriptConfiguration.getDevopsTool());
        }
        return false;
    }

    private boolean isFaasComponent(Configuration configuration) {
        return configuration instanceof ServerlessConfiguration;
    }

    private boolean isPlatformComponent(Configuration configuration) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    private List<AdapterPort> convertToPorts(SoftwareComponent softwareComponent, List<Communication> communications) {
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
                    .updateAction(getUpdateActionCommand(requiredCommunication.getName(), communications))
                    .build());
        }
        return result;
    }

    private String getUpdateActionCommand(String requiredCommunicationName, List<Communication> communications){
        return getCommunicationForRequiredPort(requiredCommunicationName, communications)
                .map(ScriptConfiguration::getStartCommand)
                .orElse(null);
    }

    private Optional<ScriptConfiguration> getCommunicationForRequiredPort(String requiredCommunicationName, List<Communication> communications) {
        return communications.stream()
                .filter(communication -> communication.getRequiredCommunication().getName().equals(requiredCommunicationName))
                .findFirst()
                .map(peek(communication -> log.info("Communication {} found for requiredCommunicationName {}", communication.getName(), requiredCommunicationName)))
                .map(Communication::getRequiredPortConfiguration)
                .map(peek(configuration -> log.info("Found RequiredPortConfiguration {}", configuration.getName())))
                .filter(configuration1 -> configuration1 instanceof ScriptConfiguration)
                .map(peek(configuration -> log.info("Found RequiredPortConfiguration {} is instance of ScriptConfiguration", configuration.getName())))
                .map(configuration1 -> (ScriptConfiguration) configuration1);
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
