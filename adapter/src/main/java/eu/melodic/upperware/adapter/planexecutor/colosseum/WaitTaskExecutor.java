package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterWaitData;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.WaitTask;
import io.github.cloudiator.rest.model.Queue;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.function.Function;

@Slf4j
public class WaitTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterWaitData> {

    WaitTaskExecutor(WaitTask task, Collection<Future> predecessors, ColosseumApi api,
                     ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
    }

    @Override
    public void create(AdapterWaitData taskBody) {
        log.info("Invoiking create method for WaitTask");
    }

    @Override
    public void delete(AdapterWaitData taskBody) {
        log.info("Invoiking delete method for WaitTask");
    }
}
