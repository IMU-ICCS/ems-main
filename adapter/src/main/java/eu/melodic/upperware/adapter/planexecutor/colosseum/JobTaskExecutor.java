package eu.melodic.upperware.adapter.planexecutor.colosseum;

import com.google.common.base.MoreObjects;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.JobTask;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class JobTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterJob> {

    private final String applicationId;

    JobTaskExecutor(JobTask task, Collection<Future> predecessors, ColosseumApi api,
                    ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture, String applicationId) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
        this.applicationId = applicationId;
    }

    @Override
    public void create(AdapterJob taskBody) {
        if (context.getJob(taskBody.getJobName()).isPresent()) {
            log.warn("Job {} already exists in Colosseum - skipping execution of the task", taskBody.getName());
            return;
        }

        try {
            /*JobNew jobNew = convertToJobNew(taskBody);
            Job job = api.addJob(jobNew);
            context.addJob(job);*/
            // TODO: LSZ
            // here we can create workflow/job skeleton with all defined tasks (e.g. Component_App) with their communication ports and installation recipe
            // and connection between tasks/components (by portRequired and portProvided matching)
            log.info("ProActive Dev [JobTaskExecutor]: AdapterJob taskBody= {}", taskBody);
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

            log.info("ProActive Dev [JobTaskExecutor]: JSONObject jobJSON= {}", jobJSON);
        } /*catch (ApiException e) {
            log.error("Could not add Job. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException(format("Could not add Job %s", taskBody.getJobName()), e);
        }*/
        catch (RuntimeException e) {
            log.error("Could not add Job. Error: {}", e.getMessage());
            throw new AdapterException(format("Could not add Job %s", taskBody.getJobName()), e);
        }
    }

    @Override
    public void delete(AdapterJob taskBody) {
        throw new UnsupportedOperationException("Delete method is not supported for JobTaskExecutor");
    }


    private JobNew convertToJobNew(AdapterJob taskBody) {
        return new JobNew()
                .name(taskBody.getJobName())
                .tasks(convertToTasks(taskBody.getTasks()))
                .communications(convertToCommunications(taskBody.getCommunications()));
    }

    private List<Task> convertToTasks(List<AdapterTask> tasks) {
        return tasks.stream()
                .map(this::convertToTask)
                .collect(Collectors.toList());
    }

    private Task convertToTask(AdapterTask adapterTask) {
        return new Task()
                .name(adapterTask.getName())
                .behaviour(new ServiceBehaviour()
                        .restart(true)
                        .type(ServiceBehaviour.class.getSimpleName())
                )
                .ports(convertToPorts(adapterTask.getPorts()))
                .interfaces(convertToInterfaces(adapterTask.getInterfaces()));
    }

    private List<Port> convertToPorts(List<AdapterPort> ports){
        return ports.stream()
                .map(this::convertToPort)
                .collect(Collectors.toList());
    }

    private Port convertToPort(AdapterPort adapterPort) {
        if (adapterPort instanceof AdapterPortProvided) {
            return new PortProvided()
                    .port(((AdapterPortProvided) adapterPort).getPort())
                    .name(adapterPort.getName())
                    .type(adapterPort.getType());
        } else if (adapterPort instanceof AdapterPortRequired) {
            return new PortRequired()
                    .isMandatory(((AdapterPortRequired) adapterPort).getIsMandatory())
                    .name(adapterPort.getName())
                    .type(adapterPort.getType());
        }
        throw new AdapterException(format("Unknown Port type: %s", adapterPort.getClass().getSimpleName()));
    }

    private List<TaskInterface> convertToInterfaces(List<AdapterTaskInterface> interfaces) {
        return interfaces.stream()
                .map(this::convertToInterface)
                .collect(Collectors.toList());

    }

    private TaskInterface convertToInterface(AdapterTaskInterface adapterTaskInterface) {
        if (adapterTaskInterface instanceof AdapterLanceInterface) {
            return new LanceInterface()
                    .updateAction(((AdapterLanceInterface) adapterTaskInterface).getUpdate())
                    .containerType(LanceInterface.ContainerTypeEnum.valueOf(((AdapterLanceInterface) adapterTaskInterface).getContainterType()))
                    .operatingSystem(convertToOperatingSystem(((AdapterLanceInterface) adapterTaskInterface).getOperatingSystem()))
                    .preInstall(((AdapterLanceInterface) adapterTaskInterface).getPreInstall())
                    .install(((AdapterLanceInterface) adapterTaskInterface).getInstall())
                    .postInstall(((AdapterLanceInterface) adapterTaskInterface).getPostInstall())
                    .start(((AdapterLanceInterface) adapterTaskInterface).getStart())
                    .startDetection(((AdapterLanceInterface) adapterTaskInterface).getStartDetection())
                    .stop(((AdapterLanceInterface) adapterTaskInterface).getStop())
                    .type(LanceInterface.class.getSimpleName());

        } else if (adapterTaskInterface instanceof AdapterSparkInterface) {
            return new SparkInterface()
                    .file(((AdapterSparkInterface) adapterTaskInterface).getFile())
                    .className(((AdapterSparkInterface) adapterTaskInterface).getClassName())
                    .arguments(((AdapterSparkInterface) adapterTaskInterface).getArguments())
                    .sparkArguments(((AdapterSparkInterface) adapterTaskInterface).getSparkArguments())
                    .sparkConfiguration(((AdapterSparkInterface) adapterTaskInterface).getSparkConfiguration())
                    .processMapping(ProcessMapping.CLUSTER)
                    .type(SparkInterface.class.getSimpleName());

        } else if (adapterTaskInterface instanceof AdapterDockerInterface) {
            return new DockerInterface()
                    .dockerImage(((AdapterDockerInterface) adapterTaskInterface).getDockerImage())
                    .environment(((AdapterDockerInterface) adapterTaskInterface).getEnvironment())
                    .type(DockerInterface.class.getSimpleName());

        } else if (adapterTaskInterface instanceof AdapterFaasInterface) {
            return new FaasInterface()
                    .functionName(((AdapterFaasInterface) adapterTaskInterface).getFunctionName())
                    .sourceCodeUrl(((AdapterFaasInterface) adapterTaskInterface).getSourceCodeUrl())
                    .handler(((AdapterFaasInterface) adapterTaskInterface).getHandler())
                    .triggers(convertToTriggers(((AdapterFaasInterface) adapterTaskInterface).getTriggers()))
                    .timeout(((AdapterFaasInterface) adapterTaskInterface).getTimeout())
                    .functionEnvironment(((AdapterFaasInterface) adapterTaskInterface).getFunctionEnvironment())
                    .type(FaasInterface.class.getSimpleName());
        }
        throw new AdapterException(format("Unknown TaskInterface type: %s", adapterTaskInterface.getClass().getSimpleName()));
    }

    private OperatingSystem convertToOperatingSystem(AdapterOperatingSystem adapterOperatingSystem) {
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setOperatingSystemFamily(OperatingSystemFamily.valueOf(adapterOperatingSystem.getOperatingSystemFamily().toString()));
        operatingSystem.setOperatingSystemArchitecture(OperatingSystemArchitecture.valueOf(adapterOperatingSystem.getOperatingSystemArchitecture().toString()));
        operatingSystem.setOperatingSystemVersion(adapterOperatingSystem.getOperatingSystemVersion());
        return operatingSystem;
    }

    private List<Communication> convertToCommunications(List<AdapterCommunication> communications) {
        return communications
                .stream()
                .map(this::convertToCommunication)
                .collect(Collectors.toList());
    }

    private Communication convertToCommunication(AdapterCommunication adapterCommunication) {
        return new Communication()
                .portProvided(adapterCommunication.getPortProvided())
                .portRequired(adapterCommunication.getPortRequired());
    }

    private List<Trigger> convertToTriggers(List<AdapterFaasTrigger> triggers) {
        return triggers
                .stream()
                .map(this::convertToTrigger)
                .collect(Collectors.toList());
    }


    private Trigger convertToTrigger(AdapterFaasTrigger trigger) {
        return new HttpTrigger()
                .httpMethod(trigger.getHttpMethod())
                .httpPath(trigger.getHttpPath())
                .type(trigger.getType());
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
        .orElseThrow(() -> new AdapterException(format("No installation instructions for task: %s [part of application id: %s]", adapterTask.getName(), applicationId)));
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
