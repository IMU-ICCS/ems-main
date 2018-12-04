/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.communication.colosseum;

import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.JobApi;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;
import io.github.cloudiator.rest.api.QueueApi;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumClientApi implements ColosseumApi {

  /*
   *  TODO Colosseum Client does not throw exceptions while deleting nonexistent application etc.
   *  Because of that execution process will not be interrupted.
   *  There are possible solutions:
   *  - enrich Colosseum Client with this functionality
   *  - create interceptor analyzing HTTP response codes
   *  This remark deals with others eu.melodic.upperware.adapter.colosseum.* 'Client' (not REST) APIs as well.
   */

  private JobApi jobApi;
  private NodeApi nodeApi;
  private QueueApi queueApi;
  private ProcessApi processApi;

  @Override
  public Queue findQueuedTask(String taskId) throws ApiException {
    return queueApi.findQueuedTask(taskId);
  }
  @Override
  public Queue addSchedule(@NonNull ScheduleNew scheduleNew) throws ApiException {
    log.info("Adding ScheduleNew: {}", scheduleNew);
    return processApi.addSchedule(scheduleNew);
  }

  @Override
  public Schedule getSchedule(String scheduleId) throws ApiException {
    return CollectionUtils.emptyIfNull(processApi.getSchedules())
            .stream()
            .filter(schedule -> scheduleId.equals(schedule.getId()))
            .findFirst()
            .orElse(null);
  }

  @Override
  public List<Schedule> getSchedules() throws ApiException {
    return processApi.getSchedules();
  }

  @Override
  public Queue addProcess(@NotNull CloudiatorProcessNew cloudiatorProcessNew) throws ApiException {
    log.info("Adding CloudiatorProcessNew: {}", cloudiatorProcessNew);
    return processApi.createProcess(cloudiatorProcessNew);
  }

  @Override
  public CloudiatorProcess getProcess(String scheduleId, String processId) throws ApiException {
    return CollectionUtils.emptyIfNull(processApi.getProcesses(scheduleId))
            .stream()
            .filter(process -> processId.equals(process.getId()))
            .findFirst()
            .orElse(null);
  }

  @Override
  public List<CloudiatorProcess> getProcessess(String scheduleId) throws ApiException {
    return processApi.getProcesses(scheduleId);
  }

  @Override
  public Job addJob(@NonNull JobNew jobNew) throws ApiException {
    log.info("Adding JobNew: {}", jobNew);
    return jobApi.addJob(jobNew);
  }

  @Override
  public Job getJob(@NonNull String jobId) throws ApiException {
    return jobApi.findJob(jobId);
  }

  @Override
  public List<Job> getJobs() throws ApiException {
    return jobApi.findJobs();
  }

  @Override
  public Queue addNode(@NonNull NodeRequest nodeRequest) throws ApiException {
      log.info("Adding NodeRequest: {}", nodeRequest);
      return nodeApi.addNode(nodeRequest);
  }

  @Override
  public Node getNode(String id) throws ApiException {
    return nodeApi.getNode(id);
  }

  @Override
  public List<Node> getNodes() throws ApiException {
    return nodeApi.findNodes();
  }

  @Override
  public NodeGroup getNodeGroup(String nodeGroupId) throws ApiException {
    return nodeApi.getNodeGroup(nodeGroupId);
  }

}
