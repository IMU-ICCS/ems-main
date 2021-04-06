package eu.melodic.upperware.adapter.planexecutor.colosseum;

import com.google.common.base.MoreObjects;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.JobTask;
import eu.melodic.upperware.adapter.proactive.client.ProactiveClientService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.concurrent.Future;

import static java.lang.String.format;

@Slf4j
public class JobTaskExecutor extends RunnableTaskExecutor<AdapterJob> {

    private final String applicationId;
    private final ProactiveClientService proactiveClientService;

    JobTaskExecutor(JobTask task, Collection<Future> predecessors, String applicationId, ProactiveClientService proactiveClientService) {
        super(task, predecessors);
        this.applicationId = applicationId;
        this.proactiveClientService = proactiveClientService;
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
            JSONObject jobJSON = new JSONObject();
            JSONObject jobInformationJSON = new JSONObject();
            jobInformationJSON.put("id", this.applicationId);
            jobInformationJSON.put("name", taskBody.getJobName());
            jobJSON.put("jobInformation", jobInformationJSON);

            JSONArray tasksJSONArray = new JSONArray();
            taskBody.getTasks()
                    .forEach(adapterTask -> addTask(tasksJSONArray, adapterTask));
            jobJSON.put("tasks", tasksJSONArray);

            JSONArray communicationsJSONArray = new JSONArray();
            taskBody.getCommunications()
                    .forEach(adapterCommunication -> addCommunication(communicationsJSONArray, adapterCommunication));
            jobJSON.put("communications", communicationsJSONArray);

            log.info("JobTaskExecutor->create: [application id: {}] ProActive job (JSONObject): \n{}", applicationId, jobJSON);

            log.info("JobTaskExecutor->create: [application id: {}] ProActive Connection State={}", applicationId, proactiveClientService.getConnectionState());
            proactiveClientService.createJob(jobJSON);

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

    private void addTask(JSONArray tasksJSONArray, AdapterTask adapterTask) {
        JSONObject taskJSON = new JSONObject();
        taskJSON.put("name", adapterTask.getName());
        taskJSON.put("installation", getInstallationInstructionsJSON(adapterTask));

        JSONArray portsJSONArray = new JSONArray();
        adapterTask.getPorts()
                .forEach(adapterPort -> addPort(portsJSONArray, adapterPort));
        taskJSON.put("ports", portsJSONArray);

        tasksJSONArray.put(taskJSON);
    }

    private JSONObject getInstallationInstructionsJSON(AdapterTask adapterTask) {
        return adapterTask.getInterfaces()
        .stream()
        .findFirst()
        .map(this::addInstallationInstructions)
        .orElseThrow(() -> new AdapterException(format("No installation instructions for task: %s [application id: %s]", adapterTask.getName(), applicationId)));
    }

    private void addCommunication(JSONArray communicationsJSONArray, AdapterCommunication adapterCommunication) {
        JSONObject communicationJSON = new JSONObject();
        communicationJSON.put("portRequired", adapterCommunication.getPortRequired());
        communicationJSON.put("portProvided", adapterCommunication.getPortProvided());
        communicationsJSONArray.put(communicationJSON);
    }

    private JSONObject addInstallationInstructions(AdapterTaskInterface adapterTaskInterface) {
        JSONObject installationInstructionsJSON = new JSONObject();

        if (adapterTaskInterface instanceof AdapterLanceInterface) {
            JSONObject operatingSystemJSON = new JSONObject();
            installationInstructionsJSON.put("type", "commands");
            operatingSystemJSON.put("operatingSystemFamily", ((AdapterLanceInterface) adapterTaskInterface).getOperatingSystem().getOperatingSystemFamily());
            operatingSystemJSON.put("operatingSystemVersion", ((AdapterLanceInterface) adapterTaskInterface).getOperatingSystem().getOperatingSystemVersion());
            installationInstructionsJSON.put("operatingSystem", operatingSystemJSON);
            installationInstructionsJSON.put("preInstall", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getPreInstall(), JSONObject.NULL));
            installationInstructionsJSON.put("install", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getInstall(), JSONObject.NULL));
            installationInstructionsJSON.put("postInstall", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getPostInstall(), JSONObject.NULL));
            installationInstructionsJSON.put("start", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getStart(), JSONObject.NULL));
            installationInstructionsJSON.put("startDetection", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getStartDetection(), JSONObject.NULL));
            installationInstructionsJSON.put("stop", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getStop(), JSONObject.NULL));
            installationInstructionsJSON.put("update", MoreObjects.firstNonNull(((AdapterLanceInterface) adapterTaskInterface).getUpdate(), JSONObject.NULL));
        } else if (adapterTaskInterface instanceof AdapterSparkInterface) {
            installationInstructionsJSON.put("type", "spark");
            installationInstructionsJSON.put("file", ((AdapterSparkInterface) adapterTaskInterface).getFile());
            installationInstructionsJSON.put("className", (((AdapterSparkInterface) adapterTaskInterface).getClassName()));
            installationInstructionsJSON.put("arguments", (((AdapterSparkInterface) adapterTaskInterface).getArguments()));
            installationInstructionsJSON.put("sparkArguments", (((AdapterSparkInterface) adapterTaskInterface).getSparkArguments()));
            installationInstructionsJSON.put("sparkConfiguration", (((AdapterSparkInterface) adapterTaskInterface).getSparkConfiguration()));
        } else if (adapterTaskInterface instanceof AdapterDockerInterface) {
            installationInstructionsJSON.put("type", "docker");
            installationInstructionsJSON.put("dockerImage", ((AdapterDockerInterface) adapterTaskInterface).getDockerImage());
            installationInstructionsJSON.put("environment", ((AdapterDockerInterface) adapterTaskInterface).getEnvironment());
        } else if (adapterTaskInterface instanceof AdapterFaasInterface) {
            installationInstructionsJSON.put("type", "faas");
            installationInstructionsJSON.put("functionName", ((AdapterFaasInterface) adapterTaskInterface).getFunctionName());
            installationInstructionsJSON.put("sourceCodeUrl", ((AdapterFaasInterface) adapterTaskInterface).getSourceCodeUrl());
            installationInstructionsJSON.put("handler", ((AdapterFaasInterface) adapterTaskInterface).getHandler());
            installationInstructionsJSON.put("triggers", ((AdapterFaasInterface) adapterTaskInterface).getTriggers());
            installationInstructionsJSON.put("timeout", ((AdapterFaasInterface) adapterTaskInterface).getTimeout());
            installationInstructionsJSON.put("functionEnvironment", ((AdapterFaasInterface) adapterTaskInterface).getFunctionEnvironment());
        } else {
            throw new AdapterException(format("Unknown TaskInterface type: %s", adapterTaskInterface.getClass().getSimpleName()));
        }
        return installationInstructionsJSON;
    }

    private void addPort(JSONArray portsJSONArray, AdapterPort adapterPort) {
        JSONObject portJSON = new JSONObject();

        if (adapterPort instanceof AdapterPortProvided) {
            portJSON.put("type", adapterPort.getType());
            portJSON.put("name", adapterPort.getName());
            portJSON.put("port", ((AdapterPortProvided) adapterPort).getPort());
        } else if (adapterPort instanceof AdapterPortRequired) {
            portJSON.put("type", adapterPort.getType());
            portJSON.put("name", adapterPort.getName());
            portJSON.put("isMandatory", ((AdapterPortRequired) adapterPort).getIsMandatory());
        } else {
            throw new AdapterException(format("Unknown Port type: %s", adapterPort.getClass().getSimpleName()));
        }

        portsJSONArray.put(portJSON);
    }
}
