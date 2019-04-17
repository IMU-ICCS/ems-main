package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.JobTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class JobTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterJob> {

    JobTaskExecutor(JobTask task, Collection<Future> predecessors, ColosseumApi api,
                    ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
    }

    @Override
    public void create(AdapterJob taskBody) {
        if (context.getJob(taskBody.getJobName()).isPresent()) {
            log.warn("Job {} already exists in Colosseum - skipping execution of the task", taskBody.getName());
            return;
        }

        try {
            JobNew jobNew = convertToJobNew(taskBody);
            Job job = api.addJob(jobNew);
            context.addJob(job);
        } catch (ApiException e) {
            log.error("Could not add Job. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
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
                    .updateAction(((AdapterPortRequired) adapterPort).getUpdateAction())
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
                    .containerType(LanceInterface.ContainerTypeEnum.valueOf(((AdapterLanceInterface) adapterTaskInterface).getContainterType()))
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
                    .processMapping(SparkInterface.ProcessMappingEnum.CLUSTER)
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

}
