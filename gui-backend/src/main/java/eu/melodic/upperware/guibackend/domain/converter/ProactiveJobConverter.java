package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.Job;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ProactiveJobConverter implements GenericConverter<Job, eu.passage.upperware.commons.model.internal.Job> {

    private ProactiveMonitorConverter proactiveMonitorConverter;

    @Override
    public eu.passage.upperware.commons.model.internal.Job createDomain(@NonNull Job external) {
        return eu.passage.upperware.commons.model.internal.Job.builder()
                .id(external.getJobId())
                .submittedJobId(external.getSubmittedJobId())
                .submittedJobType(SubmittedJobType.valueOf(external.getSubmittedJobType().name()))
                .name(external.getName())
                .tasks(createTasksList(external.getTasks()))
                .build();
    }

    private List<Task> createTasksList(List<org.activeeon.morphemic.model.Task> tasks) {
        if(Objects.isNull(tasks)){
            return Collections.emptyList();
        }

        return tasks.stream()
                .map(task -> Task.builder()
                        .name(task.getName())
                        .taskId(task.getTaskId())
                        .type(task.getType())
                        .parentTasks(task.getParentTasks())
                        .installation(createInstallation(task.getInstallation()))
                        .portsToOpen(createPortsList(task.getPortsToOpen()))
                        .dockerEnvironment(createDockerEnvironment(task.getEnvironment()))
                        .deployments(createDeploymentsList(task.getDeployments()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private List<Deployment> createDeploymentsList(List<org.activeeon.morphemic.model.Deployment> deployments) {
        if(Objects.isNull(deployments)){
            return Collections.emptyList();
        }

        return deployments.stream()
                .map(deployment -> Deployment.builder()
                        .cloudProviderName(deployment.getPaCloud().getCloudProviderName())
                        .instanceId(deployment.getInstanceId())
                        .nodeAccessToken(deployment.getNodeAccessToken())
                        .nodeName(deployment.getNodeName())
                        .locationName(deployment.getLocationName())
                        .imageProviderId(deployment.getImageProviderId())
                        .hardwareProviderId(deployment.getHardwareProviderId())
                        .emsDeployment(proactiveMonitorConverter.createDomain(deployment.getEmsDeployment()))
                        .build()
                )
                .collect(Collectors.toList());

    }

    private DockerEnvironment createDockerEnvironment(org.activeeon.morphemic.model.DockerEnvironment environment) {
        if(Objects.isNull(environment)){
            return null;
        }

        return DockerEnvironment.builder()
                .dockerImage(environment.getDockerImage())
                .environmentVars(environment.getEnvironmentVars())
                .port(environment.getPort())
                .build();
    }

    private List<Port> createPortsList(List<org.activeeon.morphemic.model.Port> portsToOpen) {
        if(Objects.isNull(portsToOpen)){
            return Collections.emptyList();
        }

        return portsToOpen.stream()
                .map(port -> Port.builder()
                        .requestedName(port.getRequestedName())
                        .value(port.getValue())
                        .build()
                )
                .collect(Collectors.toList());
    }

    private CommandsInstallation createInstallation(org.activeeon.morphemic.model.CommandsInstallation installation) {
        if(Objects.isNull(installation)){
            return null;
        }

        return CommandsInstallation.builder()
                .operatingSystemType(OperatingSystem.builder()
                        .operatingSystemVersion(BigDecimal.valueOf(installation.getOperatingSystemType().getOperatingSystemVersion()))
                        .operatingSystemFamily(OperatingSystemFamily.valueOf(installation.getOperatingSystemType().getOperatingSystemFamily()))
                        .build()
                )
                .install(installation.getInstall())
                .postInstall(installation.getPostInstall())
                .postStart(installation.getPostStart())
                .postStop(installation.getPostStop())
                .preInstall(installation.getPreInstall())
                .preStart(installation.getPreStart())
                .preStop(installation.getPreStop())
                .start(installation.getStart())
                .stop(installation.getStop())
                .updateCmd(installation.getUpdateCmd())
                .build();
    }

    @Override
    public Job createExternal(@NonNull eu.passage.upperware.commons.model.internal.Job domain) {
        log.warn("ProactiveJobConverter.createExternal is not implemented yet.");
        return null;
    }
}
