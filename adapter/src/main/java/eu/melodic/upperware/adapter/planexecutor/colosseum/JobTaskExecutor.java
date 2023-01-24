package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.JobTask;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import static java.lang.String.format;

@Slf4j
public class JobTaskExecutor extends RunnableTaskExecutor<AdapterJob> {

    private final String applicationId;
    private final ProactiveClientServiceForAdapter proactiveClientServiceForAdapter;

    JobTaskExecutor(JobTask task, Collection<Future> predecessors, String applicationId, ProactiveClientServiceForAdapter proactiveClientServiceForAdapter) {
        super(task, predecessors);
        this.applicationId = applicationId;
        this.proactiveClientServiceForAdapter = proactiveClientServiceForAdapter;
    }

    @Override
    public void create(AdapterJob taskBody) {
        if (taskBody.getPreviousJob().isPresent()) {
            log.warn("JobTaskExecutor->create: Job {} [application id: {}] has already been executed - skipping execution of the task", taskBody.getName(), applicationId);
            return;
        }

        try {
            // here we create workflow/job skeleton with all defined tasks (e.g. Component_App) with their communication ports and installation recipe
            // and connection between tasks/components (by portRequired and portProvided matching)
            log.info("JobTaskExecutor->create: [application id: {}] AdapterJob= {}", applicationId, taskBody);

            JobDefinition jobDefinition = new JobDefinition();
            JobInformation jobInformation = new JobInformation();
            jobInformation.setId(applicationId);
            jobInformation.setName(taskBody.getName());
            jobDefinition.setJobInformation(jobInformation);

            List<TaskDefinition> tasks = new LinkedList<>();
            taskBody.getTasks()
                    .forEach(adapterTask -> addTask(tasks, adapterTask));
            jobDefinition.setTasks(tasks);

            List<Communication> communications = new LinkedList<>();
            taskBody.getCommunications()
                    .forEach(adapterCommunication -> addCommunication(communications, adapterCommunication));
            jobDefinition.setCommunications(communications);
            log.info("JobTaskExecutor->create: [application id: {}] ProActive job (JSONObject): \n{}", applicationId, jobDefinition);

            int status = proactiveClientServiceForAdapter.createJob(jobDefinition);

            log.info("JobTaskExecutor->create: [application id: {}] createJob status= {}", applicationId, status);

        }
        catch (RuntimeException e) {
            log.error("JobTaskExecutor->create: [application id: {}] Could not add Job. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during adding Job %s [application id: %s]: %s", taskBody.getJobName(), applicationId, e.getMessage()), e);
        }
    }

    @Override
    public void delete(AdapterJob taskBody) {
        throw new UnsupportedOperationException("Delete method is not supported for JobTaskExecutor");
    }

    private void addTask(List<TaskDefinition> tasks, AdapterTask adapterTask) {
        TaskDefinition task = new TaskDefinition();
        task.setName(adapterTask.getName());
        task.setInstallation(getInstallationInstructionsJSON(adapterTask));
        List<AbstractPortDefinition> ports = new LinkedList<>();
        adapterTask.getPorts()
                .forEach(adapterPort -> addPort(ports, adapterPort));
        task.setPorts(ports);

        tasks.add(task);
    }

    private AbstractInstallation getInstallationInstructionsJSON(AdapterTask adapterTask) {
        return adapterTask.getInterfaces()
        .stream()
        .findFirst()
        .map(this::addInstallationInstructions)
        .orElseThrow(() -> new AdapterException(format("No installation instructions for task: %s [application id: %s]", adapterTask.getName(), applicationId)));
    }

    private void addCommunication(List<Communication> communications, AdapterCommunication adapterCommunication) {
        Communication communication = new Communication(
                adapterCommunication.getPortProvided(),
                adapterCommunication.getPortRequired()
        );
        communications.add(communication);
    }

    private AbstractInstallation addInstallationInstructions(AdapterTaskInterface adapterTaskInterface) {
        if (adapterTaskInterface instanceof AdapterLanceInterface) {
            CommandsInstallation installationInstructions = new CommandsInstallation();
            OperatingSystemType operatingSystem = new OperatingSystemType();
            operatingSystem.setOperatingSystemFamily(((AdapterLanceInterface) adapterTaskInterface).getOperatingSystem().getOperatingSystemFamily().toString());
            operatingSystem.setOperatingSystemVersion(((AdapterLanceInterface) adapterTaskInterface).getOperatingSystem().getOperatingSystemVersion().floatValue());
            installationInstructions.setOperatingSystemType(operatingSystem);
            installationInstructions.setPreInstall(((AdapterLanceInterface) adapterTaskInterface).getPreInstall());
            installationInstructions.setInstall(((AdapterLanceInterface) adapterTaskInterface).getInstall());
            installationInstructions.setPostInstall(((AdapterLanceInterface) adapterTaskInterface).getPostInstall());
            installationInstructions.setStart(((AdapterLanceInterface) adapterTaskInterface).getStart());
            installationInstructions.setStartDetection(((AdapterLanceInterface) adapterTaskInterface).getStartDetection());
            installationInstructions.setStop(((AdapterLanceInterface) adapterTaskInterface).getStop());
            installationInstructions.setUpdateCmd(((AdapterLanceInterface) adapterTaskInterface).getUpdate());
            return installationInstructions;
        }  else if (adapterTaskInterface instanceof AdapterDockerInterface) {
            return new DockerEnvironment(
                    ((AdapterDockerInterface) adapterTaskInterface).getDockerImage(),
                    ((AdapterDockerInterface) adapterTaskInterface).getEnvironment().get("port"),
                    ((AdapterDockerInterface) adapterTaskInterface).getEnvironment()
            );
//        } else if (adapterTaskInterface instanceof AdapterFaasInterface) {
//            installationInstructionsJSON.put("type", "faas");
//            installationInstructionsJSON.put("functionName", ((AdapterFaasInterface) adapterTaskInterface).getFunctionName());
//            installationInstructionsJSON.put("sourceCodeUrl", ((AdapterFaasInterface) adapterTaskInterface).getSourceCodeUrl());
//            installationInstructionsJSON.put("handler", ((AdapterFaasInterface) adapterTaskInterface).getHandler());
//            installationInstructionsJSON.put("triggers", ((AdapterFaasInterface) adapterTaskInterface).getTriggers());
//            installationInstructionsJSON.put("timeout", ((AdapterFaasInterface) adapterTaskInterface).getTimeout());
//            installationInstructionsJSON.put("functionEnvironment", ((AdapterFaasInterface) adapterTaskInterface).getFunctionEnvironment());
//        } else if (adapterTaskInterface instanceof AdapterSparkInterface) {
//            installationInstructionsJSON.put("type", "spark");
//            installationInstructionsJSON.put("file", ((AdapterSparkInterface) adapterTaskInterface).getFile());
//            installationInstructionsJSON.put("className", (((AdapterSparkInterface) adapterTaskInterface).getClassName()));
//            installationInstructionsJSON.put("arguments", (((AdapterSparkInterface) adapterTaskInterface).getArguments()));
//            installationInstructionsJSON.put("sparkArguments", (((AdapterSparkInterface) adapterTaskInterface).getSparkArguments()));
//            installationInstructionsJSON.put("sparkConfiguration", (((AdapterSparkInterface) adapterTaskInterface).getSparkConfiguration()));
        } else {
            throw new AdapterException(format("Unknown TaskInterface type: %s", adapterTaskInterface.getClass().getSimpleName()));
        }
    }

    private void addPort(List<AbstractPortDefinition> ports, AdapterPort adapterPort) {
        AbstractPortDefinition port;
        if (adapterPort instanceof AdapterPortProvided) {
            port = new PortProvided(((AdapterPortProvided) adapterPort).getPort());
            port.setType(PortDefinition.PortType.fromValue(adapterPort.getType()));
            port.setName(adapterPort.getName());
        } else if (adapterPort instanceof AdapterPortRequired) {
            port = new PortRequired(((AdapterPortRequired) adapterPort).getIsMandatory());
            port.setType(PortDefinition.PortType.fromValue(adapterPort.getType()));
            port.setName(adapterPort.getName());
        } else {
            throw new AdapterException(format("Unknown Port type: %s", adapterPort.getClass().getSimpleName()));
        }
        ports.add(port);
    }
}
