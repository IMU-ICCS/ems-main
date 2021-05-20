package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterMonitor;
import eu.melodic.upperware.adapter.plangenerator.tasks.MonitorTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;

@Slf4j
public class MonitorTaskExecutor extends RunnableTaskExecutor<AdapterMonitor> {

    private final String applicationId;
    private final String authorizationBearer;
    private final ProactiveClientServiceForAdapter proactiveClientServiceForAdapter;

    MonitorTaskExecutor(MonitorTask task, Collection<Future> predecessors,
                        String applicationId,
                        String authorizationBearer,
                        ProactiveClientServiceForAdapter proactiveClientServiceForAdapter) {

        super(task, predecessors);
        this.applicationId = applicationId;
        this.authorizationBearer = authorizationBearer;
        this.proactiveClientServiceForAdapter = proactiveClientServiceForAdapter;
    }

    @Override
    public void create(AdapterMonitor taskBody) {
        try {
            log.info("MonitorTaskExecutor->create: [application id: {}]\nauthorization: {}\nAdapterMonitor= {}",
                    applicationId,
                    StringUtils.replace(authorizationBearer, "Bearer ", ""),
                    taskBody);
            // here we create a monitor for task/component (e.g. Component_App) that collects metrics and uses Push (app specific)
            // or Pull (e.g. SystemCpuUsage classic metric) Sensor to do this. We know node name here, so we can use it.
            // Every sensor type sends to ems client (localhost), we have jms broker configuration.

            int status = proactiveClientServiceForAdapter.addMonitors(
                    Collections.singletonList(taskBody.getNodeName()),
                    StringUtils.replace(authorizationBearer, "Bearer ", "")
            );
            log.info("MonitorTaskExecutor->create: [application id: {}] addMonitors status= {}", applicationId, status);

        } catch (RuntimeException e) {
            log.error("MonitorTaskExecutor->create: [application id: {}] Could not add Monitor. Error: {}", applicationId, e.getMessage());
            // TODO: LSZ - dev only ! uncomment for prod when Monitors will be fully functional on proactive side
            //throw new AdapterException(String.format("Problem during adding Monitor [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }

    @Override
    public void delete(AdapterMonitor taskBody) {
        log.info("MonitorTaskExecutor->delete: [application id: {}] AdapterMonitor= {}", applicationId, taskBody);
        log.info("MonitorTaskExecutor->delete: [application id: {}] delete not supported - only initial Monitors creation is managed by Melodic/Adapter", applicationId);
    }
}
