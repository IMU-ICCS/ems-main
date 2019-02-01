package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.WaitData;
import eu.melodic.upperware.adapter.plangenerator.tasks.WaitTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.concurrent.Future;

@Slf4j
public class WaitTaskExecutor extends WatchdogColosseumTaskExecutor<WaitData> {

    WaitTaskExecutor(WaitTask task, Collection<Future> predecessors, ColosseumApi api,
                     ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory);
    }

    @Override
    public void create(WaitData taskBody) {
        log.info("Invoiking create method for WaitTask");
    }

    @Override
    public void delete(WaitData taskBody) {
        log.info("Invoiking delete method for WaitTask");
    }
}
