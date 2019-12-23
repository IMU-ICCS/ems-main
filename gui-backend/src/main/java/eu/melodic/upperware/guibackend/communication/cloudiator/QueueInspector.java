package eu.melodic.upperware.guibackend.communication.cloudiator;

import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.QueueApi;
import io.github.cloudiator.rest.model.Queue;
import io.github.cloudiator.rest.model.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class QueueInspector {

    private QueueApi queueApi;
    private CloudiatorProperties cloudiatorProperties;

    public void waitForQueueFinish(String queueId) {
        boolean continueWaiting = true;
        Queue queuedTask;
        log.info("Waiting for queued task with id: {}", queueId);
        do {
            log.debug("Queue with id: {} checking", queueId);
            try {
                queuedTask = queueApi.findQueuedTask(queueId);
                if (QueueStatus.COMPLETED.equals(queuedTask.getStatus())) {
                    log.info("Queued task with id: {} completed", queueId);
                    continueWaiting = false;
                } else if (QueueStatus.FAILED.equals(queuedTask.getStatus())) {
                    log.error("Queued task with id: {} failed: {}", queueId, queuedTask.getDiagnosis());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, queuedTask.getDiagnosis());
                }

            } catch (ApiException e) {
                String errorMessage = String.format("Error by checking queue with id %s status.", queueId);
                log.error(errorMessage, e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
            }
            try {
                Thread.sleep(cloudiatorProperties.getCloudiator().getDelayBetweenQueueCheck());
            } catch (InterruptedException e) {
                log.error("Waiting for finish of queued task with id {} interrupted: ", queueId, e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem on backend side: waiting for finish of queued task with id %s interrupted.", queueId));
            }

        } while (QueueStatus.SCHEDULED.equals(queuedTask.getStatus()) || QueueStatus.RUNNING.equals(queuedTask.getStatus()) || continueWaiting);
    }
}
