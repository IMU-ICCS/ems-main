package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveJob;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.JobTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import io.github.cloudiator.rest.model.Communication;
import io.github.cloudiator.rest.model.PortProvided;
import io.github.cloudiator.rest.model.PortRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class JobTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterJob> {

    JobTaskExecutor(JobTask task, Collection<Future> predecessors, ColosseumApi api,
                    ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory, ShelveContext shelveContext) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory, shelveContext);
    }

    @Override
    public void create(AdapterJob taskBody) {
        JobNew jobNew = convertToJobNew(taskBody);
        if (context.getJob(jobNew.getName()).isPresent()) {
            log.warn("Job {} already exists in Colosseum - skipping execution of the task", taskBody.getName());
            log.warn("Trying to deploy job: {}", jobNew.toString());
            return;
        }

        try {
            Job job = api.addJob(jobNew);
            context.addJob(job);
            shelveContext.addShelveJob(new ShelveJob(job.getId(), job.getName()));
        } catch (ApiException e) {
            log.error("Could not add Job. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException(format("Could not add Job %s", jobNew.getName()), e);
        }
    }

    @Override
    public void delete(AdapterJob taskBody) {

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
                .taskType(TaskType.valueOf(adapterTask.getTaskType().name()))
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

        } else if (adapterTaskInterface instanceof AdapterDockerInterface) {
            return new DockerInterface()
                    .dockerImage(((AdapterDockerInterface) adapterTaskInterface).getDockerImage())
                    .type(DockerInterface.class.getSimpleName());
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

}
