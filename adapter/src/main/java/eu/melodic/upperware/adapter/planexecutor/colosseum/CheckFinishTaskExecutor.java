package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import io.github.cloudiator.rest.model.Queue;
import io.github.cloudiator.rest.model.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

import static java.lang.String.format;

@Slf4j
@AllArgsConstructor
public class CheckFinishTaskExecutor implements Callable<Queue> {

    private CheckFinishTask checkFinishTask;
    private ColosseumApi api;
    private int delayBetweenCheck;

    @Override
    public Queue call() throws Exception {
        Queue queuedTask;

        boolean continueChecking = false;
        do {
            queuedTask = api.findQueuedTask(checkFinishTask.getData().getQueueName());
            log.debug("Result of queuedTask {} with queueId {} is {}",
                    checkFinishTask.getData().getName(), checkFinishTask.getData().getQueueName(), queuedTask.toString());

            if (isInStatus(QueueStatus.FAILED, queuedTask)) {
                throw new AdapterException(format("Result of task %s failed, reason: %s", queuedTask.getId(), queuedTask.getDiagnosis()));
            }

            if (isInStatus(QueueStatus.COMPLETED, queuedTask)) {
                String location = queuedTask.getLocation();
                if (checkFinishTask.getFunction().apply(location)){
                    return queuedTask;
                } else {
                    continueChecking = true;
                }
                log.debug("Result of queuedTask {} with queueId {} is {} but check function return false",
                        checkFinishTask.getData().getName(), checkFinishTask.getData().getQueueName(), queuedTask.toString());
            }
            //waiting
            Thread.sleep(delayBetweenCheck);
        } while (isInStatus(QueueStatus.RUNNING, queuedTask) || isInStatus(QueueStatus.SCHEDULED, queuedTask) || continueChecking);
        throw new AdapterException("Something is wrong, and I don't know why...");
    }

    private boolean isInStatus(QueueStatus queueStatus, Queue queue) {
        return queueStatus.equals(queue.getStatus());
    }
}
