package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterScale;
import eu.melodic.upperware.adapter.plangenerator.tasks.ScaleTask;
import eu.melodic.upperware.adapter.proactive.client.ProactiveClientService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

@Slf4j
public class ScaleTaskExecutor extends RunnableTaskExecutor<AdapterScale> {

    private final String applicationId;
    private final ProactiveClientService proactiveClientService;

    ScaleTaskExecutor(ScaleTask task, Collection<Future> predecessors, String applicationId, ProactiveClientService proactiveClientService) {
        super(task, predecessors);
        this.applicationId = applicationId;
        this.proactiveClientService = proactiveClientService;
    }

    @Override
    public void create(AdapterScale taskBody) {
        try {
            // here we trigger scale out (adding node) to task/component (e.g. Component_App). We have new node name, new node will be
            // the same as the original one (based on the same node candidate). Probably monitor created for original node will have to be created for
            // new node as well. Scale out is one node at a time.
            log.info("ScaleTaskExecutor->create: [application id: {}] ScaleDirection->OUT AdapterScale= {}", applicationId, taskBody);
            int status = proactiveClientService.addScaleOutTask(taskBody.getNodeNames(), applicationId, taskBody.getTaskName());
            log.info("ScaleTaskExecutor->create: [application id: {}] addScaleOutTask status= {}", applicationId, status);

        }
        catch (RuntimeException e) {
            log.error("ScaleTaskExecutor->create: [application id: {}] Could not scale OUT. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during scaleTask->OUT [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }

    @Override
    public void delete(AdapterScale taskBody) {
        try {
            // here we trigger scale in (deleting nodes) from task/component (e.g. Component_App). We have a list of nodes names to delete.
            log.info("ScaleTaskExecutor->delete: [application id: {}] ScaleDirection->IN AdapterScale= {}", applicationId, taskBody);
            int status = proactiveClientService.addScaleInTask(taskBody.getNodeNames(), applicationId, taskBody.getTaskName());
            log.info("ScaleTaskExecutor->delete: [application id: {}] addScaleInTask status= {}", applicationId, status);
        }
        catch (RuntimeException e) {
            log.error("ScaleTaskExecutor->delete: [application id: {}] Could not scale IN. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during scaleTask->IN [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }
}
