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
import io.github.cloudiator.rest.model.*;

import java.util.List;

public interface ColosseumApi {

  Queue findQueuedTask(String taskId) throws ApiException;


  Queue addSchedule(ScheduleNew scheduleNew) throws ApiException;

  Schedule getSchedule(String scheduleId) throws ApiException;

  List<Schedule> getSchedules() throws ApiException;


  Queue addProcess(CloudiatorProcessNew cloudiatorProcessNew) throws ApiException;

  CloudiatorProcess getProcess(String scheduleId, String processId) throws ApiException;

  List<CloudiatorProcess> getProcessess(String scheduleId) throws ApiException;


  Job addJob(JobNew jobNew) throws ApiException;

  Job getJob(String jobId) throws ApiException;

  List<Job> getJobs() throws ApiException;


  Queue addNode(NodeRequest nodeRequest) throws ApiException;

  Node getNode(String id) throws ApiException;

  List<Node> getNodes() throws ApiException;


  NodeGroup getNodeGroup(String nodeGroupId) throws ApiException;

}
