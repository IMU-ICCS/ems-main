package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveContext;
import eu.melodic.upperware.adapter.planexecutor.TaskWatchDog;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterCheckFinish;
import eu.melodic.upperware.adapter.plangenerator.model.Data;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import io.github.cloudiator.rest.model.Queue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;

@Slf4j
public abstract class WatchdogColosseumTaskExecutor<T extends Data> extends ColosseumTaskExecutor<T> implements TaskWatchDog {

    private ThreadPoolTaskExecutor executor;
    private ColosseumExecutorFactory colosseumExecutorFactory;
    ShelveContext shelveContext;


    WatchdogColosseumTaskExecutor(Task<T> task, Collection<Future> predecessors, ColosseumApi api,
                                  ColosseumContext context, ThreadPoolTaskExecutor executor,
                                  ColosseumExecutorFactory colosseumExecutorFactory, ShelveContext shelveContext) {
        super(task, predecessors, api, context);
        this.executor = executor;
        this.colosseumExecutorFactory = colosseumExecutorFactory;
        this.shelveContext = shelveContext;
    }

    @Override
    public Queue watch(String queueId) throws AdapterException {
        String taskName = task.getData().getName();
        try {
            Future<Queue> queueFuture = submitCallableTask(new CheckFinishTask(task.getType(), new AdapterCheckFinish(queueId, taskName)));
            log.info("Waiting for result of the {} from the queue {}", taskName, queueId);
            Queue queue = queueFuture.get();
            log.info("Result of waiting for task {} on queue {} is {}", taskName, queueId, queue);
            return queue;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Problem during checking queue status", e);

            if (e instanceof ExecutionException) {
                ExecutionException ee = (ExecutionException) e;
                log.error("ExecutionException: ", ee);
            }
            throw new AdapterException(format("Problem during waiting for Future operation. Task: %s, Queue: %s", taskName, queueId), e);
        }
    }

    protected String getId(String queueLocation) {
        if (StringUtils.isNotBlank(queueLocation)) {
            return queueLocation.substring(queueLocation.lastIndexOf("/") + 1);
        }
        throw new AdapterException(format("Could not get id from location %s", queueLocation));
    }

    private Future<Queue> submitCallableTask(Task task) {
        return executor.submit(createCallableTaskExecutor(task));
    }

    private Callable createCallableTaskExecutor(Task task) {
        return colosseumExecutorFactory.createTaskExecutor(task);
    }
}
