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
import java.util.Optional;

public interface ColosseumApi {

    Queue findQueuedTask(String taskId) throws ApiException;


    Queue addSchedule(ScheduleNew scheduleNew) throws ApiException;

    Optional<Schedule> getSchedule(String scheduleId) throws ApiException;

    List<Schedule> getSchedules() throws ApiException;


    Queue addProcess(CloudiatorProcessNew cloudiatorProcessNew) throws ApiException;


    Job addJob(JobNew jobNew) throws ApiException;

    Job getJob(String jobId) throws ApiException;

    List<Job> getJobs() throws ApiException;


    Queue addNode(NodeRequest nodeRequest) throws ApiException;


    Optional<NodeGroup> getNodeGroup(String nodeGroupId) throws ApiException;

    Queue deleteNode(String nodeId) throws ApiException;

    Optional<ProcessGroup> getProcessGroup(String processGroupId) throws ApiException;

    Queue deleteProcess(String processId) throws ApiException;

    Monitor addMonitor(Monitor monitor) throws ApiException;
}
